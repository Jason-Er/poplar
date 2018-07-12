package com.wecyberstage.wecyberstage.view.composeZ;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import timber.log.Timber;

class RoleInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public RoleInfoLayoutDelegate(int viewType) {
        super(viewType);
    }

    public void layout(RecyclerView.LayoutManager layoutManager, View view) {
        Timber.d("layoutRole roleView: ");
        RectF roleRectF = ((RoleCardView) view).roleInfo.roleViewRect;
        int parentHeight = ((View)view.getParent()).getHeight();
        layoutManager.measureChildWithMargins(view, 0, 0);
        layoutManager.layoutDecorated(view, (int)(parentHeight * roleRectF.left), (int)(parentHeight * roleRectF.top), (int)(parentHeight * roleRectF.right), (int)(parentHeight * roleRectF.bottom));
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
