package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class ComposeZToolViewsDelegate extends ToolViewsDelegate {

    public ComposeZToolViewsDelegate(View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, View floatingActionButton) {
        super(toolBar, playerControlBar, lineEditBar, drawerLayout, floatingActionButton);
    }

    @Override
    public void slideBegin() {
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        ((FloatingActionButton)floatingActionButton).hide();
        lineEditBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void slideEnd() {

    }
}
