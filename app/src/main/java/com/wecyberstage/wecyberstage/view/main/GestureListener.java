package com.wecyberstage.wecyberstage.view.main;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.wecyberstage.wecyberstage.message.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        MessageEvent messageEvent = new MessageEvent("onTouchEvent from CustomViewPager");
        EventBus.getDefault().post(messageEvent);
        return false;
    }
}
