package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.ComposeLine;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.model.Line;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.UpdateComposeScriptInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComposeScriptLiveData extends LiveData<ComposeScript> implements PlayStateInterface, UpdateComposeScriptInterface{
    private PlayState playState;
    private ComposeScript composeScript;
    @Inject
    public ComposeScriptLiveData(PlayRepository repository) {
        playState = new PlayState(-1,-1,-1);
    }

    @Override
    public void setPlayState(PlayState playState) {
        if( !this.playState.equals(playState) ) {
            this.playState = playState;
            // TODO: 6/29/2018 dummy data below
            composeScript = new ComposeScript(playState.getPlayId(), playState.getSceneId());
            for(int i = 0; i< 12; i++) {
                MaskGraph maskGraph = new MaskGraph(i%3, 0, "http://www.f1188.com/upload/20180107205142.jpg");
                Line line = new Line(i%3, "Hello " + i, 3 * i * 1000, 1);
                ComposeLine composeLine = new ComposeLine(maskGraph, line);
                composeScript.composeLineList.add(composeLine);
            }
        }
        setValue(composeScript);
    }

    @Override
    public PlayState getPlayState() {
        return playState;
    }

    @Override
    public void updateComposeLine(ComposeLine composeLine, int ordinal) {
        Log.i("ComposeScriptLiveData","updateComposeLine");
        getValue().composeLineList.get(ordinal).maskGraph = composeLine.maskGraph;
        getValue().composeLineList.get(ordinal).line = composeLine.line;
    }
}
