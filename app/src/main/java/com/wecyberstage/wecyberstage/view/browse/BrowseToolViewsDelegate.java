package com.wecyberstage.wecyberstage.view.browse;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class BrowseToolViewsDelegate extends ToolViewsDelegate {

    public BrowseToolViewsDelegate(View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, View floatingActionButton) {
        super(toolBar, playerControlBar, lineEditBar, drawerLayout, floatingActionButton);
    }

    @Override
    public void slideBegin() {
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void slideEnd() {

    }
}
