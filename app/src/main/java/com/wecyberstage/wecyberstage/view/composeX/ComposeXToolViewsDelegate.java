package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.main.FooterEditBar;
import com.wecyberstage.wecyberstage.view.main.PlayerControlBar;

public class ComposeXToolViewsDelegate extends ToolViewsDelegate {

    public ComposeXToolViewsDelegate(Activity activity,
                                     AppBarLayout appBarLayout,
                                     PlayerControlBar playerControlBar,
                                     FooterEditBar footerEditBar,
                                     DrawerLayout drawerLayout,
                                     FloatingActionButton fab) {
        super(activity, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
    }

    @Override
    public void slideBegin() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void slideEnd() {

    }
}
