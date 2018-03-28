package com.wecyberstage.wecyberstage.view.common;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/26.
 */

public class ListDelegationAdapter extends RecyclerView.Adapter {

    protected List<Object> items;
    protected AdapterDelegatesManager delegatesManager;

    @Inject
    public ListDelegationAdapter(AdapterDelegatesManager delegates) {
        this.delegatesManager = delegates;
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override
    public int getItemCount() {
        return items == null? 0 : items.size();
    }
}
