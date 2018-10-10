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

import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.main.FooterEditBar;
import com.wecyberstage.wecyberstage.view.main.PlayerControlBar;

public class ComposeZToolViewsDelegate extends ToolViewsDelegate {

    public ComposeZToolViewsDelegate(Activity activity,
                                     AppBarLayout appBarLayout,
                                     PlayerControlBar playerControlBar,
                                     FooterEditBar footerEditBar,
                                     DrawerLayout drawerLayout,
                                     FloatingActionButton fab) {
        super(activity, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
    }

    @Override
    public void slideBegin() {
        Log.d("ComposeZTool","slideEnd");
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        fab.hide();
        footerEditBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void slideEnd() {
        Log.d("ComposeZTool","slideEnd");
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide status bar
        appBarLayout.setExpanded(false, true);
        appBarLayout.setVisibility(View.GONE);
        playerControlBar.animate().translationY(playerControlBar.getHeight()).alpha(0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playerControlBar.setVisibility(View.GONE);
            }
        }).start();
    }
}
