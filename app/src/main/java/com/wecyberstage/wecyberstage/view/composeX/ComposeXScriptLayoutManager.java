package com.wecyberstage.wecyberstage.view.composeX;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.wecyberstage.wecyberstage.view.helper.LifeCycle;

public class ComposeXScriptLayoutManager extends RecyclerView.LayoutManager implements LifeCycle {

    private ComposeXScriptAdapter adapter;
    private ComposeXLayoutDelegateManager delegatesManager;

    public ComposeXScriptLayoutManager(ComposeXScriptAdapter adapter,
                                       ComposeXLayoutDelegateManager<Object> delegates) {
        this.adapter = adapter;
        delegatesManager = delegates;
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

        delegatesManager.onLayoutChildren(this, adapter.getDataSet(), recycler, state);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return delegatesManager.scrollHorizontallyBy(this, adapter.getDataSet(), dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return delegatesManager.scrollVerticallyBy(this, adapter.getDataSet(), dy, recycler, state);
    }

    public void updateOneViewHolder(RecyclerView.ViewHolder viewHolder) {
        delegatesManager.updateOneViewHolder(this, adapter, viewHolder);
    }

    @Override
    public void onResume(Activity activity) {
        delegatesManager.onResume(activity);
    }

    @Override
    public void onPause(Activity activity) {
        delegatesManager.onPause(activity);
    }
}
