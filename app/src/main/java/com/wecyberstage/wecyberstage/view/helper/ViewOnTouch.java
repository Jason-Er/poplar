package com.wecyberstage.wecyberstage.view.helper;

import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class ViewOnTouch
        implements View.OnTouchListener
{
    GestureDetector gestureDetector;
    FlingAnimation flingAnimationX;
    SpringAnimation springAnimationX;
    View view;
    View follow;
    float pixelPerSecondX;

    public ViewOnTouch(View v)
    {
        this.view = v;
        gestureDetector = new GestureDetector(view.getContext(), mGestureListener);
        springAnimationX = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X)
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        if(follow != null) {
                            if(value < 0) { // move left
                                follow.setX(value + view.getWidth()); // let follow view be visible
                            }
                            else { // move right
                                follow.setX(value - view.getWidth()); // let follow view be visible
                            }
                        }
                    }
                })
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {

                    }
                });
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springAnimationX.setSpring(springForce);
        flingAnimationX = new FlingAnimation(view, DynamicAnimation.TRANSLATION_X)
                .setFriction(1.1f)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                        Log.i("addEndListener", "finished " + "value: " + value + " velocity: " + velocity);
                        Log.i("addEndListener", "finished " + "X: " + view.getX() + " Y: " + view.getY());
                        springAnimationX.setStartVelocity(velocity)
                                .setStartValue(value)
                                .animateToFinalPosition(value);
                    }
                })
                .addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        Log.i("addUpdateListener: ", "left: " + ViewOnTouch.this.view.getLeft() + " X: " + ViewOnTouch.this.view.getX());
                        Log.i("addUpdateListener", "value: " + value + " velocity: " + velocity);
                        if(follow != null) {
                            if(value < 0) { // move left
                                follow.setX(value + view.getWidth()); // let follow view be visible
                            }
                            else { // move right
                                follow.setX(value - view.getWidth()); // let follow view be visible
                            }
                        }
                    }
                });
    }

    public void setFollow(View follow) {
        this.follow = follow;
    }

    public void setHorizontalMinMax(float min, float max) {
        flingAnimationX.setMinValue(min).setMaxValue(max);
    }

    public void setPixelPerSecondX(float pixelPerSecondX) {
        this.pixelPerSecondX = pixelPerSecondX;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener()
    {
        // private float mMotionDownX, mMotionDownY;

        @Override
        public boolean onDown(MotionEvent e)
        {
            /*
            mMotionDownX = e.getRawX() - view.getTranslationX();
            mMotionDownY = e.getRawY() - view.getTranslationY();
            */
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            /*
            view.setTranslationX(e2.getRawX() - mMotionDownX);
            view.setTranslationY(e2.getRawY() - mMotionDownY);
            */
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float speedX;
            if( Math.abs(velocityX) > pixelPerSecondX ) {
                speedX = velocityX;
            } else {
                if( velocityX > 0)
                    speedX = pixelPerSecondX;
                else
                    speedX = -pixelPerSecondX;
            }
            // float pixelPerSecond = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpPerSecond, getResources().getDisplayMetrics());

            flingAnimationX.setStartVelocity(speedX);
            flingAnimationX.start();
            return true;
        }
    };
}
