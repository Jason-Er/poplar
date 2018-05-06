package com.wecyberstage.wecyberstage.view.compose;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.CustomViewInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayInterface;
import com.wecyberstage.wecyberstage.view.helper.ViewOnTouch;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class Compose extends CustomView implements PlayInterface {

    private static final String COMPOSE_INFO_KEY = "compose_info";

    private ComposeViewModel viewModel;
    private AppCompatActivity activity;

    static class ComposeInfo implements Parcelable {
        long playId;
        long sceneId;

        public ComposeInfo(long playId, long sceneId) {
            this.playId = playId;
            this.sceneId = sceneId;
        }

        protected ComposeInfo(Parcel in) {
            playId = in.readLong();
            sceneId = in.readLong();
        }

        public static final Creator<ComposeInfo> CREATOR = new Creator<ComposeInfo>() {
            @Override
            public ComposeInfo createFromParcel(Parcel in) {
                return new ComposeInfo(in);
            }

            @Override
            public ComposeInfo[] newArray(int size) {
                return new ComposeInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(playId);
            dest.writeLong(sceneId);
        }
    }

    @Inject
    ComposeScriptAdapter adapter;
    @Inject
    ComposeScriptLayoutManager layoutManager;
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
        ComposeInfo composeInfo = activity.getIntent().getParcelableExtra(COMPOSE_INFO_KEY);
        if(composeInfo != null) {
            viewModel.setPlayAndSceneId(composeInfo.playId, composeInfo.sceneId);
        }
        viewModel.scriptLiveData.observe(activity, new Observer<ComposeScript>() {
            @Override
            public void onChanged(@Nullable ComposeScript keyframe) {
                if(keyframe != null) {
                    adapter.setComposeScript(keyframe);
                }
            }
        });
    }

    @Override
    public void setPlayAndSceneId(long playId, long sceneId) {
        activity.getIntent().putExtra(COMPOSE_INFO_KEY, new ComposeInfo(playId, sceneId));
        viewModel.setPlayAndSceneId(playId, sceneId);
    }
}
