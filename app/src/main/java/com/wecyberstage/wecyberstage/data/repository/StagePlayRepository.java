package com.wecyberstage.wecyberstage.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.wecyberstage.wecyberstage.model.StagePlay;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mike on 2018/3/13.
 */

@Singleton
public class StagePlayRepository {
    @Inject
    public StagePlayRepository() {

    }
    public LiveData<StagePlay> getStagePlay(final long stagePlayId) {
        return new MutableLiveData<>();
    }
}
