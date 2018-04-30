package com.wecyberstage.wecyberstage.view.helper;

import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CustomViewSlideControl {

    public enum ViewType {
        BROWSE, PARTICIPANT, COMPOSE
    }
    ViewGroup container;
    SparseArray viewArray;

    float pixelPerSecondX;
    float pixelPerSecondY;

    public CustomViewSlideControl(ViewGroup container) {
        this.container = container;
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_MEDIUM);
        pixelPerSecondX = 3600f;
        pixelPerSecondY = 5300f;
        viewArray = new SparseArray();
    }

    public void intViewsPosition() {
        /*
        composeViewAndMoves.get(0).view.setX(0);
        composeViewAndMoves.get(1).view.setX(composeViewAndMoves.get(0).view.getWidth());
        */
        // offset all views
        for(int i = 0, nsize = viewArray.size(); i < nsize; i++) {
            CustomView customView = (CustomView) viewArray.valueAt(i);
            customView.view.setVisibility(View.INVISIBLE);
        }
        currentView = ((CustomView) viewArray.get(ViewType.BROWSE.ordinal())).view;
        currentView.setVisibility(View.VISIBLE);

        /*
        composeViewAndMoves.get(0).viewOnTouch.setFollow(composeViewAndMoves.get(1).view);
        composeViewAndMoves.get(1).viewOnTouch.setFollow(composeViewAndMoves.get(0).view);
        composeViewAndMoves.get(0).viewOnTouch.setHorizontalMinMax(-composeViewAndMoves.get(0).view.getWidth(), 0);
        composeViewAndMoves.get(1).viewOnTouch.setHorizontalMinMax(0, composeViewAndMoves.get(1).view.getWidth());

        composeViewAndMoves.get(0).viewOnTouch.setPixelPerSecondX(pixelPerSecondX);
        composeViewAndMoves.get(1).viewOnTouch.setPixelPerSecondX(pixelPerSecondX);
        */
    }

    public void addView(ViewType type, CustomView customView, int index) {
        viewArray.put(type.ordinal(), customView);
        container.addView(customView.view, index);
    }

    FlingAnimation flingAnimationY;
    SpringAnimation springAnimationY;
    View currentView;
    SpringForce springForce = new SpringForce();

    void slideView(final View currentView, final View followView, final int index) {

        followView.setVisibility(View.VISIBLE);
        if(index == -1) {
            followView.setTranslationY(-followView.getHeight());
        } else {
            followView.setTranslationY(followView.getHeight());
        }
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
                        currentView.setVisibility(View.INVISIBLE);
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

    public void navigateToView(ViewType type) {
        View followView = ((CustomView) viewArray.get(type.ordinal())).view;
        followView.setTranslationY(followView.getHeight());
        slideView(currentView, followView, -1);
        currentView = followView;
    }

}