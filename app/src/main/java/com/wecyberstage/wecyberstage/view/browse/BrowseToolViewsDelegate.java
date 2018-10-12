package com.wecyberstage.wecyberstage.view.browse;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.main.FooterEditBar;
import com.wecyberstage.wecyberstage.view.main.PlayerControlBar;

public class BrowseToolViewsDelegate extends ToolViewsDelegate {

    public BrowseToolViewsDelegate(Activity activity, AppBarLayout appBarLayout, PlayerControlBar playerControlBar, FooterEditBar footerEditBar, DrawerLayout drawerLayout, FloatingActionButton fab) {
        super(activity, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
    }

    @Override
    public void slideBegin() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        fab.hide();
        footerEditBar.setVisibility(View.INVISIBLE);
        playerControlBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void slideEnd() {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Show status bar
        appBarLayout.setVisibility(View.VISIBLE);
        appBarLayout.setExpanded(true, true);
    }
}
