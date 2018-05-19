package com.wecyberstage.wecyberstage.view.composeX;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.wecyberstage.wecyberstage.model.ComposeScript;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegatesManager;

import java.util.List;

import javax.inject.Inject;

public class ComposeXScriptLayoutManager extends RecyclerView.LayoutManager {

    private int leftOffset = 0;
    private int topOffset = 0;
    private float beginTime = 0;
    private TimeLineView timeLineView;
    private final float TIME_SPAN = 10f; // 10 second for this view
    private ComposeXScriptAdapter adapter;

    @Inject
    public ComposeXScriptLayoutManager(ComposeXScriptAdapter adapter) {
        this.adapter = adapter;
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

        if (getChildCount() == 0) { //First or empty layout
            fillVisibleChildren(recycler);
        }
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
        int totalLength;
        List<Object> dataSet = adapter.getDataSet();
        if (dataSet.size() > 1) {
            ComposeScript.Avatar_Line avatarLine = (ComposeScript.Avatar_Line) dataSet.get(dataSet.size() -1);
            int tempLength = (int) ((avatarLine.getLine().startTime + avatarLine.getLine().duration) / TIME_SPAN * getHorizontalSpace());
            totalLength = tempLength > getHorizontalSpace() ? tempLength : getHorizontalSpace();
        } else {
            totalLength = getHorizontalSpace();
        }
        int deltaX = 0;
        int deltaReturn = 0;
        int temp = leftOffset + dx;

        if(temp >= 0 && temp <= totalLength - getHorizontalSpace()) {
            deltaX = dx;
            deltaReturn = dx;
            leftOffset += dx;
        } else if( temp < 0 ) {
            deltaX = -leftOffset;
            deltaReturn = -(leftOffset + dx);
            leftOffset = 0;
        } else if( temp > totalLength - getHorizontalSpace() ) {
            deltaX = totalLength - getHorizontalSpace() - leftOffset;
            deltaReturn = -(temp - totalLength + getHorizontalSpace());
            leftOffset = totalLength - getHorizontalSpace();
        }
        beginTime = leftOffset * TIME_SPAN / getHorizontalSpace();
        timeLineView.setLeftOffset(leftOffset);
        offsetChildrenHorizontal(-deltaX);
        fillVisibleChildren(recycler);
        timeLineView.invalidate();
        return deltaReturn;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingStart() - getPaddingEnd();
    }

    private void fillVisibleChildren(RecyclerView.Recycler recycler){

        SparseArray viewCache = new SparseArray();
        if(getChildCount() != 0) {
            // position 0 must be timeline view and other is avatarLine view
            for (int i=1; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                int position = ((AvatarLineCardView) child).getPosition();
                viewCache.put(position, child);
            }
            for (int i=0; i < viewCache.size(); i++) {
                detachView((View) viewCache.valueAt(i));
            }
        }

        SparseArray viewsMaxHeight = new SparseArray();
        List<Object> dataSet = adapter.getDataSet();

        int mDecoratedChildWidth, mDecoratedChildHeight;
        for(int i = 0; i < getItemCount(); i++) {
            switch ( ComposeXCardViewType.values()[adapter.getItemViewType(i)] ) {
                case TIME_LINE: {
                    View view;
                    if (getChildCount() == 0) {
                        view = recycler.getViewForPosition(i);
                        addView(view);
                        timeLineView = (TimeLineView) view;
                    } else {
                        view = getChildAt(0);
                    }
                    measureChildWithMargins(view, 0, 0);
                    mDecoratedChildWidth = getDecoratedMeasuredWidth(view);
                    mDecoratedChildHeight = getDecoratedMeasuredHeight(view);
                    layoutDecorated(view, 0, 0, mDecoratedChildWidth, mDecoratedChildHeight);
                    Log.i("fillVisibleChildren", "mDecoratedChildWidth: " + mDecoratedChildWidth + " mDecoratedChildHeight: " + mDecoratedChildHeight);
                    break;
                }
                case AVATAR_LINE: {
                    ComposeScript.Avatar_Line avatarLine = (ComposeScript.Avatar_Line) dataSet.get(i);
                    if (isVisible(avatarLine.getLine().startTime, avatarLine.getLine().duration)) {
                        View view = (View) viewCache.get(i);
                        if (view != null) {
                            attachView(view);
                            viewCache.remove(i);
                        } else {
                            view = recycler.getViewForPosition(i);
                            addView(view);
                        }
                        // TODO: 2018/5/19 widthUsed need calculate further
                        measureChildWithMargins(view, 0, 0);
                        mDecoratedChildWidth = getDecoratedMeasuredWidth(view);
                        mDecoratedChildHeight = getDecoratedMeasuredHeight(view);

                        if (viewsMaxHeight.get((int) avatarLine.getLine().roleId) != null) {
                            int height = (int) viewsMaxHeight.get((int) avatarLine.getLine().roleId);
                            if (height < mDecoratedChildHeight) {
                                viewsMaxHeight.setValueAt((int) avatarLine.getLine().roleId, mDecoratedChildHeight);
                            }
                        } else {
                            viewsMaxHeight.put((int) avatarLine.getLine().roleId, mDecoratedChildHeight);
                        }

                        /*
                        if (i % 2 == 0) {
                            layoutDecorated(view, (i - 1) * 3 * getHorizontalSpace() / 10 - leftOffset, topOffset,
                                    (i - 1) * 3 * getHorizontalSpace() / 10 + mDecoratedChildWidth - leftOffset,
                                    topOffset + mDecoratedChildHeight);
                        } else {
                            layoutDecorated(view, (i - 1) * 3 * getHorizontalSpace() / 10 - leftOffset, topOffset + mDecoratedChildHeight,
                                    (i - 1) * 3 * getHorizontalSpace() / 10 + mDecoratedChildWidth - leftOffset,
                                    topOffset + mDecoratedChildHeight + mDecoratedChildHeight);
                        }
                        */
                    }
                    break;
                }
            }
        }

        int totalHeight = 0;
        for(int i = 0, size = viewsMaxHeight.size(); i < size; i++) {
            totalHeight += (int) viewsMaxHeight.valueAt(i);
            viewsMaxHeight.setValueAt(i, totalHeight);
        }
        topOffset = (totalHeight - getVerticalSpace())/2;
        Log.i("fillVisibleChildren","topOffset"+topOffset+" totalHeight: "+totalHeight);
        for(int i = 1; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int position = ((AvatarLineCardView) view).getPosition();
            ComposeScript.Avatar_Line avatarLine = (ComposeScript.Avatar_Line) dataSet.get(position);

            mDecoratedChildWidth = getDecoratedMeasuredWidth(view);
            mDecoratedChildHeight = getDecoratedMeasuredHeight(view);

            layoutDecorated(view, (int) ( avatarLine.getLine().startTime / TIME_SPAN * getHorizontalSpace()) - leftOffset,
                    (int) viewsMaxHeight.get((int) avatarLine.getLine().roleId) - topOffset - mDecoratedChildHeight,
                    (int) ( avatarLine.getLine().startTime / TIME_SPAN * getHorizontalSpace()) + mDecoratedChildWidth - leftOffset,
                    (int) viewsMaxHeight.get((int) avatarLine.getLine().roleId) - topOffset);
        }


        for (int i=0; i < viewCache.size(); i++) {
            recycler.recycleView((View) viewCache.valueAt(i));
        }
    }

    private boolean isVisible(float startTime, float duration) {
        if( startTime >= beginTime && startTime <= beginTime + 10 || startTime + duration >= beginTime && startTime + duration <= beginTime + 10) {
            return true;
        } else {
            return false;
        }
    }

}
