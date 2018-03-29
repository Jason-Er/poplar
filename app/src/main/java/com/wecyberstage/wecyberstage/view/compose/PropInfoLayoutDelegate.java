package com.wecyberstage.wecyberstage.view.compose;

import android.view.View;

import com.wecyberstage.wecyberstage.view.common.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.common.ViewTypeDelegateClass;

public class PropInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public PropInfoLayoutDelegate(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType() {
        return viewType;
    }

    @Override
    public void layout(View view) {

    }
}
