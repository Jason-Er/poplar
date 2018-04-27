package com.wecyberstage.wecyberstage.view.helper;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public interface CustomViewInterface {
    void onCreate(AppCompatActivity activity, @Nullable ViewGroup container, Bundle savedInstanceState);
}
