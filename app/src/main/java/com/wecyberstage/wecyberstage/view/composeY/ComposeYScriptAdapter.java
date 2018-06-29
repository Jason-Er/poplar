package com.wecyberstage.wecyberstage.view.composeY;

import android.support.annotation.NonNull;

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
                .addDelegate(new AvatarLineAdapterStartDelegate(ComposeYCardViewType.START.ordinal()))
                .addDelegate(new AvatarLineAdapterEndDelegate(ComposeYCardViewType.END.ordinal()));
    }

    public void setComposeScript(@NonNull ComposeScript script) {
        dataSet = new ArrayList<>();
        List<Long> listStart = new ArrayList<>();
        List<Long> listEnd = new ArrayList<>();
        for(ComposeScript.AvatarLine avatarLine: script.avatarLines) {
            ComposeYCardViewType viewType;
            // TODO: 2018/5/22 need further refactoring: is user act some role then let the role id be start position
            if(listStart.contains(avatarLine.getLine().roleId)) {
                viewType = ComposeYCardViewType.START;
            } else if(listEnd.contains(avatarLine.getLine().roleId)) {
                viewType = ComposeYCardViewType.END;
            } else if(listStart.size() > listEnd.size()) {
                listEnd.add(avatarLine.getLine().roleId);
                viewType = ComposeYCardViewType.END;
            } else {
                listStart.add(avatarLine.getLine().roleId);
                viewType = ComposeYCardViewType.START;
            }
            ComposeYItemDto dto = new ComposeYItemDto(viewType, avatarLine);
            dataSet.add(dto);
        }
        notifyDataSetChanged();
    }

}
