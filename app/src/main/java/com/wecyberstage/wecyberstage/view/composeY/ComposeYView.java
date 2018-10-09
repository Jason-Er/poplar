package com.wecyberstage.wecyberstage.view.composeY;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.wecyberstage.wecyberstage.view.helper.PlayControlSub1Interface;

public class ComposeYView extends RecyclerView implements PlayControlSub1Interface {

    private final String TAG = "ComposeYView";

    public ComposeYView(@NonNull Context context) {
        super(context);
    }

    public ComposeYView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ComposeYView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
