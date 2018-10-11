package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.util.helper.KeyFrameLiveData;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursor;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursorHandle;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/15.
 */

public class ParticipateViewModel extends ViewModel implements StagePlayCursorHandle {

    public final LiveData<KeyFrame> keyFrameLiveData;

    @Inject
    public ParticipateViewModel(KeyFrameLiveData keyFrameLiveData) {
        this.keyFrameLiveData = keyFrameLiveData;
    }

    @Override
    public void setStagePlayCursor(StagePlayCursor stagePlayCursor) {
        ((KeyFrameLiveData) keyFrameLiveData).setStagePlayCursor(stagePlayCursor);
    }

    @Override
    public StagePlayCursor getStagePlayCursor() {
        return ((KeyFrameLiveData) keyFrameLiveData).getStagePlayCursor();
    }

}
