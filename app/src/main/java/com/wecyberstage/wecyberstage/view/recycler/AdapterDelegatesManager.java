package com.wecyberstage.wecyberstage.view.recycler;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/26.
 */

public class AdapterDelegatesManager<T> implements RegisterBusEventInterface {

    private SparseArray delegates = new SparseArray();

    @Inject
    public AdapterDelegatesManager() {
    }

    public AdapterDelegatesManager<T> addDelegate(@NonNull ViewTypeDelegateClass delegate) {
        delegates.put(delegate.getItemViewType(), delegate);
        return this;
    }

    public int getItemViewType(@NonNull T items, int position) {
        int key = 0;
        for(int i = 0; i < delegates.size(); i++) {
            key = delegates.keyAt(i);
            AdapterDelegateInterface<T> delegate = (AdapterDelegateInterface<T>)delegates.get(key);
            if ( delegate.isForViewType(items, position) ) {
                return key;
            }
        }
        throw new IllegalArgumentException("No delegate found");
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegateInterface<T> delegate = (AdapterDelegateInterface<T>) delegates.get(viewType);
        if (delegate != null) {
            return delegate.onCreateViewHolder(parent);
        }
        throw new IllegalArgumentException("No delegate found");
    }

    public void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder viewHolder) {
        int key = 0;
        for(int i = 0; i < delegates.size(); i++) {
            key = delegates.keyAt(i);
            AdapterDelegateInterface<T> delegate = (AdapterDelegateInterface<T>)delegates.get(key);
            if ( delegate.isForViewType(items, position) ) {
                delegate.onBindViewHolder(items, position, viewHolder);
                return;
            }
        }
        throw new IllegalArgumentException("No delegate found");
    }

    @Override
    public void register(Activity activity) {
        for(int i = 0; i < delegates.size(); i++) {
            if( delegates.valueAt(i) instanceof RegisterBusEventInterface) {
                ((RegisterBusEventInterface)delegates.valueAt(i)).register(activity);
            }
        }
    }

    @Override
    public void unRegister(Activity activity) {
        for(int i = 0; i < delegates.size(); i++) {
            if( delegates.valueAt(i) instanceof RegisterBusEventInterface) {
                ((RegisterBusEventInterface)delegates.valueAt(i)).unRegister(activity);
            }
        }
    }
}
