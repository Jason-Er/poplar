package com.wecyberstage.wecyberstage.view.compose;

import android.support.annotation.NonNull;

import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.view.common.AdapterDelegatesManager;
import com.wecyberstage.wecyberstage.view.common.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mike on 2018/3/17.
 */

@Singleton
public class KeyFrameAdapter extends ListDelegationAdapter {

    @Inject
    public KeyFrameAdapter(AdapterDelegatesManager<List<Object>> delegates) {
        super(delegates);
        delegatesManager
                .addDelegate(new StageInfoAdapterDelegate(KeyFrameCardViewType.STAGE.ordinal()))
                .addDelegate(new PropInfoAdapterDelegate(KeyFrameCardViewType.PROP.ordinal()))
                .addDelegate(new LineInfoAdapterDelegate(KeyFrameCardViewType.LINE.ordinal()))
                .addDelegate(new RoleInfoAdapterDelegate(KeyFrameCardViewType.ROLE.ordinal()));
    }

    public void setKeyframe(@NonNull KeyFrame keyframe) {
        items = new ArrayList<>();
        items.add(keyframe.stageInfo);
        /*
        items.addAll(keyframe.propInfoList);
        items.addAll(keyframe.roleInfoList);
        items.addAll(keyframe.lineInfoList);
        */
        notifyDataSetChanged();
    }

}
