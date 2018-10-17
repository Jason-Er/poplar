package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

import com.wecyberstage.wecyberstage.data.dto.PageRequest;
import com.wecyberstage.wecyberstage.data.repository.StagePlayRepository;
import com.wecyberstage.wecyberstage.model.StagePlayInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mike on 2018/3/14.
 */

@Singleton
public class PlayInfoLiveData extends LiveData<Resource<List<StagePlayInfo>>> {

    @Inject
    public PlayInfoLiveData(StagePlayRepository repository) {

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
        List<StagePlayInfo> stagePlayInfoList = new ArrayList<>();
        StagePlayInfo stagePlayInfo1 = new StagePlayInfo();
        stagePlayInfo1.name = "Adventures of Huckleberry Finn";
        stagePlayInfo1.briefIntro = "a boy's story";
        stagePlayInfo1.posterURL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521049025342&di=b8152a98cb6aee22fc5cdd20e2562bbb&imgtype=0&src=http%3A%2F%2Fwww.kfzimg.com%2FG05%2FM00%2F52%2F01%2Fp4YBAFfXiF2ASrT7AALI-jIjL0M031_b.jpg";
        StagePlayInfo stagePlayInfo2 = new StagePlayInfo();
        stagePlayInfo2.name = "Blue whale";
        stagePlayInfo2.briefIntro = "a big mammal";
        stagePlayInfo2.posterURL = "http://pic28.photophoto.cn/20130727/0035035114302168_b.jpg";
        stagePlayInfoList.add(stagePlayInfo1);
        stagePlayInfoList.add(stagePlayInfo2);
        Resource<List<StagePlayInfo>> a = Resource.success(stagePlayInfoList);
        setValue(a);
    }
}
