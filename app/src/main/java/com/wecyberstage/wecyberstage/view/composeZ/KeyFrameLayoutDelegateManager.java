package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegatesManager;

import javax.inject.Inject;

public class KeyFrameLayoutDelegateManager<T> extends LayoutDelegatesManager<T>{

    @Inject
    public KeyFrameLayoutDelegateManager() {
        addDelegate(new StageInfoLayoutDelegate(KeyFrameCardViewType.STAGE.ordinal()));
        addDelegate(new RoleInfoLayoutDelegate(KeyFrameCardViewType.ROLE.ordinal()));
        addDelegate(new PropInfoLayoutDelegate(KeyFrameCardViewType.PROP.ordinal()));
        addDelegate(new LineInfoLayoutDelegate(KeyFrameCardViewType.LINE.ordinal()));
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull T items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        ((LayoutDelegateInterface)getDelegate(KeyFrameCardViewType.STAGE.ordinal())).onLayoutChildren(layoutManager, items, recycler, state);
        ((LayoutDelegateInterface)getDelegate(KeyFrameCardViewType.ROLE.ordinal())).onLayoutChildren(layoutManager, items, recycler, state);
    }

    @Override
    public int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull T items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return 0;
    }

    @Override
    public int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull T items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return 0;
    }
}
