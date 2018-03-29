package com.wecyberstage.wecyberstage.view.compose;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wecyberstage.wecyberstage.view.common.LayoutDelegatesManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Created by mike on 2018/3/17.
 */

@Singleton
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

        // layoutItemView(recycler);
    }

    private void layoutItemView(RecyclerView.Recycler recycler) {
        for(int i=0; i< getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            int viewType = getItemViewType(view);
            switch (KeyFrameCardViewType.values()[viewType]) {
                case STAGE:
                    addView(view);
                    layoutStage(view);
                    break;
                case ROLE:
                    Rect roleRect = ((RoleCardView) view).roleInfo.roleViewRect;
                    Point point = ((RoleCardView) view).roleInfo.point;
                    addView(view);
                    layoutRole(view, roleRect, point);
                    break;
                case PROP:
                    break;
                case LINE:
                    /*
                    addView(view);
                    layoutLine(view);
                    */
                    break;
            }
        }
    }

    private void layoutStage(View view) {
        measureChildWithMargins(view, 0, 0);

        int width = getDecoratedMeasuredWidth(view);
        int height = getDecoratedMeasuredHeight(view);
        int parentWidth = ((View)view.getParent()).getWidth();
        int parentHeight = ((View)view.getParent()).getHeight();

        layoutDecorated(view, (parentWidth-width)/2, (parentHeight-height)/2, (parentWidth+width)/2, (parentHeight+height)/2);
    }

    private void layoutRole(View view, Rect roleView, Point point) {
        Timber.d("layoutRole roleView: " + roleView);
        measureChildWithMargins(view, 0, 0);
        layoutDecorated(view, point.x + roleView.left, point.y + roleView.top, point.x + roleView.right, point.y + roleView.bottom);
    }

    private void layoutProp(View view, Rect propView, Point point) {

    }

    private void layoutLine(View view) {

    }
}
