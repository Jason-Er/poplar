package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import javax.inject.Inject;

public class ComposeXScriptAdapter extends ListDelegationAdapter {

    @Inject
    public ComposeXScriptAdapter(AdapterDelegatesManager<String> delegates) {
        super(delegates);
    }

    public void setComposeScript(@NonNull ComposeScript script) {

    }

}
