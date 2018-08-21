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
import com.wecyberstage.wecyberstage.message.OutsideClickEvent;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.UpdateStagePlayInterface;
import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.helper.CustomItemTouchHelper;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.PlayControlInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayControlSub1Interface;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.helper.SlideInterface;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class ComposeX extends CustomView implements PlayStateInterface,
        SlideInterface, UpdateStagePlayInterface, OnStartDragListener,
        PlayControlInterface, RegisterBusEventInterface {

    private static final String COMPOSE_INFO_KEY = "compose_info";

    private ComposeViewModel viewModel;
    private AppCompatActivity appCompatActivity;
    private ComposeXScriptLayoutManager layoutManager;
    private ComposeXScriptAdapter adapter;
    private CustomItemTouchHelper itemTouchHelper;
    // single click detect
    private float startX;
    private float startY;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public ComposeX(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType) {
        super(activity, container, viewType);
        this.appCompatActivity = activity;
    }

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.composex, container,false);

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
        PlayState playState = activity.getIntent().getParcelableExtra(COMPOSE_INFO_KEY);
        if(playState != null) {
            viewModel.setPlayState(playState);
        }
        viewModel.stageSceneLiveData.observe(activity, new Observer<StageScene>() {
            @Override
            public void onChanged(@Nullable StageScene stageScene) {
                if(stageScene != null) {
                    adapter.setStageScene(stageScene);
                }
            }
        });
    }

    @Override
    public void onStop(AppCompatActivity activity, @Nullable ViewGroup container) {

    }

    @Override
    public void setPlayState(PlayState playState) {
        appCompatActivity.getIntent().putExtra(COMPOSE_INFO_KEY, playState);
        viewModel.setPlayState(playState);
    }

    @Override
    public PlayState getPlayState() {
        return viewModel.getPlayState();
    }

    @Override
    public void slideEnd() {
        appCompatActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
        layoutManager.register(activity);
        adapter.register(activity);
    }

    @Override
    public void unRegister(Activity activity) {
        layoutManager.unRegister(activity);
        adapter.unRegister(activity);
    }

    // region implementation of PlayControlInterface
    @Override
    public void play() {
        if( view.getVisibility() == View.VISIBLE ) {
            if(view instanceof PlayControlSub1Interface) {
                ((PlayControlSub1Interface) view).play();
            }
        }
    }

    @Override
    public void pause() {
        if( view.getVisibility() == View.VISIBLE ) {
            if(view instanceof PlayControlSub1Interface) {
                ((PlayControlSub1Interface) view).pause();
            }
        }
    }

    @Override
    public void pre() {
        if( view.getVisibility() == View.VISIBLE ) {
            // TODO: 7/26/2018 to pre stage scene
        }
    }

    @Override
    public void next() {
        if( view.getVisibility() == View.VISIBLE ) {
            // TODO: 7/26/2018 to next stage scene
        }
    }

    @Override
    public void stop() {
        if( view.getVisibility() == View.VISIBLE ) {
            if(view instanceof PlayControlSub1Interface) {
                ((PlayControlSub1Interface) view).stop();
            }
        }
    }

    @Override
    public void seek(float percent) {
        if( view.getVisibility() == View.VISIBLE ) {
            if(view instanceof PlayControlSub1Interface) {
                ((PlayControlSub1Interface) view).seek(percent);
            }
        }
    }

    @Override
    public void volume(boolean open) {
        if( view.getVisibility() == View.VISIBLE ) {
            // TODO: 7/27/2018 open or shut down volume
        }
    }
    // endregion
}
