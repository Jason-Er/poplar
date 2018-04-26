package com.wecyberstage.wecyberstage.view.helper;

import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CustomViewSlideControl {

    ViewGroup container;
    List<CustomView> composeViewAndMoves = new ArrayList<>();
    List<CustomView> accountViewAndMoves = new ArrayList<>();
    List<CustomView> browseViewAndMoves = new ArrayList<>();

    float pixelPerSecondX;
    float pixelPerSecondY;

    public CustomViewSlideControl(ViewGroup container) {
        this.container = container;
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_MEDIUM);
        pixelPerSecondX = 3600f;
        pixelPerSecondY = 5300f;
    }

    public void intViewsPosition() {
        /*
        composeViewAndMoves.get(0).view.setX(0);
        composeViewAndMoves.get(1).view.setX(composeViewAndMoves.get(0).view.getWidth());
        */

        currentView = browseViewAndMoves.get(0).view;
        // browseViewAndMoves.get(0).view.setX(0);

        for(CustomView viewAndMove: composeViewAndMoves) {
            viewAndMove.view.setTranslationY(viewAndMove.view.getHeight());
        }

        for(CustomView viewAndMove: accountViewAndMoves) {
            viewAndMove.view.setTranslationY(-viewAndMove.view.getHeight());
        }


        composeViewAndMoves.get(0).viewOnTouch.setFollow(composeViewAndMoves.get(1).view);
        composeViewAndMoves.get(1).viewOnTouch.setFollow(composeViewAndMoves.get(0).view);
        composeViewAndMoves.get(0).viewOnTouch.setHorizontalMinMax(-composeViewAndMoves.get(0).view.getWidth(), 0);
        composeViewAndMoves.get(1).viewOnTouch.setHorizontalMinMax(0, composeViewAndMoves.get(1).view.getWidth());

        composeViewAndMoves.get(0).viewOnTouch.setPixelPerSecondX(pixelPerSecondX);
        composeViewAndMoves.get(1).viewOnTouch.setPixelPerSecondX(pixelPerSecondX);

    }

    public void addComposeView(CustomView customView, int index) {
        composeViewAndMoves.add(customView);
        container.addView(customView.view, index);
    }

    public void addAccountView(CustomView customView, int index) {
        accountViewAndMoves.add(customView);
        container.addView(customView.view, index);
    }

    public void addBrowseView(CustomView customView, int index) {
        browseViewAndMoves.add(customView);
        container.addView(customView.view, index);
    }

    FlingAnimation flingAnimationY;
    SpringAnimation springAnimationY;
    View currentView;
    SpringForce springForce = new SpringForce();

    void slideView(final View currentView, final View followView, final int index) {

        springAnimationY = new SpringAnimation(currentView, DynamicAnimation.ROTATION_Y)
                .setSpring(springForce)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                        Log.i("onAnimationEnd", " currentView X: " + currentView.getX() + " currentView Y: " + currentView.getY());
                    }
                })
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        followView.setTranslationY(value + followView.getHeight() * (-index));
                    }
                });

        flingAnimationY = new FlingAnimation(currentView, DynamicAnimation.TRANSLATION_Y)
                .setFriction(1.1f)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                        Log.i("fling End", " value: " + value + " velocity: " + velocity);
                        springAnimationY.setStartVelocity(velocity)
                                .setStartValue(value)
                                .animateToFinalPosition(value);
                    }
                })
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        Log.i("fling Update", " value: " + value + " velocity: " + velocity);
                        followView.setTranslationY(value + followView.getHeight() * (-index));
                    }
                });

        if(index == 1) {
            flingAnimationY.setMinValue(0)
                    .setMaxValue(currentView.getHeight());
        } else if (index == -1) {
            flingAnimationY.setMinValue(-currentView.getHeight())
                    .setMaxValue(0);
        }
        flingAnimationY.setStartVelocity(pixelPerSecondY * index);
        flingAnimationY.start();

    }

    public void slideToAccount() {
        View followView = accountViewAndMoves.get(0).view;
        followView.setTranslationY(-followView.getHeight());
        slideView(currentView, followView, 1);
        currentView = followView;
    }

    public void slideToCompose() {
        View followView = composeViewAndMoves.get(0).view;
        followView.setTranslationY(followView.getHeight());
        slideView(currentView, followView, -1);
        currentView = followView;
    }

}