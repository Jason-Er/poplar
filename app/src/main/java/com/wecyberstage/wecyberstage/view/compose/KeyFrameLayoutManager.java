package com.wecyberstage.wecyberstage.view.compose;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mike on 2018/3/17.
 */

public class KeyFrameLayoutManager extends RecyclerView.LayoutManager{

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
        layoutItemView(recycler);
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
                    /*
                    Rect roleRect = ((RoleCardView) view).roleInfo.roleViewRect;
                    addView(view);
                    layoutRole(view, roleRect);
                    */
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

    private void layoutRole(View view, Rect roleView) {
        measureChildWithMargins(view, 0, 0);
        layoutDecorated(view, roleView.left, roleView.top, roleView.right, roleView.bottom);
    }

    private void layoutLine(View view) {

    }
}
