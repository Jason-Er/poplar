package com.wecyberstage.wecyberstage.view.composeY;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class ComposeYToolViewsDelegate extends ToolViewsDelegate {

    public ComposeYToolViewsDelegate(Activity activity, View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, View floatingActionButton) {
        super(activity, toolBar, playerControlBar, lineEditBar, drawerLayout, floatingActionButton);
    }

    @Override
    public void slideBegin() {
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        ((FloatingActionButton)floatingActionButton).hide();
        lineEditBar.setVisibility(View.GONE);
    }

    @Override
    public void slideEnd() {
        Log.d("ComposeYToolViews","slideEnd");
        ((FloatingActionButton)floatingActionButton).show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FloatingActionButton)floatingActionButton).hide();
                lineEditBar.setVisibility(View.VISIBLE);
            }
        });
        ((AppBarLayout) toolbar.getParent()).setExpanded(false, true);
        ((AppBarLayout) toolbar.getParent()).setVisibility(View.GONE);
        playerControlBar.animate().translationY(playerControlBar.getHeight()).alpha(0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playerControlBar.setVisibility(View.GONE);
            }
        }).start();


    }
}
