package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.v7.widget.RecyclerView;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegatesManager;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/17.
 */

public class KeyFrameLayoutManager extends RecyclerView.LayoutManager{

    protected LayoutDelegatesManager layoutDelegatesManager;

    @Inject
    public KeyFrameLayoutManager(LayoutDelegatesManager layoutDelegatesManager) {
        this.layoutDelegatesManager = layoutDelegatesManager;
        layoutDelegatesManager
                .addDelegate(new StageInfoLayoutDelegate(KeyFrameCardViewType.STAGE.ordinal()))
                .addDelegate(new RoleInfoLayoutDelegate(KeyFrameCardViewType.ROLE.ordinal()))
                .addDelegate(new PropInfoLayoutDelegate(KeyFrameCardViewType.PROP.ordinal()))
                .addDelegate(new LineInfoLayoutDelegate(KeyFrameCardViewType.LINE.ordinal()));
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //We have nothing to show for an empty data set but clear any existing views
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            //Nothing to do during prelayout when empty
            return;
        }
        detachAndScrapAttachedViews(recycler);

        // layout according to position
        layoutDelegatesManager.layoutItemView(this, recycler);
    }

}
