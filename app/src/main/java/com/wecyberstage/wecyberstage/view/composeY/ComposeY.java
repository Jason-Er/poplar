package com.wecyberstage.wecyberstage.view.composeY;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;

import javax.inject.Inject;

public class ComposeY extends CustomView implements PlayStateInterface, OnStartDragListener, UtilityInterface {

    private static final String COMPOSE_INFO_KEY = "compose_info";

    private ComposeViewModel viewModel;
    private AppCompatActivity activity;
    ItemTouchHelper itemTouchHelper;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        this.activity = activity;
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.view_recycler, container,false);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        final ComposeYScriptAdapter adapter = new ComposeYScriptAdapter(activity, this);
        ((RecyclerView)view).setHasFixedSize(true);
        ((RecyclerView)view).setLayoutManager(new LinearLayoutManager(activity));
        ((RecyclerView)view).setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        ((RecyclerView)view).addItemDecoration(decoration);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView( (RecyclerView) view);

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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void hideSoftKeyboard() {
        if(activity.getCurrentFocus() == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
