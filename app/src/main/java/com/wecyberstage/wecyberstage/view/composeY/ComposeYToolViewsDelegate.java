package com.wecyberstage.wecyberstage.view.composeY;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;

import com.wecyberstage.wecyberstage.view.helper.ClickActionInterface;
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.message.FABEvent;

import org.greenrobot.eventbus.EventBus;

public class ComposeYToolViewsDelegate extends ToolViewsDelegate implements ClickActionInterface {

    private final String TAG = "ComposeYToolViews";
    public ComposeYToolViewsDelegate(Activity activity, View toolBar, View playerControlBar, View lineEditBar, View drawerLayout, FloatingActionButton fab) {
        super(activity, toolBar, playerControlBar, lineEditBar, drawerLayout, fab);
    }

    @Override
    public void slideBegin() {
        ((DrawerLayout)drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        fab.hide();
        lineEditBar.setVisibility(View.GONE);
    }

    @Override
    public void slideEnd() {
        Log.d(TAG,"slideEnd");
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.hide();
                FABEvent event = new FABEvent("click");
                EventBus.getDefault().post(event);
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

    public void hideLineEditBar() {
        lineEditBar.setVisibility(View.GONE);
        fab.show();
    }

    @Override
    public void itemClick() {
        Log.d(TAG, "itemClick");
        if(lineEditBar.getVisibility() == View.VISIBLE) {
            fab.show();
            lineEditBar.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void containerClick() {

    }
}
