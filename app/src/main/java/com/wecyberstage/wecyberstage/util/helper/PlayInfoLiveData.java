package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.PlayInfo;

import java.util.ArrayList;
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

    public void setRequestPage(PageRequest pageable) {
        // TODO: 2018/3/14 below is dummy data
        List<PlayInfo> playInfoList = new ArrayList<>();
        PlayInfo playInfo1 = new PlayInfo();
        playInfo1.name = "Adventures of Huckleberry Finn";
        playInfo1.briefIntro = "a boy's story";
        PlayInfo playInfo2 = new PlayInfo();
        playInfo2.name = "Blue whale";
        playInfo2.briefIntro = "a big mammal";
        playInfoList.add(playInfo1);
        playInfoList.add(playInfo2);
        Resource<List<PlayInfo>> a = Resource.success(playInfoList);
        setValue(a);
    }
}
