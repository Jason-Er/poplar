package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.wecyberstage.wecyberstage.model.Role;
import com.wecyberstage.wecyberstage.view.helper.LifeCycle;
import com.wecyberstage.wecyberstage.view.helper.RecyclerViewEvent;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface<List<Object>>, LifeCycle {

    private Map<Long, View> roleMap;
    public RoleLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("Popup","onLayoutChildren");
        roleMap = new HashMap<>();
        for(Object item: items) {
            if (item instanceof Role) {
                int index = items.indexOf(item);
                View view = recycler.getViewForPosition(index);
                layoutManager.addView(view);
                layoutManager.measureChildWithMargins(view, 0, 0);
                int width = layoutManager.getDecoratedMeasuredWidth(view);
                int height = layoutManager.getDecoratedMeasuredHeight(view);
                layoutManager.layoutDecorated(view, 0, 0, width, height);
                if(!roleMap.containsKey(((Role) item).id)) {
                    roleMap.put(((Role) item).id, view);
                }
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dx;
    }

    @Override
    public int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dy;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseEventBus(RecyclerViewEvent event) {
        switch (event.getMessage()) {
            case "MASK_CLICK":
                Log.i("RoleLayoutDelegate","receive MASK_CLICK");
                // notifyItemChanged(1);
                break;
        }
    }

    @Override
    public void onResume(Activity activity) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause(Activity activity) {
        EventBus.getDefault().unregister(this);
    }
}
