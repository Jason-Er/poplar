package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegateInterface;

class PropInfoAdapterDelegate extends ViewTypeDelegateClass implements AdapterDelegateInterface {

    public PropInfoAdapterDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public boolean isForViewType(@NonNull Object items, int position) {
        return false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Object items, int position, @NonNull RecyclerView.ViewHolder holder) {

    }
}
