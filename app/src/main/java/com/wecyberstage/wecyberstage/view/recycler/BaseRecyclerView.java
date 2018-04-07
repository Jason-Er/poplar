package com.wecyberstage.wecyberstage.view.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.wecyberstage.wecyberstage.view.main.TouchListenerInterface;

public class BaseRecyclerView extends RecyclerView {

    public TouchListenerInterface touchListenerInterface;

    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if( touchListenerInterface != null ) {
            return touchListenerInterface.onTouchEvent(e);
        } else {
            return super.onTouchEvent(e);
        }
    }
}
