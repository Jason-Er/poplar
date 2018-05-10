package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

public class ComposeXScriptAdapter extends ListDelegationAdapter {

    @Inject
    public ComposeXScriptAdapter(AdapterDelegatesManager<Object> delegates) {
        super(delegates);
        delegatesManager
                .addDelegate(new LineInfoAdapterDelegate(ComposeXCardViewType.AVATAR_LINE.ordinal()));
    }

    public void setComposeScript(@NonNull ComposeScript script) {
        items = new ArrayList<>();
        // TODO: 2018/5/10  need further refactoring
        items.add("Hello");
        items.add("world");
        notifyDataSetChanged();
    }

}
