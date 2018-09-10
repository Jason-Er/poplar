package com.wecyberstage.wecyberstage.view.composeZ;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;

public class ComposeZToolViewsDelegate extends ToolViewsDelegate {

    public ComposeZToolViewsDelegate(Activity activity, View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, View floatingActionButton) {
        super(activity, toolBar, playerControlBar, lineEditBar, drawerLayout, floatingActionButton);
    }

    @Override
    public void slideBegin() {
        Log.d("ComposeZTool","slideEnd");
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        ((FloatingActionButton)floatingActionButton).hide();
        lineEditBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void slideEnd() {
        Log.d("ComposeZTool","slideEnd");
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide status bar
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
