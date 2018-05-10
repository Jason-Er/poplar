package com.wecyberstage.wecyberstage.view.recycler;

public class ViewTypeDelegateClass {
    protected int viewType;

    public ViewTypeDelegateClass(int viewType) {
        this.viewType = viewType;
    }

    public int getItemViewType() {
        return viewType;
    }
}
