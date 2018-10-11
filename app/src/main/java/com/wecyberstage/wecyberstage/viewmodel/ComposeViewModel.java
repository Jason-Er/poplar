package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageLineHandle;
import com.wecyberstage.wecyberstage.util.helper.StagePlayLiveData;

import javax.inject.Inject;

public class ComposeViewModel extends ViewModel implements StageLineHandle {

    public final StagePlayLiveData stagePlayLiveData;

    @Inject
    public ComposeViewModel(StagePlayLiveData stagePlayLiveData) {
        this.stagePlayLiveData = stagePlayLiveData;
    }

    public void getStagePlay(long stagePlayId) {
        stagePlayLiveData.getStagePlay(stagePlayId);
    }

    @Override
    public void updateStageLine(StageLine maskLine) {

    }

    @Override
    public void addStageLine(StageLine stageLine) {

    }

    @Override
    public void deleteStageLine(StageLine stageLine) {

    }

    @Override
    public void swapStageLines(int position1, int position2) {

    }

}
