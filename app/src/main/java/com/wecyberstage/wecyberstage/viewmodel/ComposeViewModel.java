package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.model.UpdateComposeScriptInterface;
import com.wecyberstage.wecyberstage.util.helper.ComposeScriptLiveData;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;

import javax.inject.Inject;

public class ComposeViewModel extends ViewModel implements PlayStateInterface, UpdateComposeScriptInterface {

    public final LiveData<ComposeScript> scriptLiveData;

    @Inject
    public ComposeViewModel(ComposeScriptLiveData scriptLiveData) {
        this.scriptLiveData = scriptLiveData;
    }

    @Override
    public void setPlayState(PlayState playState) {
        ((ComposeScriptLiveData) scriptLiveData).setPlayState(playState);
    }

    @Override
    public PlayState getPlayState() {
        return ((ComposeScriptLiveData) scriptLiveData).getPlayState();
    }

    @Override
    public void updateAvatarLine(ComposeScript.AvatarLine avatarLine, int ordinal) {
        ((ComposeScriptLiveData) scriptLiveData).updateAvatarLine(avatarLine, ordinal);
    }
}
