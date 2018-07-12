package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

class StageInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public StageInfoLayoutDelegate(int viewType) {
        super(viewType);
    }

    public void layout(RecyclerView.LayoutManager layoutManager, View view) {
        layoutManager.measureChildWithMargins(view, 0, 0);
        int width = layoutManager.getDecoratedMeasuredWidth(view);
        int height = layoutManager.getDecoratedMeasuredHeight(view);
        layoutManager.layoutDecorated(view, 0, 0, width, height);
    }

    @Override
    public void onLayoutChildren(@NonNull Object items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("StageInfo","onLayoutChildren");
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
