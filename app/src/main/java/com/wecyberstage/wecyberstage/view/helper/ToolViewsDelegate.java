package com.wecyberstage.wecyberstage.view.helper;

import android.app.Activity;
import android.view.View;

public abstract class ToolViewsDelegate implements SlideInterface {
    protected Activity activity;
    protected View toolbar;
    protected View playerControlBar;
    protected View lineEditBar;
    protected View drawerLayout;
    protected View floatingActionButton;

    public ToolViewsDelegate(Activity activity, View toolbar, View playerControlBar, View lineEditBar, View drawerLayout, View floatingActionButton) {
        this.activity = activity;
        this.toolbar = toolbar;
        this.playerControlBar = playerControlBar;
        this.lineEditBar = lineEditBar;
        this.drawerLayout = drawerLayout;
        this.floatingActionButton = floatingActionButton;
    }

    public View getFloatingActionButton() {
        return floatingActionButton;
    }

    public void showLineEditBar() {
        lineEditBar.setVisibility(View.VISIBLE);
    }
}
