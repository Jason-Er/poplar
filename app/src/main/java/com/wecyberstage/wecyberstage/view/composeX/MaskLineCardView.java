package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.wecyberstage.wecyberstage.message.OutsideClickEvent;

import org.greenrobot.eventbus.EventBus;

public class MaskLineCardView extends RelativeLayout {

    private int position;

    public MaskLineCardView(@NonNull Context context) {
        super(context);
    }

    public MaskLineCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskLineCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private long lastTouchDown;
    private static int CLICK_ACTION_THRESHHOLD = 200;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchDown = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHHOLD) {
                    Log.w("App", "You clicked!");
                    OutsideClickEvent event = new OutsideClickEvent("OUTSIDE_CLICK");
                    EventBus.getDefault().post(event);
                }
                break;
        }
        return true;
    }

}
