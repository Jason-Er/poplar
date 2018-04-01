package com.wecyberstage.wecyberstage.view.compose;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

public class StageInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public StageInfoLayoutDelegate(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType() {
        return viewType;
    }

    @Override
    public void layout(RecyclerView.LayoutManager layoutManager, View view) {
        layoutManager.measureChildWithMargins(view, 0, 0);
        int width = layoutManager.getDecoratedMeasuredWidth(view);
        int height = layoutManager.getDecoratedMeasuredHeight(view);
        layoutManager.layoutDecorated(view, 0, 0, width, height);
    }
}
