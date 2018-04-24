package com.wecyberstage.wecyberstage.view.compose;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.recycler.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.recycler.ListDelegationAdapter;

import javax.inject.Inject;

public class ComposeScriptAdapter extends ListDelegationAdapter {

    @Inject
    public ComposeScriptAdapter(AdapterDelegatesManager<String> delegates) {
        super(delegates);
    }

    public void setComposeScript(@NonNull ComposeScript script) {

    }
}
