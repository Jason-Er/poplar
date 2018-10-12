package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StagePlay;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.model.StageScene;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StagePlayLiveData extends LiveData<StagePlay> {

    @Inject
    public StagePlayLiveData(PlayRepository repository) {
    }

    public void getStagePlay(long playId) {
        // TODO: 6/29/2018 dummy data below need fetch StagePlay according to playId
        StagePlay stagePlay = new StagePlay();
        stagePlay.scenes = new ArrayList<>();
        StageScene stageScene = new StageScene();
        stageScene.ordinal = 1;
        stageScene.actionScriptId = 1;
        String[] arrIcon = {"http://demo.sc.chinaz.com/Files/pic/icons/6124/10dvs.png",
                "http://demo.sc.chinaz.com/Files/pic/icons/6124/3dvs.png",
                "http://demo.sc.chinaz.com/Files/pic/icons/6124/8dvs.png"};
        for(int i=0; i<3; i++) {
            Mask mask = new Mask(i);
            for(int j=0; j<3; j++) {
                MaskGraph maskGraph = new MaskGraph(i, arrIcon[j]);
                mask.maskGraphList.add(maskGraph);
            }
            StageRole stageRole = new StageRole(i, ""+i, mask);
            stageScene.stageRoles.add(stageRole);
        }
        for(int i = 0; i< 12; i++) {
            StageLine line = new StageLine(stageScene.stageRoles.get(i%3), "Hello " + i, 3 * i * 1000, 1000, i%3);
            line.ordinal = i + 1;
            stageScene.stageLines.add(line);
        }
        stagePlay.scenes.add(stageScene);
        stagePlay.cast = stageScene.stageRoles;
        setValue(stagePlay);
    }
}
