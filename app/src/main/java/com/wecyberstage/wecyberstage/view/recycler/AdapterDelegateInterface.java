package com.wecyberstage.wecyberstage.view.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by mike on 2018/3/26.
 */

public interface AdapterDelegateInterface<T> {
    int getItemViewType();
    boolean isForViewType(@NonNull T items, int position);
    @NonNull
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);
    void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder holder);
}
