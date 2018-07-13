package com.wecyberstage.wecyberstage.view.composeX;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.wecyberstage.wecyberstage.model.ComposeLine;

import java.util.List;

public class ComposeXScriptLayoutManager extends RecyclerView.LayoutManager {

    private int leftOffset = 0;
    private int topOffset = 0;
    private float beginTime = 0;
    private TimeLineView timeLineView;
    private final float TIME_SPAN = 10f; // 10 second for this view
    private final float MS_PERSECOND = 1000f;
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
        /*
        if (getChildCount() == 0) { //First or empty layout
            fillVisibleChildren(recycler);
        }
        */
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
        /*
        int totalLength;
        List<Object> dataSet = adapter.getDataSet();
        if (dataSet != null && dataSet.size() > 1) {
            ComposeLine composeLine = (ComposeLine) dataSet.get(dataSet.size() -1);
            int tempLength = (int) (( (float) composeLine.line.beginTime / MS_PERSECOND + composeLine.line.duration) / TIME_SPAN * getHorizontalSpace());
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
        */
        return delegatesManager.scrollHorizontallyBy(this, adapter.getDataSet(), dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return delegatesManager.scrollVerticallyBy(this, adapter.getDataSet(), dy, recycler, state);
        // return super.scrollVerticallyBy(dy, recycler, state);
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingStart() - getPaddingEnd();
    }

    private SparseArray viewsMaxHeight;

    private void fillVisibleChildren(RecyclerView.Recycler recycler){

        SparseArray viewCache = new SparseArray();
        if(getChildCount() != 0) {
            // position 0 must be timeline view and other is avatarLine view
            for (int i=1; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                int position = ((MaskLineCardView) child).getPosition();
                viewCache.put(position, child);
            }
            for (int i=0; i < viewCache.size(); i++) {
                detachView((View) viewCache.valueAt(i));
            }
        }

        viewsMaxHeight = new SparseArray();
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
                case MASK_LINE: {
                    ComposeLine composeLine = (ComposeLine) dataSet.get(i);
                    if (isVisible(composeLine.line.beginTime, composeLine.line.duration)) {
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

                        if (viewsMaxHeight.get((int) composeLine.line.roleId) != null) {
                            int height = (int) viewsMaxHeight.get((int) composeLine.line.roleId);
                            if (height < mDecoratedChildHeight) {
                                viewsMaxHeight.setValueAt((int) composeLine.line.roleId, mDecoratedChildHeight);
                            }
                        } else {
                            viewsMaxHeight.put((int) composeLine.line.roleId, mDecoratedChildHeight);
                        }
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
            int position = ((MaskLineCardView) view).getPosition();
            ComposeLine composeLine = (ComposeLine) dataSet.get(position);

            mDecoratedChildWidth = getDecoratedMeasuredWidth(view);
            mDecoratedChildHeight = getDecoratedMeasuredHeight(view);

            layoutDecorated(view, (int) ( (float) composeLine.line.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace() ) - leftOffset,
                    (int) viewsMaxHeight.get((int) composeLine.line.roleId) - topOffset - mDecoratedChildHeight,
                    (int) ( (float) composeLine.line.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace()) + mDecoratedChildWidth - leftOffset,
                    (int) viewsMaxHeight.get((int) composeLine.line.roleId) - topOffset);
        }


        for (int i=0; i < viewCache.size(); i++) {
            recycler.recycleView((View) viewCache.valueAt(i));
        }
    }

    private boolean isVisible(float startTime, float duration) {
        if( startTime / MS_PERSECOND >= beginTime && startTime / MS_PERSECOND <= beginTime + TIME_SPAN || startTime / MS_PERSECOND + duration >= beginTime && startTime / MS_PERSECOND + duration <= beginTime  + TIME_SPAN) {
            return true;
        } else {
            return false;
        }
    }

    public void updateOneViewHolder(RecyclerView.ViewHolder viewHolder) {
        List<Object> dataSet = adapter.getDataSet();
        int position =  viewHolder.getAdapterPosition();
        ComposeLine composeLine = (ComposeLine) dataSet.get(position);
        long startTime = (long) ( viewHolder.itemView.getTranslationX() * TIME_SPAN * MS_PERSECOND / getHorizontalSpace() + (float) composeLine.line.beginTime );
        composeLine.line.beginTime = startTime;
        Log.i("LayoutManager","updateOneViewHolder startTime: "+startTime);

        View view = viewHolder.itemView;
        measureChildWithMargins(view, 0, 0);
        int mDecoratedChildWidth = getDecoratedMeasuredWidth(view);
        int mDecoratedChildHeight = getDecoratedMeasuredHeight(view);
        layoutDecorated(view, (int) ( (float) composeLine.line.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace()) - leftOffset,
                (int) viewsMaxHeight.get((int) composeLine.line.roleId) - topOffset - mDecoratedChildHeight,
                (int) ( (float) composeLine.line.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace()) + mDecoratedChildWidth - leftOffset,
                (int) viewsMaxHeight.get((int) composeLine.line.roleId) - topOffset);

        adapter.updateComposeLine(composeLine, position-1); // "position -1" for the first place is for timeLine view
    }
}
