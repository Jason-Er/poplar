package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

public class LayoutDelegateTimeLine extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public LayoutDelegateTimeLine(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(@NonNull Object items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("TimeLine","onLayoutChildren");
    }

    @Override
    public int scrollHorizontallyBy(@NonNull Object items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dx;
    }

    @Override
    public int scrollVerticallyBy(@NonNull Object items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dy;
    }
}
