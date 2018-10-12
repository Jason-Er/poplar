package com.wecyberstage.wecyberstage.view.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.wecyberstage.wecyberstage.view.main.FooterEditBar;
import com.wecyberstage.wecyberstage.view.main.PlayerControlBar;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ToolViewsDelegate implements SlideInterface {

    protected Activity activity;
    protected AppBarLayout appBarLayout;
    protected PlayerControlBar playerControlBar;
    protected FooterEditBar footerEditBar; // edit stageLine and stageScene
    protected DrawerLayout drawerLayout;
    protected FloatingActionButton fab;

    public ToolViewsDelegate( Activity activity,
                             AppBarLayout appBarLayout,
                             PlayerControlBar playerControlBar,
                             FooterEditBar footerEditBar,
                             DrawerLayout drawerLayout,
                             FloatingActionButton fab ) {
        this.activity = activity;
        this.appBarLayout = appBarLayout;
        this.playerControlBar = playerControlBar;
        this.footerEditBar = footerEditBar;
        this.drawerLayout = drawerLayout;
        this.fab = fab;
    }

    private final Lock queueLock=new ReentrantLock();
    private Handler autoHideHandler = new Handler();
    private Runnable autoHideRunnable=new Runnable() {
        @Override
        public void run() {
            // queueLock.lock();
            if(appBarLayout.getVisibility() == View.VISIBLE) {
                moveOutHeaderAndFooter(appBarLayout, playerControlBar);
            }
        }
    };

    private Runnable autoHidePlayerControlRunnable=new Runnable() {
        @Override
        public void run() {
            // queueLock.lock();
            if(appBarLayout.getVisibility() == View.VISIBLE) {
                moveOutPlayerControl();
            }
        }
    };

    public void moveOutPlayerControl() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(playerControlBar, "alpha", 0.0f),
                ObjectAnimator.ofFloat(playerControlBar, "translationY", playerControlBar.getHeight())
        );
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playerControlBar.setVisibility(View.INVISIBLE);
                // queueLock.unlock();
            }
        });
        set.setDuration(300).start();
    }

    public void moveInPlayerControl() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(playerControlBar, "alpha", 1.0f),
                ObjectAnimator.ofFloat(playerControlBar, "translationY", 0)
        );
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                playerControlBar.setVisibility(View.VISIBLE);
                // queueLock.unlock();
                autoHideHandler.postDelayed(autoHidePlayerControlRunnable, 6000);
            }
        });
        set.setDuration(300).start();
    }

    public void moveOutHeaderAndFooter() {
        moveOutHeaderAndFooter(appBarLayout, playerControlBar);
    }

    public void moveInHeaderAndFooter() {
        moveInHeaderAndFooter(appBarLayout, playerControlBar);
    }

    public void moveOutHeaderAndFooter(final View header, final View footer) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(header, "alpha", 0.0f),
                ObjectAnimator.ofFloat(header, "translationY", -header.getHeight()),
                ObjectAnimator.ofFloat(footer, "alpha", 0.0f),
                ObjectAnimator.ofFloat(footer, "translationY", footer.getHeight())
        );
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                header.setVisibility(View.INVISIBLE);
                footer.setVisibility(View.INVISIBLE);
                // queueLock.unlock();
            }
        });
        set.setDuration(300).start();
    }

    public void moveInHeaderAndFooter(final View header, final View footer) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(header, "alpha", 1.0f),
                ObjectAnimator.ofFloat(header, "translationY", 0),
                ObjectAnimator.ofFloat(footer, "alpha", 1.0f),
                ObjectAnimator.ofFloat(footer, "translationY", 0)
        );
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                header.setVisibility(View.VISIBLE);
                footer.setVisibility(View.VISIBLE);
                // queueLock.unlock();
                autoHideHandler.postDelayed(autoHideRunnable, 6000);
            }
        });
        set.setDuration(300).start();
    }
}
