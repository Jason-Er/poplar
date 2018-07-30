package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.wecyberstage.wecyberstage.message.ComposeEvent;
import com.wecyberstage.wecyberstage.view.helper.PlayControlSub1Interface;
import com.wecyberstage.wecyberstage.view.helper.PlayTimeInterface;

import org.greenrobot.eventbus.EventBus;

public class ComposeXPlayControl extends RecyclerView implements PlayControlSub1Interface {

    private Scroller scroller;

    public ComposeXPlayControl(Context context) {
        super(context);
    }

    public ComposeXPlayControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context, new LinearInterpolator());
    }

    public ComposeXPlayControl(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    int lastX;
    int duration;
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()) {
            scrollBy(scroller.getCurrX() - lastX, 0);
            lastX = scroller.getCurrX();
        } else {
            Log.d("ComposeXPlayControl","scroller.timePassed(): "+scroller.timePassed()+" duration: "+duration);
            if(scroller.timePassed() >= duration && scroller.isFinished()) {
                ComposeEvent event = new ComposeEvent("END");
                EventBus.getDefault().post(event);
            }
        }
    }

    @Override
    public void play() {
        LayoutManager layoutManager = getLayoutManager();
        if( layoutManager instanceof PlayTimeInterface ) {
            int timeSpan = ((PlayTimeInterface) layoutManager).getTimeSpan();
            int timeSpanCover = ((PlayTimeInterface) layoutManager).getTimeSpanCover();
            int scrolledX = ((PlayTimeInterface) layoutManager).getScrolledX();
            duration = (int) ((float) (timeSpanCover - scrolledX)  * (float) timeSpan / (float) timeSpanCover );
            Log.d("ComposeXPlayControl","scrolledX: "+scrolledX+" duration: "+duration);
            scroller.startScroll(scrolledX, 0, timeSpanCover - scrolledX, 0, duration);
            lastX = scrolledX;
            invalidate();
        }
    }

    @Override
    public void pause() {
        scroller.forceFinished(true);
    }

    @Override
    public void stop() {
        scroller.forceFinished(true);
        LayoutManager layoutManager = getLayoutManager();
        int scrolledX = ((PlayTimeInterface) layoutManager).getScrolledX();
        smoothScrollBy(-scrolledX,0);
    }

    @Override
    public void seek(float percent) {
        Log.d("ComposeXPlayControl","seek() percent: "+percent);
        LayoutManager layoutManager = getLayoutManager();
        int timeSpanCover = ((PlayTimeInterface) layoutManager).getTimeSpanCover();
        int scrolledX = ((PlayTimeInterface) layoutManager).getScrolledX();
        int cover = Math.min((int) (percent*timeSpanCover), timeSpanCover);
        cover = Math.max(cover, 0);
        smoothScrollBy(cover -  scrolledX,0);
    }

}
