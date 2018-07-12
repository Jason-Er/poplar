package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.ComposeLine;
import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.Role;
import com.wecyberstage.wecyberstage.model.UpdateComposeScriptInterface;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.helper.ComposeScriptHelper;
import com.wecyberstage.wecyberstage.view.helper.ItemTouchHelperAdapter;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ComposeXScriptAdapter extends ListDelegationAdapter implements ItemTouchHelperAdapter, UpdateComposeScriptInterface, ComposeScriptHelper {

    final private UpdateComposeScriptInterface updateComposeScriptInterface;
    private List<Role> roleList;
    @Inject
    public ComposeXScriptAdapter(AdapterDelegatesManager<Object> delegates,
                                 UpdateComposeScriptInterface updateComposeScriptInterface,
                                 OnStartDragListener startDragListener) {
        super(delegates);
        delegatesManager
                .addDelegate(new MaskLineAdapterDelegate(ComposeXCardViewType.MASK_LINE.ordinal(), startDragListener, this))
                .addDelegate(new TimeLineAdapterDelegate(ComposeXCardViewType.TIME_LINE.ordinal()));
        this.updateComposeScriptInterface = updateComposeScriptInterface;
    }

    public void setComposeScript(@NonNull ComposeScript script) {
        // for recyclerView show
        dataSet = new ArrayList<>();
        dataSet.add(new TimeLine());
        dataSet.addAll(script.composeLineList);
        // for whole scene info
        roleList = script.roleList;
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

    @Override
    public List<Mask> getMaskList() {
        return null;
    }

    @Override
    public Mask getMask(long maskId) {
        return null;
    }

    @Override
    public Mask getMaskByRole(long roleId) {
        Mask mask = null;
        for(Role role: roleList) {
            if(role.id == roleId) {
                mask = role.mask;
                break;
            }
        }
        return mask;
    }

    @Override
    public List<Role> getRoleList() {
        return null;
    }
}
