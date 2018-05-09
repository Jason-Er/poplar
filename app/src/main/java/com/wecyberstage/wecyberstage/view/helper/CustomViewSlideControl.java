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

    public enum Direction {
        TO_UP, TO_RIGHT, TO_DOWN, TO_LEFT
    }
    public enum ViewType {
        BROWSE, PARTICIPANT, COMPOSE
    }
    ViewGroup container;
    SparseArray viewArray;
    SparseArray flingResponseArray;

    View currentView;
    FlingResponseInterface currentFlingResponse;

    float pixelPerSecondX;
    float pixelPerSecondY;
    private AppCompatActivity activity;

    public CustomViewSlideControl(AppCompatActivity activity, ViewGroup container) {
        this.activity = activity;
        this.container = container;
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_MEDIUM);
        pixelPerSecondX = 3600f;
        pixelPerSecondY = 5300f;
        viewArray = new SparseArray();
        flingResponseArray = new SparseArray();
    }

    public void intViewsPosition() {

        for(int i = 0, nsize = viewArray.size(); i < nsize; i++) {
            CustomView customView = (CustomView) viewArray.valueAt(i);
            customView.view.setVisibility(View.INVISIBLE);
            Log.i("intViewsPosition", "view.getHeight(): "+ customView.view.getHeight());
        }
        currentView = ((CustomView) viewArray.get(ViewType.BROWSE.ordinal())).view;
        currentView.setVisibility(View.VISIBLE);

        pixelPerSecondX = calcInitVelocity( currentView.getWidth(), 700);
        pixelPerSecondY = calcInitVelocity( currentView.getHeight(), 700);

    }

    public void addView(ViewType type, CustomView customView, int index) {
        customView.onCreate(activity, container);
        viewArray.put(type.ordinal(),customView);
        switch (type) {
            case BROWSE:
                flingResponseArray.put(type.ordinal(), new FlingResponseBrowse(this));
                break;
            case PARTICIPANT:
                flingResponseArray.put(type.ordinal(), new FlingResponseParticipate(this));
                break;
            case COMPOSE:
                flingResponseArray.put(type.ordinal(), new FlingResponseBrowse(this));
                break;
        }
        container.addView(customView.view, index);
    }


    SpringForce springForce = new SpringForce();

    void slideView(final View currentView, final View followView, Direction direction) {

        Rect rect = new Rect();
        currentView.getGlobalVisibleRect(rect);
        Log.i("slideView", "Rect (" + rect.left + "," + rect.top + ")");

        followView.setVisibility(View.VISIBLE);
        Log.i("slideView", "followView.getHeight(): "+followView.getHeight());
        switch (direction) {
            case TO_UP:
                followView.setTranslationY(followView.getHeight());
                SlideVertical(currentView, followView, -1);
                break;
            case TO_RIGHT:
                followView.setTranslationX(-followView.getWidth());
                SlideHorizontal(currentView, followView, 1);
                break;
            case TO_DOWN:
                followView.setTranslationY(-followView.getHeight());
                SlideVertical(currentView, followView, 1);
                break;
            case TO_LEFT:
                followView.setTranslationX(followView.getWidth());
                SlideHorizontal(currentView, followView, -1);
                break;
        }
    }

    /**
     *
     * @param currentView
     * @param followView
     * @param index index: -1 left; 1 right
     */
    private void SlideHorizontal(final View currentView, final View followView, final int index) {
        final SpringAnimation springAnimationX = new SpringAnimation(currentView, DynamicAnimation.ROTATION_X)
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

        FlingAnimation flingAnimationX = new FlingAnimation(currentView, DynamicAnimation.TRANSLATION_X)
                .setFriction(1.1f)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                        Log.i("fling End", " value: " + value + " velocity: " + velocity);
                        currentView.setVisibility(View.INVISIBLE);
                        springAnimationX.setStartVelocity(velocity)
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
            flingAnimationX.setMinValue(0)
                    .setMaxValue(currentView.getWidth());
        } else if (index == -1) {
//            Log.i("slideView", "dm.heightPixels: " + dm.heightPixels + " currentView.getHeight(): " + currentView.getHeight());

            flingAnimationX.setMinValue(-currentView.getWidth())
                    .setMaxValue(0);
        }
        flingAnimationX.setStartVelocity(pixelPerSecondX * index);
        flingAnimationX.start();
    }
    /**
     *
     * @param currentView
     * @param followView
     * @param index index: -1 up; 1 down
     */
    private void SlideVertical(final View currentView, final View followView, final int index) {
        final SpringAnimation springAnimationY = new SpringAnimation(currentView, DynamicAnimation.ROTATION_Y)
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

        FlingAnimation flingAnimationY = new FlingAnimation(currentView, DynamicAnimation.TRANSLATION_Y)
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
//            Log.i("slideView", "dm.heightPixels: " + dm.heightPixels + " currentView.getHeight(): " + currentView.getHeight());

            flingAnimationY.setMinValue(-currentView.getHeight())
                    .setMaxValue(0);
        }
        flingAnimationY.setStartVelocity(pixelPerSecondY * index);
        flingAnimationY.start();
    }

    public void flingResponse(Direction direction) {
        switch (direction) {
            case TO_UP:
                currentFlingResponse.toUp();
                break;
            case TO_RIGHT:
                currentFlingResponse.toRight();
                break;
            case TO_DOWN:
                currentFlingResponse.toDown();
                break;
            case TO_LEFT:
                currentFlingResponse.toLeft();
                break;
        }
    }

    public void navigateToView(ViewType type, Direction direction) {
        View followView = ((CustomView) viewArray.get(type.ordinal())).view;
        followView.setTranslationY(followView.getHeight());
        slideView(currentView, followView, direction);
        currentView = followView;
        currentFlingResponse = (FlingResponseInterface) flingResponseArray.get(type.ordinal());
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