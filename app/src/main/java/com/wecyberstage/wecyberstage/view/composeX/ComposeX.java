package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.model.StageLineHandle;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.view.helper.ClickActionInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayerView;
import com.wecyberstage.wecyberstage.view.message.OutsideClickEvent;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.helper.CustomItemTouchHelper;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.helper.SlideInterface;
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class ComposeX extends PlayerView
        implements SlideInterface, StageLineHandle, OnStartDragListener,
        RegisterBusEventInterface, ClickActionInterface {

    private static final String COMPOSE_INFO_KEY = "compose_info";

    private final String TAG = "ComposeX";
    private ComposeViewModel viewModel;
    private ComposeXScriptLayoutManager layoutManager;
    private ComposeXScriptAdapter adapter;
    private CustomItemTouchHelper itemTouchHelper;
    // single click detect
    private float startX;
    private float startY;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public ComposeX(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType, ToolViewsDelegate toolViewsDelegate) {
        super(activity, container, viewType, toolViewsDelegate);
    }

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.frag_composex, container,false);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        adapter = new ComposeXScriptAdapter(new AdapterDelegatesManager<>(), this, this);
        layoutManager = new ComposeXScriptLayoutManager(adapter, new ComposeXLayoutDelegateManager<Object>());
        ((RecyclerView)view).setHasFixedSize(true);
        ((RecyclerView)view).setLayoutManager(layoutManager);
        ((RecyclerView)view).setAdapter(adapter);

        ((RecyclerView)view).addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = e.getX();
                        startY = e.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = e.getX();
                        float endY = e.getY();
                        if (UICommon.isAClick(startX, endX, startY, endY)) {
                            View child = ((RecyclerView)view).findChildViewUnder(e.getX(), e.getY());
                            if (child == null) {
                                Log.i("ComposeX","click outside");
                                OutsideClickEvent event = new OutsideClickEvent("OUTSIDE_CLICK");
                                EventBus.getDefault().post(event);
                            }
                        }
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        // playControl = new ComposeXPlayControl((RecyclerView)view);

        CustomItemTouchHelper.Callback callback = new ComposeXItemTouchHelperCallback();
        itemTouchHelper = new CustomItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(((RecyclerView)view));

        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(ComposeViewModel.class);
        viewModel.stageScene.observe(activity, new Observer<StageScene>() {
            @Override
            public void onChanged(@Nullable StageScene stageScene) {
                adapter.setStageScene(stageScene);
            }
        });

    }

    @Override
    public void onStop(AppCompatActivity activity, @Nullable ViewGroup container) {

    }

    @Override
    public void slideEnd() {
        super.slideEnd();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void updateStageLine(StageLine stageLine) {
        viewModel.updateStageLine(stageLine);
    }

    @Override
    public void addStageLine(StageLine stageLine) {

    }

    @Override
    public void deleteStageLine(StageLine stageLine) {

    }

    @Override
    public void swapStageLines(int position1, int position2) {

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void register(Activity activity) {
        if ( isVisible() ) {
            layoutManager.register(activity);
            adapter.register(activity);
        }
    }

    @Override
    public void unRegister(Activity activity) {
        if ( isVisible() ) {
            layoutManager.unRegister(activity);
            adapter.unRegister(activity);
        }
    }

}
