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
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()) {
            scrollBy(scroller.getCurrX() - lastX, 0);
            lastX = scroller.getCurrX();
            invalidate();
        }
    }

    @Override
    public void play() {
        LayoutManager layoutManager = getLayoutManager();
        if( layoutManager instanceof PlayTimeInterface ) {
            int timeSpan = ((PlayTimeInterface) layoutManager).getTimeSpan();
            int timeSpanCover = ((PlayTimeInterface) layoutManager).getTimeSpanCover();
            int scrolledX = ((PlayTimeInterface) layoutManager).getScrolledX();
            Log.d("ComposeXPlayControl","scrolledX: "+scrolledX);
            scroller.startScroll(scrolledX, 0, timeSpanCover - scrolledX, 0,
                    (int) ((float) (timeSpanCover - scrolledX)  * (float) timeSpan / (float) timeSpanCover ));
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

}
