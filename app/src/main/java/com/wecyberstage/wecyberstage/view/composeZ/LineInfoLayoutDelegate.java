package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

class LineInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public LineInfoLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void layout(RecyclerView.LayoutManager layoutManager, View view) {

    }
}
