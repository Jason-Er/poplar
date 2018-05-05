package com.wecyberstage.wecyberstage.view.helper;

import android.graphics.Rect;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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
    DisplayMetrics dm;

    private AppCompatActivity activity;

    public CustomViewSlideControl(AppCompatActivity activity, ViewGroup container) {
        this.activity = activity;
        this.container = container;
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_MEDIUM);
        pixelPerSecondX = 3600f;
        pixelPerSecondY = 5300f;
        viewArray = new SparseArray();
    }

    public void intViewsPosition() {

        for(int i = 0, nsize = viewArray.size(); i < nsize; i++) {
            CustomView customView = (CustomView) viewArray.valueAt(i);
            // customView.view.setVisibility(View.INVISIBLE);
            customView.view.setTranslationY(customView.view.getHeight());
            Log.i("intViewsPosition", "view.getHeight(): "+ customView.view.getHeight());
        }
        currentView = ((CustomView) viewArray.get(ViewType.BROWSE.ordinal())).view;
        // currentView.setVisibility(View.VISIBLE);
        currentView.setTranslationY(0);

        dm = activity.getResources().getDisplayMetrics();
        pixelPerSecondX = calcInitVelocity( currentView.getWidth(), 700);
        pixelPerSecondY = calcInitVelocity( currentView.getHeight(), 700);
        /*
        CustomView participate = (CustomView) viewArray.get(ViewType.PARTICIPANT.ordinal());
        CustomView compose = (CustomView) viewArray.get(ViewType.COMPOSE.ordinal());
        participate.viewOnTouch.setFollow(compose.view);
        compose.viewOnTouch.setFollow(participate.view);
        participate.viewOnTouch.setPixelPerSecondX(pixelPerSecondX);
        compose.viewOnTouch.setPixelPerSecondX(pixelPerSecondX);
        */

    }

    public void addView(ViewType type, CustomView customView, int index) {
        CustomViewInterface customViewInterface = (CustomViewInterface) customView;
        customViewInterface.onCreate(activity, container);
        viewArray.put(type.ordinal(), customView);
        container.addView(customView.view, index);
    }

    FlingAnimation flingAnimationY;
    SpringAnimation springAnimationY;
    View currentView;
    SpringForce springForce = new SpringForce();

    void slideView(final View currentView, final View followView, final int index) {

        Rect rect = new Rect();
        currentView.getGlobalVisibleRect(rect);
        Log.i("slideView", "Rect (" + rect.left + "," + rect.top + ")");

        followView.setVisibility(View.VISIBLE);
        Log.i("slideView", "followView.getHeight(): "+followView.getHeight());
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
                        Log.i("onAnimationEnd", " followView X: " + followView.getX() + " followView Y: " + followView.getY());
                    }
                })
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        followView.setTranslationY(value + followView.getHeight() * (-index));
                        Log.i("spring", " value: " + value + " getHeight: " + followView.getHeight() + " setY()" + value + followView.getHeight() * (-index));
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
            Log.i("slideView", "dm.heightPixels: " + dm.heightPixels + " currentView.getHeight(): " + currentView.getHeight());

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

    private float calcInitVelocity(float span, float deltaT) {
        float friction = 1.1f * -4.2f;
        return (float)( ( span * friction ) / ( Math.exp(friction * deltaT / 1000f ) -1f) );

        /*
        mVelocity = 100f;

		deltaT = (float) (Math.log(mVelocity / velocity) / mFriction * 1000f);
		System.out.println("time: " + deltaT);

		mValue = 768f;
		velocity = (float)( ( mValue * mFriction ) / ( Math.exp(mFriction * deltaT / 1000f ) -1f) );

		System.out.println("velocity: " + velocity);
        */
    }
}