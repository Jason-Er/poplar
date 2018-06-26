package com.wecyberstage.wecyberstage.view.helper;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class ViewOnTouch
        implements View.OnTouchListener
{
    GestureDetector gestureDetector;
    View view;

    public ViewOnTouch(View v)
    {
        this.view = v;
        gestureDetector = new GestureDetector(view.getContext(), mGestureListener);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
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

            return true;
        }
    };
}
