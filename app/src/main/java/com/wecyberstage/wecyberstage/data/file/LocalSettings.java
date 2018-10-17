package com.wecyberstage.wecyberstage.data.file;

import android.content.SharedPreferences;

import com.wecyberstage.wecyberstage.util.helper.SerializeUtils;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalSettings {
    private final SharedPreferences sharedPreferences;
    private final String TOKEN_NAME = "token";
    private final String USER_NAME = "username";
    private final String SOFTKEYBOARD_HEIGHT = "softkeyboardheight";
    private final String STAGEPLAY_CURSOR = "stageplay_cursor";

    @Inject
    public LocalSettings(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_NAME, token);
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_NAME,"");
    }

    public void saveUserName(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, username);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME,"");
    }

    public int getSoftKeyboardHeight() {
        return sharedPreferences.getInt(SOFTKEYBOARD_HEIGHT,0);
    }

    public void saveSoftKeyboardHeight(int height) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SOFTKEYBOARD_HEIGHT, height);
        editor.commit();
    }

    public StagePlayCursor getStagePlayCursor(long userId) {
        String str = sharedPreferences.getString(STAGEPLAY_CURSOR,"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n");
        StagePlayCursor playCursor = SerializeUtils.unmarshall(str, StagePlayCursor.CREATOR);
        return playCursor;
    }

    public void saveStagePlayCursor(long userId, StagePlayCursor stagePlayCursor) {
        String str = SerializeUtils.parcelObject2String(stagePlayCursor);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STAGEPLAY_CURSOR, str);
        editor.commit();
    }
}
