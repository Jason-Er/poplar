package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.wecyberstage.wecyberstage.data.repository.StagePlayRepository;
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.Prop;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageLineHandle;
import com.wecyberstage.wecyberstage.model.StagePlay;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.StageSceneHandle;
import com.wecyberstage.wecyberstage.view.helper.PlayControlInterface;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursor;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursorHandle;

import java.util.ArrayList;

import javax.inject.Inject;

public class StagePlayViewModel extends ViewModel
        implements StageLineHandle, StageSceneHandle, StagePlayCursorHandle,
        PlayControlInterface {

    private final String TAG = "StagePlayViewModel";

    private StagePlayCursor stagePlayCursor;
    private final StagePlayRepository repository;

    private final MutableLiveData<Long> stagePlayId = new MutableLiveData<>();
    private final MutableLiveData<Integer> stageSceneOrdinal = new MutableLiveData<>();
    private final MutableLiveData<Integer> stageLineOrdinal = new MutableLiveData<>();
    private final MutableLiveData<Long> startTime = new MutableLiveData<>();

    private final MutableLiveData<StagePlay> stagePlayMutableLiveData = new MutableLiveData<>();
    public final LiveData<StagePlay> stagePlay = Transformations.switchMap(stagePlayId, new Function<Long, LiveData<StagePlay>>() {
        @Override
        public LiveData<StagePlay> apply(Long input) {
            Log.d(TAG, "LiveData<StagePlay> apply");
            // TODO: 10/15/2018 use repository instead
            StagePlay stagePlay = produceStagePlayDummy();
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
            stageSceneMutableLiveData.setValue(stagePlay.getValue().scenes.get(stageSceneOrdinal.getValue()));
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
    private final MutableLiveData<KeyFrame> keyFrameMutableLiveData = new MutableLiveData<>();
    public final LiveData<KeyFrame> keyFrame = Transformations.switchMap(startTime, new Function<Long, LiveData<KeyFrame>>() {
        @Override
        public LiveData<KeyFrame> apply(Long input) {
            keyFrameMutableLiveData.setValue(produceKeyFrameDummy());
            return keyFrameMutableLiveData;
        }
    });

    @Inject
    public StagePlayViewModel(StagePlayRepository repository) {
        this.repository = repository;

    }

    // region implement of StageLineHandle and StageSceneHandle
    @Override
    public void updateStageLine(StageLine maskLine) {

    }

    @Override
    public void addStageLine(StageLine stageLine) {
        if( stageScene.getValue().stageLines.contains(stageLine) ) {
            stageLineOrdinal.setValue(stageScene.getValue().stageLines.indexOf(stageLine));
        } else {
            stageScene.getValue().stageLines.add(stageLineOrdinal.getValue()+1, stageLine);
            refreshStageScene();
            stageLineOrdinal.setValue(stageLineOrdinal.getValue()+1);
        }
    }

    @Override
    public void deleteStageLine(StageLine stageLine) {

    }

    @Override
    public void swapStageLines(int position1, int position2) {

    }

    @Override
    public void deleteStageSceneContent() {
        stageScene.getValue().stageLines = new ArrayList<>();
        refreshStageScene();
    }

    @Override
    public void deleteStageScene() {
        int position = stagePlay.getValue().scenes.indexOf(stageScene.getValue());
        if(position > 0) {
            stagePlay.getValue().scenes.remove(position);
            stageSceneOrdinal.setValue(position - 1);
        } else { // is the first scene
            if( stageScene.getValue().stageLines.size() != 0 ) {
                stagePlay.getValue().scenes.remove(0);
                stagePlay.getValue().scenes.add(new StageScene());
                stageSceneOrdinal.setValue(0);
            }
        }
    }

    @Override
    public void addStageScene() {
        if( stageScene.getValue().stageLines.size() != 0 ) {
            Log.d(TAG, "addStageScene");
            int position = stagePlay.getValue().scenes.indexOf(stageScene.getValue());
            StageScene stageScene = new StageScene();
            stagePlay.getValue().scenes.add(position + 1, stageScene);
            stageSceneOrdinal.setValue(position + 1);
        }
    }
    // endregion

    // region implement of StagePlayCursorHandle
    @Override
    public void setStagePlayCursor(StagePlayCursor stagePlayCursor) {
        this.stagePlayCursor = stagePlayCursor;
        stagePlayId.setValue(stagePlayCursor.getPlayId());
        stageSceneOrdinal.setValue(stagePlayCursor.getSceneOrdinal());
        stageLineOrdinal.setValue(stagePlayCursor.getLineOrdinal());
        startTime.setValue(stagePlayCursor.getStartTime());
    }

    @Override
    public StagePlayCursor getStagePlayCursor() {
        return stagePlayCursor;
    }
    // endregion

    // TODO: 10/15/2018 may remove such dummy function later
    private StagePlay produceStagePlayDummy() {
        StagePlay stagePlay = new StagePlay();
        stagePlay.scenes = new ArrayList<>();
        StageScene stageScene = new StageScene();
        stageScene.name = "Scene One";
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
        // add another scene
        stageScene = new StageScene();
        stageScene.name = "Scene Two";
        for(int i = 0; i< 12; i++) {
            StageLine line = new StageLine(stagePlay.cast.get((i+1)%3), "Hi " + i, 3 * i * 1000, 1000, i%3);
            line.ordinal = i + 1;
            stageScene.stageLines.add(line);
        }
        stagePlay.scenes.add(stageScene);
        return stagePlay;
    }

    private KeyFrame produceKeyFrameDummy() {
        KeyFrame keyFrame = new KeyFrame();

        keyFrame.roleInfoList = new ArrayList<>();
        keyFrame.lineInfoList = new ArrayList<>();
        keyFrame.propInfoList = new ArrayList<>();
        keyFrame.stageInfo = new KeyFrame.StageInfo();

        StageRole stageRole = new StageRole(1, "", null);
        stageRole.name = "jason";
        StageLine line = new StageLine();
        line.dialogue = "Hello world!";
        PointF point = new PointF(200,200);
        RectF roleViewRect = new RectF(0.2f,0.2f,0.4f,0.6f);

        Prop prop = new Prop();
        RectF propViewRect = new RectF(0,0,40,40);

        KeyFrame.RoleInfo roleInfo = new KeyFrame.RoleInfo(stageRole, roleViewRect,0);
        KeyFrame.LineInfo lineInfo = new KeyFrame.LineInfo(point, line);
        KeyFrame.PropInfo propInfo = new KeyFrame.PropInfo(prop, propViewRect);

        keyFrame.roleInfoList.add(roleInfo);
        keyFrame.lineInfoList.add(lineInfo);
        keyFrame.propInfoList.add(propInfo);
        keyFrame.stageInfo.settingURL = "http://pic28.photophoto.cn/20130727/0035035114302168_b.jpg";
        return keyFrame;
    }

    public void refreshStageLine() {
        stageLineOrdinal.setValue(stageLineOrdinal.getValue());
    }

    public void refreshStageScene() {
        stageSceneOrdinal.setValue(stageSceneOrdinal.getValue());
    }
    // region implement of PlayControlInterface
    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void seek(float percent) {

    }

    @Override
    public void pre() {
        Log.d(TAG,"PlayControlInterface pre()");
        if( stagePlay.getValue() != null && stagePlay.getValue().scenes.size() > 0 ) {
            if(stageSceneOrdinal.getValue() - 1 >= 0) {
                stagePlayCursor.setSceneOrdinal(stageSceneOrdinal.getValue() - 1);
                stageSceneOrdinal.setValue(stageSceneOrdinal.getValue() - 1);
            }
        }
    }

    @Override
    public void next() {
        Log.d(TAG,"PlayControlInterface next()");
        if( stagePlay.getValue() != null && stagePlay.getValue().scenes.size() > 0 ) {
            if(stagePlay.getValue().scenes.size() > stageSceneOrdinal.getValue() + 1) {
                stagePlayCursor.setSceneOrdinal(stageSceneOrdinal.getValue() + 1);
                stageSceneOrdinal.setValue(stageSceneOrdinal.getValue() + 1);
            }
        }
    }

    @Override
    public void volume(boolean open) {

    }
    // endregion
}