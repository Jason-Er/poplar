package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.ComposeScript;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComposeScriptLiveData extends LiveData<ComposeScript> {

    @Inject
    public ComposeScriptLiveData(PlayRepository repository) {
    }

    public void setPlayAndSceneId(long playId, long sceneId) {

    }
}
