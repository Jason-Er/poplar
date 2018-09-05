package com.wecyberstage.wecyberstage.view.helper;

import android.view.View;

public abstract class ToolViewsDelegate implements SlideInterface {
    protected View toolBar;
    protected View playerControlBar;
    protected View lineEditBar;
    protected View drawerLayout;
    protected View floatingActionButton;
    public ToolViewsDelegate(View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, View floatingActionButton) {
        this.toolBar = toolBar;
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
