package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.UpdateStagePlayInterface;
import com.wecyberstage.wecyberstage.util.helper.StageSceneLiveData;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;

import javax.inject.Inject;

public class ComposeViewModel extends ViewModel implements PlayStateInterface, UpdateStagePlayInterface {

    public final LiveData<StageScene> stageSceneLiveData;

    @Inject
    public ComposeViewModel(StageSceneLiveData stageSceneLiveData) {
        this.stageSceneLiveData = stageSceneLiveData;
    }

    @Override
    public void setPlayState(PlayState playState) {
        ((StageSceneLiveData) stageSceneLiveData).setPlayState(playState);
    }

    @Override
    public PlayState getPlayState() {
        return ((StageSceneLiveData) stageSceneLiveData).getPlayState();
    }

    @Override
    public void updateStageLine(StageLine maskLine) {
        ((StageSceneLiveData) stageSceneLiveData).updateStageLine(maskLine);
    }
}
