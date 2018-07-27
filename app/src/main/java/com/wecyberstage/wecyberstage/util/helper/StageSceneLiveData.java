package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.UpdateStagePlayInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StageSceneLiveData extends LiveData<StageScene> implements PlayStateInterface, UpdateStagePlayInterface {
    private PlayState playState;
    private StageScene stageScene;
    @Inject
    public StageSceneLiveData(PlayRepository repository) {
        playState = new PlayState(-1,-1,-1);
    }

    @Override
    public void setPlayState(PlayState playState) {
        if( !this.playState.equals(playState) ) {
            this.playState = playState;
            // TODO: 6/29/2018 dummy data below
            stageScene = new StageScene();
            String[] arrIcon = {"http://demo.sc.chinaz.com/Files/pic/icons/6124/10dvs.png",
                    "http://demo.sc.chinaz.com/Files/pic/icons/6124/3dvs.png",
                    "http://demo.sc.chinaz.com/Files/pic/icons/6124/8dvs.png"};
            for(int i = 0; i< 12; i++) {
                MaskGraph maskGraph = new MaskGraph(i%3, 0, arrIcon[i%3]);
                StageLine line = new StageLine(i%3, "Hello " + i, 3 * i * 1000, 1000, maskGraph);
                line.ordinal = i + 1;
                stageScene.stageLines.add(line);
            }
            for(int i=0; i<3; i++) {
                Mask mask = new Mask(i);
                for(int j=0; j<3; j++) {
                    MaskGraph maskGraph = new MaskGraph(i, j, arrIcon[j]);
                    mask.maskGraphList.add(maskGraph);
                }
                StageRole stageRole = new StageRole(i, mask);
                stageScene.stageRoles.add(stageRole);
            }
        }
        setValue(stageScene);
    }

    @Override
    public PlayState getPlayState() {
        return playState;
    }

    @Override
    public void updateStageLine(StageLine stageLine) {
        Log.i("StageSceneLiveData","updateStageLine");
        getValue().stageLines.set((int)stageLine.ordinal - 1, stageLine);
    }
}
