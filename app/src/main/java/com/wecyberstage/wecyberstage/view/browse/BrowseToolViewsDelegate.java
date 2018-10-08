package com.wecyberstage.wecyberstage.view.browse;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class BrowseToolViewsDelegate extends ToolViewsDelegate {

    public BrowseToolViewsDelegate(Activity activity, View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, FloatingActionButton fab) {
        super(activity, toolBar, playerControlBar, lineEditBar, drawerLayout, fab);
    }

    @Override
    public void slideBegin() {
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        fab.hide();
        lineEditBar.setVisibility(View.INVISIBLE);
        playerControlBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void slideEnd() {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Show status bar
        ((AppBarLayout) toolbar.getParent()).setVisibility(View.VISIBLE);
        ((AppBarLayout) toolbar.getParent()).setExpanded(true, true);
    }
}
