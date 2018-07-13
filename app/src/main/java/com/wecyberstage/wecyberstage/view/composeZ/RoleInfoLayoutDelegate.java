package com.wecyberstage.wecyberstage.view.composeZ;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

import timber.log.Timber;

class RoleInfoLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface<List<Object>> {

    public RoleInfoLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("RoleInfo","onLayoutChildren");
        for(Object item: items) {
            if( item instanceof KeyFrame.RoleInfo ) {
                int index = items.indexOf(item);
                View view = recycler.getViewForPosition(index);
                layoutManager.addView(view);
                RectF roleRectF = ((RoleCardView) view).roleInfo.roleViewRect;
                int parentHeight = ((View)view.getParent()).getHeight();
                layoutManager.measureChildWithMargins(view, 0, 0);
                layoutManager.layoutDecorated(view, (int)(parentHeight * roleRectF.left), (int)(parentHeight * roleRectF.top), (int)(parentHeight * roleRectF.right), (int)(parentHeight * roleRectF.bottom));
            }
        }
    }

    @Override
    public int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return 0;
    }

    @Override
    public int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return 0;
    }
}
