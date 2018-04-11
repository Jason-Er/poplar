package com.wecyberstage.wecyberstage.view.participate;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

public class PropInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public PropInfoLayoutDelegate(int viewType) {
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
