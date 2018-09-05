package com.wecyberstage.wecyberstage.view.account;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class UserProfileToolViewsDelegate extends ToolViewsDelegate {

    public UserProfileToolViewsDelegate(View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, View floatingActionButton) {
        super(toolBar, playerControlBar, lineEditBar, drawerLayout, floatingActionButton);
    }

    @Override
    public void beforeEnterMain() {
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void afterEnterMain() {

    }
}
