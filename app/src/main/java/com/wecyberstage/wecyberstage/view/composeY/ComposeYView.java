package com.wecyberstage.wecyberstage.view.composeY;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.wecyberstage.wecyberstage.view.helper.PlayControlSub1Interface;
import com.wecyberstage.wecyberstage.view.message.StageLineContainerViewEvent;

import org.greenrobot.eventbus.EventBus;

public class ComposeYView extends RecyclerView implements PlayControlSub1Interface {

    private final String TAG = "ComposeYView";

    private GestureDetectorCompat detectorCompat;
    final GestureDetector.SimpleOnGestureListener gestureDetector = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("StageLineCardView","Long pressed");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("StageLineCardView", "single click");
            StageLineContainerViewEvent event = new StageLineContainerViewEvent("onSingleTapUp");
            EventBus.getDefault().post(event);
            return true;
        }

    };

    public ComposeYView(@NonNull Context context) {
        super(context);
        detectorCompat = new GestureDetectorCompat(context, gestureDetector);
    }

    public ComposeYView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        detectorCompat = new GestureDetectorCompat(context, gestureDetector);
    }

    public ComposeYView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (detectorCompat.onTouchEvent(ev)) {
            return true;
        }
        return true;
    }

    @Override
    public void play() {
        Log.d(TAG,"play()");
    }

    @Override
    public void pause() {
        Log.d(TAG,"pause()");
    }

    @Override
    public void stop() {
        Log.d(TAG,"stop()");
    }

    @Override
    public void seek(float percent) {
        Log.d(TAG,"seek()");
    }
}
