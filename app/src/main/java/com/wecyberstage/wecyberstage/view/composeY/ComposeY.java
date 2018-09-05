package com.wecyberstage.wecyberstage.view.composeY;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.UpdateStagePlayInterface;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.helper.SlideInterface;
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;

import javax.inject.Inject;

public class ComposeY extends CustomView implements PlayStateInterface, OnStartDragListener,
        SlideInterface, UpdateStagePlayInterface {

    private static final String COMPOSE_INFO_KEY = "compose_info";

    private ComposeViewModel viewModel;
    private ComposeYScriptAdapter adapter;
    ItemTouchHelper itemTouchHelper;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public ComposeY(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType, ToolViewsDelegate toolViewsDelegate) {
        super(activity, container, viewType, toolViewsDelegate);
    }

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.view_recycler, container,false);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        adapter = new ComposeYScriptAdapter(new AdapterDelegatesManager<>(), this);
        adapter.restoreStates(activity);

        ((RecyclerView)view).setHasFixedSize(true);
        ((RecyclerView)view).setLayoutManager(new LinearLayoutManager(activity));
        ((RecyclerView)view).setAdapter(adapter);

        /*
        DividerItemDecoration decoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        ((RecyclerView)view).addItemDecoration(decoration);
        */

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView( (RecyclerView) view);

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
        adapter.saveStates(activity);
    }

    @Override
    public void setPlayState(PlayState playState) {
        activity.getIntent().putExtra(COMPOSE_INFO_KEY, playState);
        viewModel.setPlayState(playState);
    }

    @Override
    public PlayState getPlayState() {
        return viewModel.getPlayState();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void slideEnd() {
        super.slideEnd();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void updateStageLine(StageLine stageLine) {
        viewModel.updateStageLine(stageLine);
    }

    @Override
    public void addStageLine(StageLine stageLine) {
        viewModel.addStageLine(stageLine);
    }

    @Override
    public void deleteStageLine(StageLine stageLine) {
        viewModel.deleteStageLine(stageLine);
    }

    @Override
    public void swapStageLines(int position1, int position2) {
        viewModel.swapStageLines(position1, position2);
    }

}
