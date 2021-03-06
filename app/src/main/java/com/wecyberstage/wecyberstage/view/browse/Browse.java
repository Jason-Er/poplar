package com.wecyberstage.wecyberstage.view.browse;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.model.PlayInfo;
import com.wecyberstage.wecyberstage.data.dto.PageRequest;
import com.wecyberstage.wecyberstage.util.helper.Resource;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.CustomViewBehavior;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.main.MainActivity;
import com.wecyberstage.wecyberstage.viewmodel.BrowseViewModel;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by mike on 2018/3/5.
 */

public class Browse extends CustomView implements PlayStateInterface {

    private BrowseViewModel viewModel;
    private RecyclerView.Adapter adapter;
    private PlayState playState; // which contains the play is clicked

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public Browse(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType) {
        super(activity, container, viewType);
    }

    @Override
    public void onCreate(final AppCompatActivity activity, @Nullable ViewGroup container) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.view_recycler, container, false);
        CustomViewBehavior behavior = new CustomViewBehavior(activity, null);
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.setBehavior(behavior);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        int spanCount = 2;
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        ((RecyclerView)view).setLayoutManager(layoutManager);
        adapter = new PlayProfileAdapter();
        ((RecyclerView)view).setAdapter(adapter);

        ((PlayProfileAdapter) adapter).onItemClickCallBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("navigate to somewhere");
                playState = new PlayState(((PlayProfileCardView)v).playInfo.id, 1L, 0f);
                ((MainActivity)activity).navigateToComposeZ();
            }
        };

        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(BrowseViewModel.class);
        viewModel.setRequestPage(new PageRequest(0,15,""));
        viewModel.playInfoLiveData.observe(activity, new Observer<Resource<List<PlayInfo>>>(){
            @Override
            public void onChanged(@Nullable Resource<List<PlayInfo>> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        Timber.d("SUCCESS");
                        ((PlayProfileAdapter)adapter).setDataset(resource.data);
                        break;
                    case ERROR:

                        break;
                    case LOADING:

                        break;
                }
            }
        });
    }

    @Override
    public void setPlayState(PlayState playState) {
        this.playState = playState;
    }

    @Override
    public PlayState getPlayState() {
        return playState;
    }
}
