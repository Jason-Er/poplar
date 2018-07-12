package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

class LineInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public LineInfoLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(@NonNull Object items, RecyclerView.Recycler recycler, RecyclerView.State state) {

    }

    @Override
    public int scrollHorizontallyBy(@NonNull Object items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return 0;
    }

    @Override
    public int scrollVerticallyBy(@NonNull Object items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return 0;
    }
}
