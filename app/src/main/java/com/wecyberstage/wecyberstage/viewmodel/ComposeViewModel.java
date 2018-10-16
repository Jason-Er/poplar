package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.wecyberstage.wecyberstage.data.repository.StagePlayRepository;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageLineHandle;
import com.wecyberstage.wecyberstage.model.StagePlay;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursor;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursorHandle;

import java.util.ArrayList;

import javax.inject.Inject;

public class ComposeViewModel extends ViewModel
        implements StageLineHandle, StagePlayCursorHandle {

    private final String TAG = "ComposeViewModel";

    private final StagePlayRepository repository;

    private final MutableLiveData<Long> stagePlayId = new MutableLiveData<>();
    private final MutableLiveData<Integer> stageSceneOrdinal = new MutableLiveData<>();
    private final MutableLiveData<Integer> stageLineOrdinal = new MutableLiveData<>();

    private final MutableLiveData<StagePlay> stagePlayMutableLiveData = new MutableLiveData<>();
    public final LiveData<StagePlay> stagePlay = Transformations.switchMap(stagePlayId, new Function<Long, LiveData<StagePlay>>() {
        @Override
        public LiveData<StagePlay> apply(Long input) {
            Log.d(TAG, "LiveData<StagePlay> apply");
            // TODO: 10/15/2018 use repository instead
            StagePlay stagePlay = produceDummy();
            stagePlayMutableLiveData.setValue(stagePlay);
            /*
            StageScene stageScene = stagePlay.scenes.get(0);
            stageSceneMutableLiveData.setValue(stageScene);
            StageLine stageLine = stageScene.stageLines.get(0);
            stageLineMutableLiveData.setValue(stageLine);
            */
            return stagePlayMutableLiveData;
        }
    });
    private final MutableLiveData<StageScene> stageSceneMutableLiveData = new MutableLiveData<>();
    public final LiveData<StageScene> stageScene = Transformations.switchMap(stageSceneOrdinal, new Function<Integer, LiveData<StageScene>>() {
        @Override
        public LiveData<StageScene> apply(Integer input) {
            Log.d(TAG, "LiveData<StageScene> apply");
            if( stagePlay.getValue()!=null ) {
                stageSceneMutableLiveData.setValue(stagePlay.getValue().scenes.get(stageSceneOrdinal.getValue()));
            }
            return stageSceneMutableLiveData;
        }
    });
    private final MutableLiveData<StageLine> stageLineMutableLiveData = new MutableLiveData<>();
    public final LiveData<StageLine> stageLine = Transformations.switchMap(stageLineOrdinal, new Function<Integer, LiveData<StageLine>>() {
        @Override
        public LiveData<StageLine> apply(Integer input) {
            return Transformations.switchMap(stageScene, new Function<StageScene, LiveData<StageLine>>() {
                @Override
                public LiveData<StageLine> apply(StageScene input) {
                    Log.d(TAG, "LiveData<StageLine> apply");
                    stageLineMutableLiveData.setValue(input.stageLines.get(stageLineOrdinal.getValue()));
                    return stageLineMutableLiveData;
                }
            });
        }
    });

    @Inject
    public ComposeViewModel(StagePlayRepository repository) {
        this.repository = repository;

    }

    // region implement of StageLineHandle
    @Override
    public void updateStageLine(StageLine maskLine) {

    }

    @Override
    public void addStageLine(StageLine stageLine) {

    }

    @Override
    public void deleteStageLine(StageLine stageLine) {

    }

    @Override
    public void swapStageLines(int position1, int position2) {

    }
    // endregion

    // region implement of StagePlayCursorHandle
    @Override
    public void setStagePlayCursor(StagePlayCursor stagePlayCursor) {
        stagePlayId.setValue(stagePlayCursor.getPlayId());
        stageSceneOrdinal.setValue(stagePlayCursor.getSceneOrdinal());
        stageLineOrdinal.setValue(stagePlayCursor.getLineOrdinal());
    }

    @Override
    public StagePlayCursor getStagePlayCursor() {
        return null;
    }
    // endregion

    // TODO: 10/15/2018 may remove such dummy function later
    private StagePlay produceDummy() {
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
        return stagePlay;
    }

}
