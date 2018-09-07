package com.wecyberstage.wecyberstage.view.composeY;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class ComposeYToolViewsDelegate extends ToolViewsDelegate {

    public ComposeYToolViewsDelegate(View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, View floatingActionButton) {
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
        Log.d("ComposeYToolViews","slideEnd");
        ((FloatingActionButton)floatingActionButton).show();
        toolbar.animate().translationY(toolbar.getHeight())
                .setDuration(300)
                .setInterpolator(new AccelerateInterpolator()).start();
    }
}