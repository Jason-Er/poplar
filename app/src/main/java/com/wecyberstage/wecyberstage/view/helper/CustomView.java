package com.wecyberstage.wecyberstage.view.helper;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public abstract class CustomView implements LifeCycle {

    protected View view;
    protected ViewType viewType;
    public CustomView(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType) {
        onCreate(activity, container);
        this.viewType = viewType;
    }

    public abstract void onCreate(AppCompatActivity activity, @Nullable ViewGroup container);

    public View getView() {
        return view;
    }
    public ViewType getViewType() {
        return viewType;
    }
}
