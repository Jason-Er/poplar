package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.ArrayMap;

import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.util.helper.KeyFrameLiveData;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/15.
 */

public class ParticipateViewModel extends ViewModel implements PlayStateInterface {

    public final LiveData<KeyFrame> keyFrameLiveData;

    @Inject
    public ParticipateViewModel(KeyFrameLiveData keyFrameLiveData) {
        this.keyFrameLiveData = keyFrameLiveData;
    }

    @Override
    public void setPlayState(PlayState playState) {
        ((KeyFrameLiveData) keyFrameLiveData).setPlayState(playState);
    }

    @Override
    public PlayState getPlayState() {
        return ((KeyFrameLiveData) keyFrameLiveData).getPlayState();
    }

}
