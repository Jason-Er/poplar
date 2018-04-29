package com.wecyberstage.wecyberstage.view.participate;

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
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.CustomViewInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayInterface;
import com.wecyberstage.wecyberstage.view.helper.ViewOnTouch;
import com.wecyberstage.wecyberstage.viewmodel.ParticipateViewModel;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/5.
 */

public class Participate extends CustomView implements CustomViewInterface, PlayInterface {

    private static final String PARTICIPATE_INFO_KEY = "participate_info";

    private ParticipateViewModel viewModel;
    private AppCompatActivity activity;

    static class ParticipateInfo implements Parcelable {
        long playId;
        long sceneId;

        public ParticipateInfo(long playId, long sceneId) {
            this.playId = playId;
            this.sceneId = sceneId;
        }

        protected ParticipateInfo(Parcel in) {
            playId = in.readLong();
            sceneId = in.readLong();
        }

        public static final Creator<ParticipateInfo> CREATOR = new Creator<ParticipateInfo>() {
            @Override
            public ParticipateInfo createFromParcel(Parcel in) {
                return new ParticipateInfo(in);
            }

            @Override
            public ParticipateInfo[] newArray(int size) {
                return new ParticipateInfo[size];
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
    KeyFrameAdapter adapter;
    @Inject
    KeyFrameLayoutManager layoutManager;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        this.activity = activity;
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.frag_participate, container,false);
        viewOnTouch = new ViewOnTouch(view);
        view.setOnTouchListener(viewOnTouch);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);

        ((RecyclerView)view).setHasFixedSize(true);
        ((RecyclerView)view).setLayoutManager(layoutManager);
        ((RecyclerView)view).setAdapter(adapter);

        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(ParticipateViewModel.class);
        ParticipateInfo participateInfo = activity.getIntent().getParcelableExtra(PARTICIPATE_INFO_KEY);
        if(participateInfo != null) {
            viewModel.setPlayAndSceneId(participateInfo.playId, participateInfo.sceneId);
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
    public void setPlayAndSceneId(long playId, long sceneId) {
        activity.getIntent().putExtra(PARTICIPATE_INFO_KEY, new ParticipateInfo(playId, sceneId));
        viewModel.setPlayAndSceneId(playId, sceneId);
    }

}
