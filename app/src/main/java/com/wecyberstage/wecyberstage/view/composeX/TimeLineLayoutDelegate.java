package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.TimeLine;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

public class TimeLineLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface<List<Object>> {

    private TimeLineView timeLineView;
    private int leftOffset = 0;
    private int topOffset = 0;
    private float beginTime = 0;
    private final float TIME_SPAN = 10f; // 10 second for this view
    private final float MS_PERSECOND = 1000f;

    public TimeLineLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("TimeLine","onLayoutChildren");
        for(Object item: items) {
            if( item instanceof TimeLine) {
                int index = items.indexOf(item);
                timeLineView = (TimeLineView) recycler.getViewForPosition(index);
                layoutManager.addView(timeLineView);
                layoutManager.measureChildWithMargins(timeLineView, 0, 0);
                int width = layoutManager.getDecoratedMeasuredWidth(timeLineView);
                int height = layoutManager.getDecoratedMeasuredHeight(timeLineView);
                layoutManager.layoutDecorated(timeLineView, 0, 0, width, height);
                break;
            }
        }
    }

    @Override
    public int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int totalLength;
        if (items != null && items.size() > 1) {
            StageLine stageLine = (StageLine) items.get(items.size() -1);
            int tempLength = (int) (( (float) stageLine.beginTime / MS_PERSECOND + stageLine.voice.duration) / TIME_SPAN * getHorizontalSpace(layoutManager));
            totalLength = tempLength > getHorizontalSpace(layoutManager) ? tempLength : getHorizontalSpace(layoutManager);
        } else {
            totalLength = getHorizontalSpace(layoutManager);
        }

        int deltaX = 0;
        int deltaReturn = 0;
        int temp = leftOffset + dx;

        if(temp >= 0 && temp <= totalLength - getHorizontalSpace(layoutManager)) {
            deltaX = dx;
            deltaReturn = dx;
            leftOffset += dx;
        } else if( temp < 0 ) {
            deltaX = -leftOffset;
            deltaReturn = -(leftOffset + dx);
            leftOffset = 0;
        } else if( temp > totalLength - getHorizontalSpace(layoutManager) ) {
            deltaX = totalLength - getHorizontalSpace(layoutManager) - leftOffset;
            deltaReturn = -(temp - totalLength + getHorizontalSpace(layoutManager));
            leftOffset = totalLength - getHorizontalSpace(layoutManager);
        }
        beginTime = leftOffset * TIME_SPAN / getHorizontalSpace(layoutManager);
        timeLineView.setLeftOffset(leftOffset);
        repositionTimeLine(layoutManager);
        timeLineView.invalidate();
        return deltaReturn;
    }

    @Override
    public int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dy;
    }

    private void repositionTimeLine(RecyclerView.LayoutManager layoutManager) {
        layoutManager.detachView(timeLineView);
        layoutManager.attachView(timeLineView);
        int width = layoutManager.getDecoratedMeasuredWidth(timeLineView);
        int height = layoutManager.getDecoratedMeasuredHeight(timeLineView);
        layoutManager.layoutDecorated(timeLineView, 0, 0, width, height);
    }

    private int getVerticalSpace(RecyclerView.LayoutManager layoutManager) {
        return layoutManager.getHeight() - layoutManager.getPaddingTop() - layoutManager.getPaddingBottom();
    }

    private int getHorizontalSpace(RecyclerView.LayoutManager layoutManager) {
        return layoutManager.getWidth() - layoutManager.getPaddingStart() - layoutManager.getPaddingEnd();
    }
}
