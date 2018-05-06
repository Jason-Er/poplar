package com.wecyberstage.wecyberstage.view.helper;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class CustomViewBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private int verticalMinDistance = 20;
    private int minVelocity         = 0;

    GestureDetector gestureDetector;
    public CustomViewBehavior() {
    }

    public CustomViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, mGestureListener);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener()
    {
        @Override
        public boolean onDown(MotionEvent e)
        {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            String message = "";
            if( Math.abs(velocityX) >= Math.abs(velocityY) ) {
                if (e1.getX() - e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                    message = "TO_LEFT";
                } else if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
                    message = "TO_RIGHT";
                }
            } else {
                if (e1.getY() - e2.getY() > verticalMinDistance && Math.abs(velocityY) > minVelocity) {
                    message = "TO_UP";
                } else if (e2.getY() - e1.getY() > verticalMinDistance && Math.abs(velocityY) > minVelocity) {
                    message = "TO_DOWN";
                }
            }
            MessageEvent messageEvent = new MessageEvent(message);
            EventBus.getDefault().post(messageEvent);
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            MessageEvent messageEvent = new MessageEvent("CLICK");
            EventBus.getDefault().post(messageEvent);
            return super.onSingleTapUp(e);
        }

    };

}
