package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.util.helper.KeyFrameLiveData;
import com.wecyberstage.wecyberstage.view.helper.PlayInterface;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/15.
 */

public class ParticipateViewModel extends ViewModel implements PlayInterface {

    public final LiveData<KeyFrame> keyFrameLiveData;

    @Inject
    public ParticipateViewModel(KeyFrameLiveData keyFrameLiveData) {
        this.keyFrameLiveData = keyFrameLiveData;
    }

    @Override
    public void setPlayAndSceneId(long playId, long sceneId) {
        ((KeyFrameLiveData) keyFrameLiveData).setPlayAndSceneId(playId, sceneId);
    }

}
