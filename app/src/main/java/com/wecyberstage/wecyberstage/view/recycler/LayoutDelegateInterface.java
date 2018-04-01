package com.wecyberstage.wecyberstage.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface LayoutDelegateInterface {
    int getItemViewType();
    void layout(RecyclerView.LayoutManager layoutManager, View view);
}
