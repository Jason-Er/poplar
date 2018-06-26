package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComposeScriptLiveData extends LiveData<ComposeScript> implements PlayStateInterface{
    PlayState playState;
    @Inject
    public ComposeScriptLiveData(PlayRepository repository) {
    }

    @Override
    public void setPlayState(PlayState playState) {
        this.playState = playState;
        ComposeScript composeScript = new ComposeScript(playState.getPlayId(), playState.getSceneId());
        for(int i = 0; i< 12; i++) {
            ComposeScript.Avatar avatar = new ComposeScript.Avatar(i%3, 0, "http://www.f1188.com/upload/20180107205142.jpg");
            ComposeScript.Line line = new ComposeScript.Line(i%3, "Hello " + i, 3f * i, 1);
            ComposeScript.Avatar_Line avatarLine = new ComposeScript.Avatar_Line(avatar, line);
            composeScript.avatarLines.add(avatarLine);
        }
        setValue(composeScript);
    }

    @Override
    public PlayState getPlayState() {
        return playState;
    }
}
