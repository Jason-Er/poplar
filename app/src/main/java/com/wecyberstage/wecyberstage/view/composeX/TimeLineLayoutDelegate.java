package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

public class TimeLineLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface<List<Object>> {

    private TimeLineView timeLineView;
    private int leftOffset = 0;

    public TimeLineLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("TimeLine","onLayoutChildren");
        for(Object item: items) {
            if( item instanceof TimeLine ) {
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
        leftOffset += dx;
        timeLineView.setLeftOffset(leftOffset);
        return dx;
    }

    @Override
    public int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dy;
    }
}
