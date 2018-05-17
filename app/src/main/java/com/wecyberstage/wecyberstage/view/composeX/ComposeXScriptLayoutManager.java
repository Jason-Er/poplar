package com.wecyberstage.wecyberstage.view.composeX;

import android.support.v7.widget.RecyclerView;

import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegatesManager;

import javax.inject.Inject;

public class ComposeXScriptLayoutManager extends RecyclerView.LayoutManager {

    protected LayoutDelegatesManager layoutDelegatesManager;

    @Inject
    public ComposeXScriptLayoutManager(LayoutDelegatesManager layoutDelegatesManager) {
        this.layoutDelegatesManager = layoutDelegatesManager;
        layoutDelegatesManager
                .addDelegate(new TimeLineLayoutDelegate(ComposeXCardViewType.TIME_LINE.ordinal()))
                .addDelegate(new AvatarLineLayoutDelegate(ComposeXCardViewType.AVATAR_LINE.ordinal()));
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
