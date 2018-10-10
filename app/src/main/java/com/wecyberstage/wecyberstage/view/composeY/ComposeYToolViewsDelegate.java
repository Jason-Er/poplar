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
import com.wecyberstage.wecyberstage.view.main.FooterEditBar;
import com.wecyberstage.wecyberstage.view.main.PlayerControlBar;
import com.wecyberstage.wecyberstage.view.message.FABEvent;

import org.greenrobot.eventbus.EventBus;

public class ComposeYToolViewsDelegate extends ToolViewsDelegate implements ClickActionInterface {

    private final String TAG = "ComposeYToolViews";

    public ComposeYToolViewsDelegate(Activity activity,
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
        footerEditBar.setVisibility(View.GONE);
        appBarLayout.setVisibility(View.VISIBLE);
        appBarLayout.setExpanded(true, true);
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
        playerControlBar.animate().translationY(playerControlBar.getHeight()).alpha(0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playerControlBar.setVisibility(View.GONE);
            }
        }).start();

    }

    public void hideLineEditBar() {
        footerEditBar.setVisibility(View.GONE);
        fab.show();
    }

    @Override
    public void itemClick() {
        Log.d(TAG, "itemClick");
        if(footerEditBar.getVisibility() == View.VISIBLE) {
            fab.show();
            footerEditBar.setVisibility(View.INVISIBLE);
        } else {
            fab.hide();
            moveInPlayerControl();
        }
    }

    @Override
    public void containerClick() {

    }
}
