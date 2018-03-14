package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.PlayInfo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mike on 2018/3/14.
 */

@Singleton
public class PlayInfoLiveData extends LiveData<Resource<List<PlayInfo>>> {

    @Inject
    public PlayInfoLiveData(PlayRepository repository) {

    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    public void pudatePage(PageRequest pageable) {
        setValue(null);
    }
}
