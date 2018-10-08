package com.wecyberstage.wecyberstage.view.helper;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

public abstract class ToolViewsDelegate implements SlideInterface {
    protected Activity activity;
    protected View toolbar;
    protected View playerControlBar;
    protected View lineEditBar;
    protected View drawerLayout;
    protected FloatingActionButton fab;

    public ToolViewsDelegate(Activity activity, View toolbar, View playerControlBar, View lineEditBar, View drawerLayout, FloatingActionButton fab) {
        this.activity = activity;
        this.toolbar = toolbar;
        this.playerControlBar = playerControlBar;
        this.lineEditBar = lineEditBar;
        this.drawerLayout = drawerLayout;
        this.fab = fab;
    }
    
}
