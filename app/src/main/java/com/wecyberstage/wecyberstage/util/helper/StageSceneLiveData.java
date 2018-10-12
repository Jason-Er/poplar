package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.StageLineHandle;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursor;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursorHandle;

import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StageSceneLiveData extends LiveData<StageScene> implements StagePlayCursorHandle, StageLineHandle {
    private StagePlayCursor stagePlayCursor;
    private StageScene stageScene;
    @Inject
    public StageSceneLiveData(PlayRepository repository) {
        stagePlayCursor = new StagePlayCursor(-1,-1,-1);
    }

    @Override
    public void setStagePlayCursor(StagePlayCursor stagePlayCursor) {
        if( !this.stagePlayCursor.equals(stagePlayCursor) ) {
            this.stagePlayCursor = stagePlayCursor;
            // TODO: 6/29/2018 dummy data below
            stageScene = new StageScene();
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

        }
        setValue(stageScene);
    }

    @Override
    public StagePlayCursor getStagePlayCursor() {
        return stagePlayCursor;
    }

    @Override
    public void updateStageLine(StageLine stageLine) {
        Log.d("StageSceneLiveData","updateStageLine");
        getValue().stageLines.set((int)stageLine.ordinal - 1, stageLine);
    }

    @Override
    public void addStageLine(StageLine stageLine) {
        Log.d("StageSceneLiveData","deleteStageLine");
        if( stageLine.ordinal > 0 ) {
            getValue().stageLines.add((int)stageLine.ordinal - 1, stageLine);
        } else {
            getValue().stageLines.add(stageLine);
        }
        updateStageLinesOrdinal();
    }

    @Override
    public void deleteStageLine(StageLine stageLine) {
        Log.d("StageSceneLiveData","deleteStageLine");
        getValue().stageLines.remove(stageLine);
        updateStageLinesOrdinal();
    }

    @Override
    public void swapStageLines(int position1, int position2) {
        Log.d("StageSceneLiveData","deleteStageLine");
        Collections.swap(getValue().stageLines, position1, position2);
        updateStageLinesOrdinal();
    }

    private void updateStageLinesOrdinal() {
        for(StageLine stageLine: getValue().stageLines) {
            stageLine.ordinal = getValue().stageLines.indexOf(stageLine) + 1;
        }
    }
}
