package com.wecyberstage.wecyberstage.view.account;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.main.FooterEditBar;
import com.wecyberstage.wecyberstage.view.main.PlayerControlBar;

public class SignUpToolViewsDelegate extends ToolViewsDelegate {

    public SignUpToolViewsDelegate(Activity activity, AppBarLayout appBarLayout, PlayerControlBar playerControlBar, FooterEditBar footerEditBar, DrawerLayout drawerLayout, FloatingActionButton fab) {
        super(activity, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
    }

    @Override
    public void slideBegin() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        fab.hide();
        footerEditBar.setVisibility(View.INVISIBLE);
        playerControlBar.setVisibility(View.GONE);
        appBarLayout.setExpanded(false, false);
        appBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void slideEnd() {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Show status bar
    }
}
