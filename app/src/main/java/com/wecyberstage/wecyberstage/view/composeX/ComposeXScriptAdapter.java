package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.ComposeLine;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.model.UpdateComposeScriptInterface;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.helper.ItemTouchHelperAdapter;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

public class ComposeXScriptAdapter extends ListDelegationAdapter implements ItemTouchHelperAdapter, UpdateComposeScriptInterface {

    final private UpdateComposeScriptInterface updateComposeScriptInterface;

    @Inject
    public ComposeXScriptAdapter(AdapterDelegatesManager<Object> delegates,
                                 UpdateComposeScriptInterface updateComposeScriptInterface,
                                 OnStartDragListener startDragListener) {
        super(delegates);
        delegatesManager
                .addDelegate(new ComposeLineAdapterDelegate(ComposeXCardViewType.AVATAR_LINE.ordinal(), startDragListener))
                .addDelegate(new TimeLineAdapterDelegate(ComposeXCardViewType.TIME_LINE.ordinal()));
        this.updateComposeScriptInterface = updateComposeScriptInterface;
    }

    public void setComposeScript(@NonNull ComposeScript script) {
        dataSet = new ArrayList<>();
        dataSet.add(new TimeLine());
        dataSet.addAll(script.composeLineList);
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    public void updateComposeLine(ComposeLine composeLine, int ordinal) {
        updateComposeScriptInterface.updateComposeLine(composeLine, ordinal);
    }
}
