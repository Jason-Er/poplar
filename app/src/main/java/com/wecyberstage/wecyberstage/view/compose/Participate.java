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
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.viewmodel.ParticipateViewModel;

import java.util.MissingResourceException;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class Participate extends Fragment implements Injectable {

    private static final String ID_KEY = "id";

    private ParticipateViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.frag_recycler, container,false);
        recyclerView.setHasFixedSize(true);
        layoutManager = new KeyFrameLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        adapter = new KeyFrameAdapter();
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ParticipateViewModel.class);
        Bundle args = getArguments();
        if (args != null && args.containsKey(ID_KEY)) {
            viewModel.setPlayId( args.getLong(ID_KEY) );
        } else {
            // TODO: 11/14/2017 throw exception or show custom dialog
            throw new MissingResourceException("ParticipateFragment key: "+ ID_KEY + " should not be NULL","Bundle",ID_KEY);
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

    public static Participate create(long playId) {
        Participate fragment = new Participate();
        Bundle args = new Bundle();
        args.putLong(ID_KEY, playId);
        fragment.setArguments(args);
        return fragment;
    }
}
