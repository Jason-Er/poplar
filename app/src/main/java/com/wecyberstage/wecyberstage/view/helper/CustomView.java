package com.wecyberstage.wecyberstage.view.helper;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public abstract class CustomView {
    public View view;
    public CustomView() {

    }
    public CustomView(View view) {
        this.view = view;
    }
    public abstract void onCreate(AppCompatActivity activity, @Nullable ViewGroup container);
}
