package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.wecyberstage.wecyberstage.view.helper.PlayControlSub1Interface;
import com.wecyberstage.wecyberstage.view.helper.PlayTimeInterface;

public class ComposeXPlayControl extends RecyclerView implements PlayControlSub1Interface {

    private Scroller scroller;
    private int totalScrolledX = 0;

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
        Log.d("ComposeXPlayControl","computeScroll: " + totalScrolledX + "line 37");
        if(scroller.computeScrollOffset()) {
            Log.d("ComposeXPlayControl","computeScroll: " + totalScrolledX + "line 39");
            scrollBy(scroller.getCurrX() - lastX, scroller.getCurrY() - lastY);
            lastX = scroller.getCurrX();
            lastY = scroller.getCurrY();
            invalidate();
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        totalScrolledX += dx;
        Log.d("ComposeXPlayControl","onScrolled: "+totalScrolledX);
    }

    @Override
    public void play() {
        LayoutManager layoutManager = getLayoutManager();
        if( layoutManager instanceof PlayTimeInterface ) {
            int timeSpan = ((PlayTimeInterface) layoutManager).getTimeSpan();
            int timeSpanCover = ((PlayTimeInterface) layoutManager).getTimeSpanCover();
            scroller.startScroll(totalScrolledX, 0, timeSpanCover - totalScrolledX, 0,
                    (int) ((float) (timeSpanCover - totalScrolledX)  * (float) timeSpan / (float) timeSpanCover ));
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
    public void stop() {
        scroller.forceFinished(true);
        smoothScrollBy(-totalScrolledX,0);
    }

}
