package com.wecyberstage.wecyberstage.view.account;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class SignUpToolViewsDelegate extends ToolViewsDelegate {

    public SignUpToolViewsDelegate(View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, View floatingActionButton) {
        super(toolBar, playerControlBar, lineEditBar, drawerLayout, floatingActionButton);
    }

    @Override
    public void slideBegin() {
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ((FloatingActionButton)floatingActionButton).hide();
        lineEditBar.setVisibility(View.INVISIBLE);
        playerControlBar.setVisibility(View.GONE);
        ((AppBarLayout) toolbar.getParent()).setExpanded(false, false);
        ((AppBarLayout) toolbar.getParent()).setVisibility(View.GONE);
    }

    @Override
    public void slideEnd() {

    }
}