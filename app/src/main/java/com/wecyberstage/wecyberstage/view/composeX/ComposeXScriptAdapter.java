package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
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
import com.wecyberstage.wecyberstage.view.helper.LifeCycle;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ComposeXScriptAdapter extends ListDelegationAdapter implements UpdateStagePlayInterface, ComposeScriptHelper, LifeCycle {

    final private UpdateStagePlayInterface updateStagePlayInterface;
    private List<StageRole> stageRoleList;
    @Inject
    public ComposeXScriptAdapter(AdapterDelegatesManager<Object> delegates,
                                 UpdateStagePlayInterface updateStagePlayInterface,
                                 OnStartDragListener startDragListener) {
        super(delegates);
        delegatesManager
                .addDelegate(new StageLineAdapterDelegate(ComposeXCardViewType.STAGE_LINE.ordinal(), this, startDragListener))
                .addDelegate(new TimeLineAdapterDelegate(ComposeXCardViewType.TIME_LINE.ordinal()))
                .addDelegate(new RoleAdapterDelegate(ComposeXCardViewType.ROLE_MASK.ordinal()));
        this.updateStagePlayInterface = updateStagePlayInterface;

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
    public void updateStageLine(StageLine stageLine) {
        updateStagePlayInterface.updateStageLine(stageLine);
    }

    @Override
    public void addStageLine(StageLine stageLine) {
        updateStagePlayInterface.addStageLine(stageLine);
    }

    @Override
    public void deleteStageLine(StageLine stageLine) {
        updateStagePlayInterface.deleteStageLine(stageLine);
    }

    @Override
    public void swapStageLines(int position1, int position2) {
        updateStagePlayInterface.swapStageLines(position1, position2);
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

    @Override
    public void onResume(Activity activity) {
        delegatesManager.onResume(activity);
    }

    @Override
    public void onPause(Activity activity) {
        delegatesManager.onPause(activity);
    }

}
