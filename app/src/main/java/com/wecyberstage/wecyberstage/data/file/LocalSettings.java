package com.wecyberstage.wecyberstage.data.file;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalSettings {
    private final SharedPreferences sharedPreferences;
    private final String TOKEN_NAME = "token";
    private final String USER_NAME = "username";
    private final String SOFTKEYBOARD_HEIGHT = "softkeyboardheight";


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
}
