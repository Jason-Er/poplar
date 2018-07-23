package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.wecyberstage.wecyberstage.view.helper.LifeCycle;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegatesManager;

import javax.inject.Inject;

public class ComposeXLayoutDelegateManager<T> extends LayoutDelegatesManager<T> implements LifeCycle {

    @Inject
    public ComposeXLayoutDelegateManager() {
        addDelegate(new TimeLineLayoutDelegate(ComposeXCardViewType.TIME_LINE.ordinal()));
        addDelegate(new StageLineLayoutDelegate(ComposeXCardViewType.MASK_LINE.ordinal()));
        addDelegate(new RoleLayoutDelegate(ComposeXCardViewType.ROLE_MASK.ordinal()));
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull T items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.MASK_LINE.ordinal())).onLayoutChildren(layoutManager, items, recycler, state);
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.TIME_LINE.ordinal())).onLayoutChildren(layoutManager, items, recycler, state);
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.ROLE_MASK.ordinal())).onLayoutChildren(layoutManager, items, recycler, state);
    }

    @Override
    public int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull T items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int minimum =
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.MASK_LINE.ordinal())).scrollHorizontallyBy(layoutManager, items, dx, recycler, state);
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.TIME_LINE.ordinal())).scrollHorizontallyBy(layoutManager, items, dx, recycler, state);
        ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.ROLE_MASK.ordinal())).scrollHorizontallyBy(layoutManager, items, dx, recycler, state);
        return minimum;
    }

    @Override
    public int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull T items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return ((LayoutDelegateInterface)getDelegate(ComposeXCardViewType.MASK_LINE.ordinal())).scrollVerticallyBy(layoutManager, items, dy, recycler, state);
    }

    public void updateOneViewHolder(RecyclerView.LayoutManager layoutManager,  ComposeXScriptAdapter adapter, RecyclerView.ViewHolder viewHolder) {
        ((StageLineLayoutDelegate)getDelegate(ComposeXCardViewType.MASK_LINE.ordinal())).updateOneViewHolder(layoutManager, adapter, viewHolder);
    }

    @Override
    public void onResume(Activity activity) {
        ((LifeCycle)getDelegate(ComposeXCardViewType.ROLE_MASK.ordinal())).onResume(activity);
    }

    @Override
    public void onPause(Activity activity) {
        ((LifeCycle)getDelegate(ComposeXCardViewType.ROLE_MASK.ordinal())).onPause(activity);
    }
}
