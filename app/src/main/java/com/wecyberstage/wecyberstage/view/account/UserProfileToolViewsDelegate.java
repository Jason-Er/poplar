package com.wecyberstage.wecyberstage.view.account;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class UserProfileToolViewsDelegate extends ToolViewsDelegate {

    public UserProfileToolViewsDelegate(Activity activity, View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, FloatingActionButton fab) {
        super(activity, toolBar, playerControlBar, lineEditBar, drawerLayout, fab);
    }

    @Override
    public void slideBegin() {
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        fab.hide();
        lineEditBar.setVisibility(View.INVISIBLE);
        playerControlBar.setVisibility(View.GONE);
        ((AppBarLayout) toolbar.getParent()).setExpanded(false, false);
        ((AppBarLayout) toolbar.getParent()).setVisibility(View.GONE);
    }

    @Override
    public void slideEnd() {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Show status bar
    }
}
