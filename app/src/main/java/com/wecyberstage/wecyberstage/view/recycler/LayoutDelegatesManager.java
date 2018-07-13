package com.wecyberstage.wecyberstage.view.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import javax.inject.Inject;

public abstract class LayoutDelegatesManager<T> {

    private SparseArray delegates = new SparseArray();

    public LayoutDelegatesManager() {}

    public LayoutDelegatesManager addDelegate(@NonNull ViewTypeDelegateClass delegate) {
        delegates.put(delegate.getItemViewType(), delegate);
        return this;
    }

    public ViewTypeDelegateClass getDelegate(int viewType) {
        ViewTypeDelegateClass delegate = null;
        for(int i = 0; i < delegates.size(); i++) {
            if( viewType == delegates.keyAt(i) ) {
                delegate = (ViewTypeDelegateClass) delegates.valueAt(i);
            }
        }
        return delegate;
    }
    /*
    public int getItemViewType(@NonNull T items, int position) {
        int key = 0;
        for(int i = 0; i < delegates.size(); i++) {
            key = delegates.keyAt(i);
            LayoutDelegateInterface delegate = (LayoutDelegateInterface)delegates.get(key);
            if ( delegate.isForViewType(items, position) ) {
                return key;
            }
        }
        throw new IllegalArgumentException("No delegate found");
    }
    */
    public abstract void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull T items, RecyclerView.Recycler recycler, RecyclerView.State state);
    /*
    {
        for(int i = 0; i < delegates.size(); i++) {
            LayoutDelegateInterface<T> delegate = (LayoutDelegateInterface<T>) delegates.valueAt(i);
            delegate.onLayoutChildren(items, recycler, state);
        }
    }
    */
    public abstract int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull T items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state);
    /*
    {
        int minimum = 0;
        for(int i = 0; i < delegates.size(); i++) {
            LayoutDelegateInterface<T> delegate = (LayoutDelegateInterface<T>) delegates.valueAt(i);
            int value = delegate.scrollHorizontallyBy(items, dx, recycler, state);
            if( minimum == 0 )
                minimum = value;
            if( value < minimum )
                minimum = value;
        }
        return minimum;
    }
    */
    public abstract int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull T items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state);
    /*
    {
        int minimum = 0;
        for(int i = 0; i < delegates.size(); i++) {
            LayoutDelegateInterface<T> delegate = (LayoutDelegateInterface<T>) delegates.valueAt(i);
            int value = delegate.scrollVerticallyBy(items, dy, recycler, state);
            if( minimum == 0 )
                minimum = value;
            if( value < minimum )
                minimum = value;
        }
        return minimum;
    }
    */
}
