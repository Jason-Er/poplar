package com.wecyberstage.wecyberstage.view.compose;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wecyberstage.wecyberstage.view.common.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.common.ViewTypeDelegateClass;

import timber.log.Timber;

public class RoleInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface {

    public RoleInfoLayoutDelegate(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType() {
        return viewType;
    }

    @Override
    public void layout(RecyclerView.LayoutManager layoutManager, View view) {
        Timber.d("layoutRole roleView: ");
        Rect roleRect = ((RoleCardView) view).roleInfo.roleViewRect;
        Point point = ((RoleCardView) view).roleInfo.point;
        layoutManager.measureChildWithMargins(view, 0, 0);
        layoutManager.layoutDecorated(view, point.x + roleRect.left, point.y + roleRect.top, point.x + roleRect.right, point.y + roleRect.bottom);
    }
}
