package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.TimeLine;
import com.wecyberstage.wecyberstage.model.UpdateStagePlayInterface;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.helper.ComposeScriptHelper;
import com.wecyberstage.wecyberstage.view.helper.ItemTouchHelperAdapter;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ComposeXScriptAdapter extends ListDelegationAdapter implements ItemTouchHelperAdapter, UpdateStagePlayInterface, ComposeScriptHelper {

    final private UpdateStagePlayInterface updateStagePlayInterface;
    private List<StageRole> stageRoleList;
    @Inject
    public ComposeXScriptAdapter(AdapterDelegatesManager<Object> delegates,
                                 UpdateStagePlayInterface updateStagePlayInterface,
                                 OnStartDragListener startDragListener) {
        super(delegates);
        delegatesManager
                .addDelegate(new StageLineAdapterDelegate(ComposeXCardViewType.MASK_LINE.ordinal(), startDragListener, this))
                .addDelegate(new TimeLineAdapterDelegate(ComposeXCardViewType.TIME_LINE.ordinal()))
                .addDelegate(new RoleAdapterDelegate(ComposeXCardViewType.ROLE_MASK.ordinal()));
        this.updateStagePlayInterface = updateStagePlayInterface;
        // EventBus.getDefault().register(this);
    }

    public void setStageScene(@NonNull StageScene stageScene) {
        // for recyclerView show
        dataSet = new ArrayList<>();
        dataSet.add(new TimeLine());
        dataSet.addAll(stageScene.stageRoles);
        dataSet.addAll(stageScene.stageLines);
        // for whole scene info
        stageRoleList = stageScene.stageRoles;
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    public void updateStageLine(StageLine stageLine) {
        updateStagePlayInterface.updateStageLine(stageLine);
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
        for(StageRole stageRole : stageRoleList) {
            if(stageRole.id == roleId) {
                mask = stageRole.mask;
                break;
            }
        }
        return mask;
    }

    @Override
    public List<StageRole> getStageRoleList() {
        return null;
    }

    /*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseEventBus(MaskClickEvent event) {
        switch (event.getMessage()) {
            case "MASK_CLICK":
                Log.i("ComposeXScriptAdapter","receive MASK_CLICK");
                // notifyItemChanged(1);
                break;
        }
    }
    */

}
