package com.wecyberstage.wecyberstage.view.composeY;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageLineHandle;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.view.helper.ClickActionInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayerView;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.helper.SlideInterface;
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.main.FooterEditBar;
import com.wecyberstage.wecyberstage.view.message.FooterEditBarEvent;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComposeY extends PlayerView
        implements OnStartDragListener, SlideInterface,
        StageLineHandle, RegisterBusEventInterface, ClickActionInterface {

    private final String TAG = "ComposeY";
    private static final String COMPOSE_INFO_KEY = "compose_info";

    // private StagePlayCursor stagePlayCursor;
    private ComposeViewModel viewModel;
    private ComposeYScriptAdapter adapter;
    ItemTouchHelper itemTouchHelper;

    @BindView(R.id.footer_edit_bar)
    FooterEditBar footerEditBar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public ComposeY(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType, ToolViewsDelegate toolViewsDelegate) {
        super(activity, container, viewType, toolViewsDelegate);
    }

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.frag_composey, container,false);
        ButterKnife.bind(this, activity);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        adapter = new ComposeYScriptAdapter(new AdapterDelegatesManager<>(), this, this);
        adapter.restoreStates(activity);

        ((RecyclerView)view).setHasFixedSize(true);
        ((RecyclerView)view).setLayoutManager(new LinearLayoutManager(activity));
        ((RecyclerView)view).setAdapter(adapter);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        AppBarLayout.ScrollingViewBehavior behavior = new AppBarLayout.ScrollingViewBehavior();
        params.setBehavior(behavior);

        /*
        DividerItemDecoration decoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        ((RecyclerView)view).addItemDecoration(decoration);
        */

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView( (RecyclerView) view);

        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(ComposeViewModel.class);
        viewModel.stageScene.observe(activity, new Observer<StageScene>() {
            @Override
            public void onChanged(@Nullable StageScene stageScene) {
                adapter.setStageScene(stageScene);
            }
        });
    }

    @Override
    public void onStop(AppCompatActivity activity, @Nullable ViewGroup container) {
        adapter.saveStates(activity);
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
    // region implement of RegisterBusEventInterface
    @Override
    public void register(Activity activity) {
        if( isVisible() ) {
            adapter.register(activity);
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void unRegister(Activity activity) {
        if( isVisible() ) {
            adapter.unRegister(activity);
            EventBus.getDefault().unregister(this);
        }
    }
    // endregion
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseFooterEditMainEvent(FooterEditBarEvent event) {
        Log.d(TAG,"receive footerEditMain");
        switch (event.getMessage()){
            case "addStageLine":
                adapter.addStageLine((StageLine) event.getData());
                break;
            case "deleteStageSceneContent":
                adapter.deleteStageSceneContent();
                break;
            case "deleteStageScene":
                adapter.deleteStageScene();
                break;
            case "addStageScene":
                adapter.addStageScene();
                break;
        }
    }

}
