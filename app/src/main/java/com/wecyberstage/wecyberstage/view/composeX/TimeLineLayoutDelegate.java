package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

public class TimeLineLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public TimeLineLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull Object items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("TimeLine","onLayoutChildren");
    }

    @Override
    public int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull Object items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dx;
    }

    @Override
    public int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull Object items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dy;
    }
}
