package com.wecyberstage.wecyberstage.view.composeY;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageScene;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ComposeYScriptAdapter extends ListDelegationAdapter {

    @Inject
    public ComposeYScriptAdapter(AdapterDelegatesManager<Object> delegates) {
        super(delegates);
        delegatesManager
                .addDelegate(new StageLineStartAdapterDelegate(ComposeYCardViewType.START.ordinal()))
                .addDelegate(new StageLineEndAdapterDelegate(ComposeYCardViewType.END.ordinal()));
    }

    public void setStageScene(@NonNull StageScene stageScene) {
        dataSet = new ArrayList<>();
        List<Long> listStart = new ArrayList<>();
        List<Long> listEnd = new ArrayList<>();
        for(StageLine stageLine : stageScene.stageLines) {
            ComposeYCardViewType viewType;
            // TODO: 2018/5/22 need further refactoring: is user act some stageRole then let the stageRole id be start position
            if(listStart.contains(stageLine.roleId)) {
                viewType = ComposeYCardViewType.START;
            } else if(listEnd.contains(stageLine.roleId)) {
                viewType = ComposeYCardViewType.END;
            } else if(listStart.size() > listEnd.size()) {
                listEnd.add(stageLine.roleId);
                viewType = ComposeYCardViewType.END;
            } else {
                listStart.add(stageLine.roleId);
                viewType = ComposeYCardViewType.START;
            }
            ComposeYItemDto dto = new ComposeYItemDto(viewType, stageLine);
            dataSet.add(dto);
        }
        notifyDataSetChanged();
    }

}
