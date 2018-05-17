package com.wecyberstage.wecyberstage.view.composeX;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

public class AvatarLineLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public AvatarLineLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void layout(RecyclerView.LayoutManager layoutManager, View view) {

    }
}
