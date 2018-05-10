package com.wecyberstage.wecyberstage.view.composeX;

import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

public class ComposeXScriptLayoutManager extends RecyclerView.LayoutManager {
    @Inject
    public ComposeXScriptLayoutManager() {
        
    }
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }
}
