package com.wecyberstage.wecyberstage.view.composeZ;

import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mike on 2018/3/17.
 */

public class KeyFrameLayoutManager extends RecyclerView.LayoutManager{

    private KeyFrameLayoutDelegateManager layoutDelegatesManager;
    private KeyFrameAdapter keyFrameAdapter;

    @Inject
    public KeyFrameLayoutManager(KeyFrameLayoutDelegateManager layoutDelegatesManager, KeyFrameAdapter keyFrameAdapter) {
        this.layoutDelegatesManager = layoutDelegatesManager;
        this.keyFrameAdapter = keyFrameAdapter;
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

        layoutDelegatesManager.onLayoutChildren(this, keyFrameAdapter.getDataSet(), recycler, state);
    }

}
