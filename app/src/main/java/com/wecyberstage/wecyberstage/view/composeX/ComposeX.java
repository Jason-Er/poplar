package com.wecyberstage.wecyberstage.view.composeX;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class ComposeX extends CustomView implements PlayStateInterface {

    private static final String COMPOSE_INFO_KEY = "compose_info";

    private ComposeViewModel viewModel;
    private AppCompatActivity activity;

    @Inject
    ComposeXScriptAdapter adapter;
    @Inject
    ComposeXScriptLayoutManager layoutManager;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        this.activity = activity;
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.frag_recycler, container,false);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        ((RecyclerView)view).setHasFixedSize(true);
        ((RecyclerView)view).setLayoutManager(layoutManager);
        ((RecyclerView)view).setAdapter(adapter);

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
        activity.getIntent().putExtra(COMPOSE_INFO_KEY, playState);
        viewModel.setPlayState(playState);
    }

    @Override
    public PlayState getPlayState() {
        return viewModel.getPlayState();
    }
}
