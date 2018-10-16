package com.wecyberstage.wecyberstage.view.composeZ;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.view.helper.ClickActionInterface;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursorHandle;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursor;
import com.wecyberstage.wecyberstage.view.helper.PlayerView;
import com.wecyberstage.wecyberstage.view.helper.SlideInterface;
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.viewmodel.StagePlayViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class ComposeZ extends PlayerView
        implements SlideInterface, ClickActionInterface {

    private final String TAG = "ComposeZ";

    private StagePlayViewModel viewModel;
    private KeyFrameAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public ComposeZ(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType, ToolViewsDelegate toolViewsDelegate) {
        super(activity, container, viewType, toolViewsDelegate);
    }

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {

        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.frag_composez, container,false);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        adapter = new KeyFrameAdapter(new AdapterDelegatesManager<List<Object>>());
        KeyFrameLayoutManager layoutManager = new KeyFrameLayoutManager(new KeyFrameLayoutDelegateManager(), adapter);
        RecyclerView recyclerView = view.findViewById(R.id.frag_participate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(StagePlayViewModel.class);
        viewModel.keyFrame.observe(activity, new Observer<KeyFrame>() {
            @Override
            public void onChanged(@Nullable KeyFrame keyframe) {
                adapter.setKeyframe(keyframe);
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
    public void itemClick() {
        Log.d(TAG, "itemClick");
        if( toolViewsDelegate instanceof ClickActionInterface ) {
            ((ClickActionInterface) toolViewsDelegate).itemClick();
        }
    }

    @Override
    public void containerClick() {
        Log.d(TAG, "containerClick");
        if( toolViewsDelegate instanceof ClickActionInterface ) {
            ((ClickActionInterface) toolViewsDelegate).containerClick();
        }
    }

}
