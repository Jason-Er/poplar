package com.wecyberstage.wecyberstage.view.participate;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.DaggerAppComponent;
import com.wecyberstage.wecyberstage.app.Injectable;
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.util.label.PerActivity;
import com.wecyberstage.wecyberstage.view.helper.PlayInterface;
import com.wecyberstage.wecyberstage.view.main.MainActivity;
import com.wecyberstage.wecyberstage.viewmodel.ParticipateViewModel;

import java.util.MissingResourceException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by mike on 2018/3/5.
 */

public class Participate extends Fragment implements Injectable, PlayInterface {

    private static final String PLAY_ID_KEY = "play_id";
    private static final String SCENE_ID_KEY = "scene_id";

    private ParticipateViewModel viewModel;

    @Inject
    KeyFrameAdapter adapter;
    @Inject
    KeyFrameLayoutManager layoutManager;
    @Inject
    UICommon uiCommon;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.frag_participate, container,false);
        view.setHasFixedSize(true);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        uiCommon.toImmersive();
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ParticipateViewModel.class);
        Bundle args = getArguments();
        if (args != null && args.containsKey(PLAY_ID_KEY) && args.containsKey(SCENE_ID_KEY)) {
            viewModel.setPlayAndSceneId( args.getLong(PLAY_ID_KEY), args.getLong(SCENE_ID_KEY) );
        } else {
            // TODO: 11/14/2017 throw exception or show custom dialog
            throw new MissingResourceException("ParticipateFragment key: "+ PLAY_ID_KEY + " should not be NULL","Bundle", PLAY_ID_KEY);
        }
        viewModel.keyFrameLiveData.observe(this, new Observer<KeyFrame>() {
            @Override
            public void onChanged(@Nullable KeyFrame keyframe) {
                if(keyframe != null) {
                    ((KeyFrameAdapter) adapter).setKeyframe(keyframe);
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        uiCommon.outImmersive();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPlayAndSceneId(long playId, long sceneId) {
        Bundle args = new Bundle();
        args.putLong(PLAY_ID_KEY, playId);
        args.putLong(SCENE_ID_KEY, sceneId);
        setArguments(args);
    }
}
