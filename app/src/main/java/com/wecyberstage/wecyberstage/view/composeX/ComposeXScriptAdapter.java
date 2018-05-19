package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.util.label.PerActivity;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

public class ComposeXScriptAdapter extends ListDelegationAdapter {

    @Inject
    public ComposeXScriptAdapter(AdapterDelegatesManager<Object> delegates) {
        super(delegates);
        delegatesManager
                .addDelegate(new AvatarLineAdapterDelegate(ComposeXCardViewType.AVATAR_LINE.ordinal()))
                .addDelegate(new TimeLineAdapterDelegate(ComposeXCardViewType.TIME_LINE.ordinal()));
    }

    public void setComposeScript(@NonNull ComposeScript script) {
        dataSet = new ArrayList<>();
        dataSet.add(new TimeLine());
        dataSet.addAll(script.avatarLines);
        notifyDataSetChanged();
    }

}
