package com.wecyberstage.wecyberstage.view.compose;

import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

public class ComposeScriptLayoutManager extends RecyclerView.LayoutManager {
    @Inject
    public ComposeScriptLayoutManager() {
        
    }
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }
}
