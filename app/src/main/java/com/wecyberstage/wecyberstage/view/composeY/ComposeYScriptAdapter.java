package com.wecyberstage.wecyberstage.view.composeY;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.ComposeLine;
import com.wecyberstage.wecyberstage.model.ComposeScript;
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
                .addDelegate(new ComposeLineAdapterStartDelegate(ComposeYCardViewType.START.ordinal()))
                .addDelegate(new ComposeLineAdapterEndDelegate(ComposeYCardViewType.END.ordinal()));
    }

    public void setComposeScript(@NonNull ComposeScript script) {
        dataSet = new ArrayList<>();
        List<Long> listStart = new ArrayList<>();
        List<Long> listEnd = new ArrayList<>();
        for(ComposeLine composeLine: script.composeLineList) {
            ComposeYCardViewType viewType;
            // TODO: 2018/5/22 need further refactoring: is user act some role then let the role id be start position
            if(listStart.contains(composeLine.line.roleId)) {
                viewType = ComposeYCardViewType.START;
            } else if(listEnd.contains(composeLine.line.roleId)) {
                viewType = ComposeYCardViewType.END;
            } else if(listStart.size() > listEnd.size()) {
                listEnd.add(composeLine.line.roleId);
                viewType = ComposeYCardViewType.END;
            } else {
                listStart.add(composeLine.line.roleId);
                viewType = ComposeYCardViewType.START;
            }
            ComposeYItemDto dto = new ComposeYItemDto(viewType, composeLine);
            dataSet.add(dto);
        }
        notifyDataSetChanged();
    }

}
