package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegatesManager;

import javax.inject.Inject;

public class ComposeXLayoutDelegateManager<T> extends LayoutDelegatesManager<T> {

    @Inject
    public ComposeXLayoutDelegateManager() {
        addDelegate(new TimeLineLayoutDelegate(ComposeXCardViewType.TIME_LINE.ordinal()));
        addDelegate(new MaskLineLayoutDelegate(ComposeXCardViewType.MASK_LINE.ordinal()));
        addDelegate(new PopupLayoutDelegate(ComposeXCardViewType.POPUP_WINDOW.ordinal()));
    }

    @Override
    public void onLayoutChildren(@NonNull T items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.MASK_LINE.ordinal())).onLayoutChildren(items, recycler, state);
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.TIME_LINE.ordinal())).onLayoutChildren(items, recycler, state);
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.POPUP_WINDOW.ordinal())).onLayoutChildren(items, recycler, state);
    }

    @Override
    public int scrollHorizontallyBy(@NonNull T items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int minimum =
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.MASK_LINE.ordinal())).scrollHorizontallyBy(items, dx, recycler, state);
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.TIME_LINE.ordinal())).scrollHorizontallyBy(items, dx, recycler, state);
        return minimum;
    }

    @Override
    public int scrollVerticallyBy(@NonNull T items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.MASK_LINE.ordinal())).scrollVerticallyBy(items, dy, recycler, state);
    }


}
