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

public class SignInToolViewsDelegate extends ToolViewsDelegate {

    public SignInToolViewsDelegate(Activity activity, AppBarLayout appBarLayout, PlayerControlBar playerControlBar, FooterEditBar footerEditBar, DrawerLayout drawerLayout, FloatingActionButton fab) {
        super(activity, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
    }

    @Override
    public void slideBegin() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        fab.hide();
        footerEditBar.setVisibility(View.INVISIBLE);
        playerControlBar.setVisibility(View.GONE);
        // AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) appBarLayout.getLayoutParams();
        // params.setScrollFlags(0);
        // appBarLayout.animate().translationY(-appBarLayout.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        // appBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
        // appBarLayout.setVisibility(View.INVISIBLE);
        appBarLayout.setExpanded(false, false);
        appBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void slideEnd() {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Show status bar
    }
}
