package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wecyberstage.wecyberstage.message.MessageEvent;
import com.wecyberstage.wecyberstage.message.OutsideClickEvent;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.message.MaskClickEvent;
import com.wecyberstage.wecyberstage.view.helper.MaskChoose;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RoleLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface<List<Object>>, RegisterBusEventInterface {

    private Map<Long, View> roleMap;
    public RoleLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("Popup","onLayoutChildren");
        roleMap = new HashMap<>();
        for(Object item: items) {
            if (item instanceof StageRole) {
                int index = items.indexOf(item);
                View view = recycler.getViewForPosition(index);
                layoutManager.addView(view);
                layoutManager.measureChildWithMargins(view, 0, 0);
                int width = layoutManager.getDecoratedMeasuredWidth(view);
                int height = layoutManager.getDecoratedMeasuredHeight(view);
                layoutManager.layoutDecorated(view, 0, 0, width, height);
                if(!roleMap.containsKey(((StageRole) item).id)) {
                    roleMap.put(((StageRole) item).id, view);
                }
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Iterator iterator = roleMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            layoutManager.detachView((View)entry.getValue());
            layoutManager.attachView((View)entry.getValue());
        }
        return dx;
    }

    @Override
    public int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dy;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMaskClickEventBus(MaskClickEvent event) {
        switch (event.getMessage()) {
            case "MASK_CLICK":
                Log.i("RoleLayoutDelegate","receive MASK_CLICK stageRole ID:" + event.getStageLine().roleId);
                ((MaskChoose)roleMap.get(event.getStageLine().roleId)).setStageLine(event.getStageLine());
                UICommon.showPopupWindow(roleMap.get(event.getStageLine().roleId), event.getRect());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOutsideClickEventBus(OutsideClickEvent event) {
        if(isAnyViewVisible()) {
            Iterator iterator = roleMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                ((View)entry.getValue()).setVisibility(View.INVISIBLE);
            }
        } else {
            MessageEvent messageEvent = new MessageEvent("CLICK");
            EventBus.getDefault().post(messageEvent);
        }
    }

    private boolean isAnyViewVisible() {
        boolean status = false;
        Iterator iterator = roleMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if(((View)entry.getValue()).getVisibility() == View.VISIBLE) {
                status = true;
                break;
            }
        }
        return status;
    }

    @Override
    public void register(Activity activity) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unRegister(Activity activity) {
        EventBus.getDefault().unregister(this);
    }
}