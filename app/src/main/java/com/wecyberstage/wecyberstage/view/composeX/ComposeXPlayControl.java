package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.wecyberstage.wecyberstage.view.helper.PlayControlInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayTimeInterface;

public class ComposeXPlayControl extends RecyclerView implements PlayControlInterface {

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
    int lastY;
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()) {
            scrollBy(scroller.getCurrX() - lastX, scroller.getCurrY() - lastY);
            lastX = scroller.getCurrX();
            lastY = scroller.getCurrY();
        }
    }

    @Override
    public void play() {
        LayoutManager layoutManager = getLayoutManager();
        if( layoutManager instanceof PlayTimeInterface ) {
            int timeSpan = ((PlayTimeInterface) layoutManager).getTimeSpan();
            int timeSpanCover = ((PlayTimeInterface) layoutManager).getTimeSpanCover();
            Log.d("ComposeXPlayControl","play() timeSpan: "+timeSpan+" timeSpanCover: "+timeSpanCover);
            scroller.startScroll(getScrollX(), 0, timeSpanCover - getScrollX(), 0,
                    (int) ((float) (timeSpanCover - getScrollX())  * (float) timeSpan / (float) timeSpanCover ));
            Log.d("ComposeXPlayControl","getScrollX() "+getScrollX()+" timeSpanCover: "+ (timeSpanCover - getScrollX())+" duration: "+(int) ((float) (timeSpanCover - getScrollX())  * (float) timeSpan / (float) timeSpanCover ));
            lastX = scroller.getCurrX();
            lastY = scroller.getCurrY();
            invalidate();
        }
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
