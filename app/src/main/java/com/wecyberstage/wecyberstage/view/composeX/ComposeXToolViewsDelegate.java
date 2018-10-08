package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class ComposeXToolViewsDelegate extends ToolViewsDelegate {

    public ComposeXToolViewsDelegate(Activity activity, View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, FloatingActionButton fab) {
        super(activity, toolBar, playerControlBar, lineEditBar, drawerLayout, fab);
    }

    @Override
    public void slideBegin() {
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void slideEnd() {

    }
}
