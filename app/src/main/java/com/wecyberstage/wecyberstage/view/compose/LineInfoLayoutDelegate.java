package com.wecyberstage.wecyberstage.view.compose;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wecyberstage.wecyberstage.view.common.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.common.ViewTypeDelegateClass;

public class LineInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public LineInfoLayoutDelegate(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType() {
        return viewType;
    }

    @Override
    public void layout(RecyclerView.LayoutManager layoutManager, View view) {

    }
}
