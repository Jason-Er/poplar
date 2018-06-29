package com.wecyberstage.wecyberstage.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.view.composeX.ViewTypeInterface;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/26.
 */

public class ListDelegationAdapter extends RecyclerView.Adapter {

    protected List<Object> dataSet;
    protected AdapterDelegatesManager delegatesManager;

    @Inject
    public ListDelegationAdapter(AdapterDelegatesManager delegates) {
        this.delegatesManager = delegates;
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(dataSet, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(dataSet, position, holder);
    }

    @Override
    public int getItemCount() {
        return dataSet == null? 0 : dataSet.size();
    }

    public List<Object> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<Object> dataSet) {
        this.dataSet = dataSet;
    }
}
