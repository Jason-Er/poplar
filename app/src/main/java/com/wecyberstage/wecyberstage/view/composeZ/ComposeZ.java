package com.wecyberstage.wecyberstage.view.composeZ;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.SlideInterface;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.viewmodel.ParticipateViewModel;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class ComposeZ extends CustomView implements PlayStateInterface, SlideInterface {

    private static final String PARTICIPATE_INFO_KEY = "participate_info";

    private ParticipateViewModel viewModel;
    private AppCompatActivity activity;

    @Inject
    KeyFrameAdapter adapter;
    @Inject
    KeyFrameLayoutManager layoutManager;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public ComposeZ(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType) {
        super(activity, container, viewType);
        this.activity = activity;
    }

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {

        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.view_compose_z, container,false);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        RecyclerView recyclerView = view.findViewById(R.id.frag_participate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(ParticipateViewModel.class);
        PlayState playState = activity.getIntent().getParcelableExtra(PARTICIPATE_INFO_KEY);
        if(playState != null) {
            viewModel.setPlayState(playState);
        }
        viewModel.keyFrameLiveData.observe(activity, new Observer<KeyFrame>() {
            @Override
            public void onChanged(@Nullable KeyFrame keyframe) {
                if(keyframe != null) {
                    adapter.setKeyframe(keyframe);
                }
            }
        });
    }

    @Override
    public void setPlayState(PlayState playState) {
        activity.getIntent().putExtra(PARTICIPATE_INFO_KEY, playState);
        viewModel.setPlayState(playState);
    }

    @Override
    public PlayState getPlayState() {
        return viewModel.getPlayState();
    }

    @Override
    public void slideEnd() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
