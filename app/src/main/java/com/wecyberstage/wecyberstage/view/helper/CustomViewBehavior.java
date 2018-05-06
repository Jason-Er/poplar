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
            Log.i("CustomViewBehavior ", "onFling" + " velocityX: " + velocityX);
            MessageEvent messageEvent = new MessageEvent("onFling");
            EventBus.getDefault().post(messageEvent);
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            MessageEvent messageEvent = new MessageEvent("onSingleTapUp");
            EventBus.getDefault().post(messageEvent);
            return super.onSingleTapUp(e);
        }

    };

}
