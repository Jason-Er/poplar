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
import com.wecyberstage.wecyberstage.model.ComposeLine;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.model.UpdateComposeScriptInterface;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.helper.CustomItemTouchHelper;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;
import com.wecyberstage.wecyberstage.view.helper.SlideInterface;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class ComposeX extends CustomView implements PlayStateInterface, SlideInterface, UpdateComposeScriptInterface, OnStartDragListener {

    private static final String COMPOSE_INFO_KEY = "compose_info";

    private ComposeViewModel viewModel;
    private AppCompatActivity appCompatActivity;
    private ComposeXScriptLayoutManager layoutManager;
    private ComposeXScriptAdapter adapter;
    private CustomItemTouchHelper itemTouchHelper;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public ComposeX(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType) {
        super(activity, container, viewType);
        this.appCompatActivity = activity;
    }

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.view_recycler, container,false);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        adapter = new ComposeXScriptAdapter(new AdapterDelegatesManager<>(), this, this);
        layoutManager = new ComposeXScriptLayoutManager(adapter, new ComposeXLayoutDelegateManager<Object>());
        ((RecyclerView)view).setHasFixedSize(true);
        ((RecyclerView)view).setLayoutManager(layoutManager);
        ((RecyclerView)view).setAdapter(adapter);
        /*
        ((RecyclerView)view).addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                View child = ((RecyclerView)view).findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    // tapped on child
                    return false;
                } else {
                    // Tap occured outside all child-views.
                    // do something
                    Log.i("RecyclerView","ComposeX RecyclerView click");
                    OutsideClickEvent event = new OutsideClickEvent("OUTSIDE_CLICK");
                    EventBus.getDefault().post(event);
                    return true;
                }
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        */
        CustomItemTouchHelper.Callback callback = new ComposeXItemTouchHelperCallback(adapter);
        itemTouchHelper = new CustomItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(((RecyclerView)view));

        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(ComposeViewModel.class);
        PlayState playState = activity.getIntent().getParcelableExtra(COMPOSE_INFO_KEY);
        if(playState != null) {
            viewModel.setPlayState(playState);
        }
        viewModel.scriptLiveData.observe(activity, new Observer<ComposeScript>() {
            @Override
            public void onChanged(@Nullable ComposeScript script) {
                if(script != null) {
                    adapter.setComposeScript(script);
                }
            }
        });
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
    public void updateComposeLine(ComposeLine composeLine, int ordinal) {
        viewModel.updateComposeLine(composeLine, ordinal);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onResume(Activity activity) {
        layoutManager.onResume(activity);
    }

    @Override
    public void onPause(Activity activity) {
        layoutManager.onPause(activity);
    }
}
