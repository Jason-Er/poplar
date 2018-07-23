package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.wecyberstage.wecyberstage.model.StagePlayInfo;
import com.wecyberstage.wecyberstage.data.dto.PageRequest;
import com.wecyberstage.wecyberstage.util.helper.PlayInfoLiveData;
import com.wecyberstage.wecyberstage.util.helper.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mike on 2018/3/10.
 */

@Singleton
public class BrowseViewModel extends ViewModel {

    public final LiveData<Resource<List<StagePlayInfo>>> playInfoLiveData;

    @Inject
    public BrowseViewModel(PlayInfoLiveData playInfoLiveData) {
        this.playInfoLiveData = playInfoLiveData;
    }

    public void setRequestPage(PageRequest pageable) {
        ((PlayInfoLiveData)playInfoLiveData).setRequestPage(pageable);
    }
}
