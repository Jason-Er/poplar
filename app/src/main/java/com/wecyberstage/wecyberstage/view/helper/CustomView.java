package com.wecyberstage.wecyberstage.view.helper;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public abstract class CustomView implements SlideInterface {

    protected View view;
    protected ViewType viewType;
    protected ToolViewsDelegate toolViewsDelegate;
    protected AppCompatActivity activity;
    public CustomView(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType,
                      ToolViewsDelegate toolViewsDelegate) {
        this.activity = activity;
        this.viewType = viewType;
        this.toolViewsDelegate = toolViewsDelegate;
        onCreate(activity, container);
    }

    public abstract void onCreate(AppCompatActivity activity, @Nullable ViewGroup container);
    public abstract void onStop(AppCompatActivity activity, @Nullable ViewGroup container);

    public void becomeVisible() {
        view.setVisibility(View.VISIBLE);
    }
    public void becomeInvisible() {
        view.setVisibility(View.INVISIBLE);
    }

    public View getView() {
        return view;
    }
    public ViewType getViewType() {
        return viewType;
    }

    @Override
    public void slideBegin() {
        toolViewsDelegate.slideBegin();
    }

    @Override
    public void slideEnd() {
        toolViewsDelegate.slideEnd();
    }

}
