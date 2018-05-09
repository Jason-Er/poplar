package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComposeScriptLiveData extends LiveData<ComposeScript> implements PlayStateInterface{
    PlayState playState;
    @Inject
    public ComposeScriptLiveData(PlayRepository repository) {
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
