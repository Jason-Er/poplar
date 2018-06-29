package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.ComposeScript;
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
                ComposeScript.Avatar avatar = new ComposeScript.Avatar(i%3, 0, "http://www.f1188.com/upload/20180107205142.jpg");
                ComposeScript.Line line = new ComposeScript.Line(i%3, "Hello " + i, 3f * i, 1);
                ComposeScript.AvatarLine avatarLine = new ComposeScript.AvatarLine(avatar, line);
                composeScript.avatarLines.add(avatarLine);
            }
        }
        setValue(composeScript);
    }

    @Override
    public PlayState getPlayState() {
        return playState;
    }

    @Override
    public void updateAvatarLine(ComposeScript.AvatarLine avatarLine, int ordinal) {
        Log.i("ComposeScriptLiveData","updateAvatarLine");
        getValue().avatarLines.get(ordinal).setAvatar(avatarLine.getAvatar());
        getValue().avatarLines.get(ordinal).setLine(avatarLine.getLine());
    }
}
