package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageLineHandle;
import com.wecyberstage.wecyberstage.model.StagePlay;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.model.TimeLine;
import com.wecyberstage.wecyberstage.view.composeY.OnStartDragListener;
import com.wecyberstage.wecyberstage.view.helper.ComposeScriptHelper;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursor;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ComposeXScriptAdapter extends ListDelegationAdapter implements StageLineHandle, ComposeScriptHelper, RegisterBusEventInterface {

    final private StageLineHandle stageLineHandle;
    private List<StageRole> stageRoleList;
    StagePlay stagePlay;

    @Inject
    public ComposeXScriptAdapter(AdapterDelegatesManager<Object> delegates,
                                 StageLineHandle stageLineHandle,
                                 OnStartDragListener startDragListener) {
        super(delegates);
        delegatesManager
                .addDelegate(new StageLineAdapterDelegate(ComposeXCardViewType.STAGE_LINE.ordinal(), this, startDragListener))
                .addDelegate(new TimeLineAdapterDelegate(ComposeXCardViewType.TIME_LINE.ordinal()))
                .addDelegate(new RoleAdapterDelegate(ComposeXCardViewType.ROLE_MASK.ordinal()));
        this.stageLineHandle = stageLineHandle;

    }

    public void setStagePlay(@NonNull StagePlay stagePlay, StagePlayCursor stagePlayCursor) {
        this.stagePlay = stagePlay;
        StageScene stageScene = stagePlay.scenes.get(stagePlayCursor.getOrdinal());
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
        stageLineHandle.updateStageLine(stageLine);
    }

    @Override
    public void addStageLine(StageLine stageLine) {
        stageLineHandle.addStageLine(stageLine);
    }

    @Override
    public void deleteStageLine(StageLine stageLine) {
        stageLineHandle.deleteStageLine(stageLine);
    }

    @Override
    public void swapStageLines(int position1, int position2) {
        stageLineHandle.swapStageLines(position1, position2);
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
    public void register(Activity activity) {
        delegatesManager.register(activity);
    }

    @Override
    public void unRegister(Activity activity) {
        delegatesManager.unRegister(activity);
    }

}
