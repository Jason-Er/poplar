package com.wecyberstage.wecyberstage.view.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import javax.inject.Inject;

public class LayoutDelegatesManager {

    private SparseArray delegates = new SparseArray();

    @Inject
    public LayoutDelegatesManager() {}

    public LayoutDelegatesManager addDelegate(@NonNull LayoutDelegateInterface delegate) {
        delegates.put(delegate.getItemViewType(), delegate);
        return this;
    }

    public void layoutItemView(RecyclerView.LayoutManager layoutManager, RecyclerView.Recycler recycler) {
        for(int i=0; i < layoutManager.getItemCount(); i++ ) {
            View view = recycler.getViewForPosition(i);
            int viewType = layoutManager.getItemViewType(view);
            layoutManager.addView(view);
            LayoutDelegateInterface delegate = (LayoutDelegateInterface) delegates.get(viewType);
            if (delegate != null) {
                delegate.layout(layoutManager, view);
            } else {
                throw new IllegalArgumentException("No delegate found");
            }
        }
    }

}
