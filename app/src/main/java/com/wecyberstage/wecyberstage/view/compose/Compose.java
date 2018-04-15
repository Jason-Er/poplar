package com.wecyberstage.wecyberstage.view.compose;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.Injectable;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.util.label.PerActivity;
import com.wecyberstage.wecyberstage.view.helper.PlayInterface;
import com.wecyberstage.wecyberstage.view.main.MainActivity;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;

import java.util.MissingResourceException;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class Compose extends Fragment implements Injectable, PlayInterface {

    private static final String PLAY_ID_KEY = "play_id";
    private static final String SCENE_ID_KEY = "scene_id";

    private ComposeViewModel viewModel;

    @Inject
    ComposeScriptAdapter adapter;
    @Inject
    ComposeScriptLayoutManager layoutManager;
    @Inject
    UICommon uiCommon;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.frag_recycler, container,false);
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ComposeViewModel.class);
        Bundle args = getArguments();
        if (args != null && args.containsKey(PLAY_ID_KEY) && args.containsKey(SCENE_ID_KEY)) {
            viewModel.setPlayAndSceneId( args.getLong(PLAY_ID_KEY), args.getLong(SCENE_ID_KEY) );
        } else {
            // TODO: 11/14/2017 throw exception or show custom dialog
            throw new MissingResourceException("ParticipateFragment key: "+ PLAY_ID_KEY + " should not be NULL","Bundle", PLAY_ID_KEY);
        }
        viewModel.scriptLiveData.observe(this, new Observer<ComposeScript>() {
            @Override
            public void onChanged(@Nullable ComposeScript script) {
                if(script != null) {
                    adapter.setComposeScript(script);
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPlayAndSceneId(long playId, long sceneId) {
        Bundle args = new Bundle();
        args.putLong(PLAY_ID_KEY, playId);
        args.putLong(SCENE_ID_KEY, sceneId);
        setArguments(args);
    }
}
