package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.util.helper.ComposeScriptLiveData;

import javax.inject.Inject;

public class ComposeViewModel extends ViewModel {

    public final LiveData<ComposeScript> scriptLiveData;

    @Inject
    public ComposeViewModel(ComposeScriptLiveData scriptLiveData) {
        this.scriptLiveData = scriptLiveData;
    }

    public void setPlayAndSceneId(long playId, long sceneId) {
        ((ComposeScriptLiveData) scriptLiveData).setPlayAndSceneId(playId, sceneId);
    }

}
