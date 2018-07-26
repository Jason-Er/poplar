package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Scroller;

import com.wecyberstage.wecyberstage.view.helper.PlayControlInterface;

public class ComposeXPlayControl extends RecyclerView implements PlayControlInterface {

    private Scroller scroller;

    public ComposeXPlayControl(Context context) {
        super(context);
    }

    public ComposeXPlayControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public ComposeXPlayControl(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()) {
            scrollBy(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public void play() {
        scroller.startScroll(getScrollX(), 0, 1000, 0, 1000);
        invalidate();
    }

    @Override
    public void pause() {
        scroller.forceFinished(true);
    }

    @Override
    public void pre() {

    }

    @Override
    public void next() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void volume(boolean open) {

    }
}
