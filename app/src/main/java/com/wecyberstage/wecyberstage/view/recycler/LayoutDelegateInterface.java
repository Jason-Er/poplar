package com.wecyberstage.wecyberstage.view.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface LayoutDelegateInterface<T> {
    void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull T items, RecyclerView.Recycler recycler, RecyclerView.State state);
    int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull T items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state);
    int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull T items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state);
}
