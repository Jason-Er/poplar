package com.wecyberstage.wecyberstage.view.helper;

import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.Log;
import android.view.View;

public class CustomViewSlideHelper {

    private static SpringForce springForce = new SpringForce();

    /**
     *
     * @param currentView
     * @param followView
     * @param index index: -1 left; 1 right
     */
    public static void SlideHorizontal(final View currentView, final View followView, final int index) {
        float pixelPerSecondX = calcInitVelocity( currentView.getWidth(), 700);
        final SpringAnimation springAnimationX = new SpringAnimation(currentView, DynamicAnimation.ROTATION_X)
                .setSpring(springForce)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                    }
                })
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        followView.setTranslationX(value + followView.getWidth() * (-index));
                    }
                });

        FlingAnimation flingAnimationX = new FlingAnimation(currentView, DynamicAnimation.TRANSLATION_X)
                .setFriction(1.1f)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                        currentView.setVisibility(View.INVISIBLE);
                        /*
                        springAnimationX.setStartVelocity(velocity)
                                .setStartValue(value)
                                .animateToFinalPosition(value);
                        Log.i("fling end", "followView x:" + followView.getX() + " y: "+ followView.getY());
                        */
                    }
                })
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        followView.setTranslationX(value + followView.getWidth() * (-index));
                    }
                });

        if(index == 1) {
            flingAnimationX.setMinValue(0)
                    .setMaxValue(currentView.getWidth());
        } else if (index == -1) {
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
    public static void SlideVertical(final View currentView, final View followView, final int index) {
        float pixelPerSecondY = calcInitVelocity( currentView.getHeight(), 700);
        final SpringAnimation springAnimationY = new SpringAnimation(currentView, DynamicAnimation.ROTATION_Y)
                .setSpring(springForce)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                        Log.i("fling End", " currentView matrix: " + currentView.getMatrix().toString() );
                    }
                })
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        followView.setTranslationY(value + followView.getHeight() * (-index));
                    }
                });

        FlingAnimation flingAnimationY = new FlingAnimation(currentView, DynamicAnimation.TRANSLATION_Y)
                .setFriction(1.1f)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                        Log.i("fling End", " value: " + value + " velocity: " + velocity);
                        Log.i("fling End", " currentView matrix: " + currentView.getMatrix().toString() );
                        currentView.setVisibility(View.INVISIBLE);
                        /*
                        springAnimationY.setStartVelocity(velocity)
                                .setStartValue(value)
                                .animateToFinalPosition(value);
                                */
                    }
                })
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
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

    private static float calcInitVelocity(float span, float deltaT) {
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