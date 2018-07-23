package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

public class StageLineLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface<List<Object>> {

    private int leftOffset = 0;
    private int topOffset = 0;
    private float beginTime = 0;
    private final float TIME_SPAN = 10f; // 10 second for this view
    private final float MS_PERSECOND = 1000f;
    private SparseArray viewsMaxHeight;

    public StageLineLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("StageLine","onLayoutChildren");
        fillVisibleChildren(layoutManager, items, recycler);
    }

    @Override
    public int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // calc totalLength
        int totalLength;
        if (items != null && items.size() > 1) {
            StageLine StageLine = (StageLine) items.get(items.size() -1);
            int tempLength = (int) (( (float) StageLine.beginTime / MS_PERSECOND + StageLine.voice.duration) / TIME_SPAN * getHorizontalSpace(layoutManager));
            totalLength = tempLength > getHorizontalSpace(layoutManager) ? tempLength : getHorizontalSpace(layoutManager);
        } else {
            totalLength = getHorizontalSpace(layoutManager);
        }

        int deltaX = 0;
        int deltaReturn = 0;
        int temp = leftOffset + dx;

        if(temp >= 0 && temp <= totalLength - getHorizontalSpace(layoutManager)) {
            deltaX = dx;
            deltaReturn = dx;
            leftOffset += dx;
        } else if( temp < 0 ) {
            deltaX = -leftOffset;
            deltaReturn = -(leftOffset + dx);
            leftOffset = 0;
        } else if( temp > totalLength - getHorizontalSpace(layoutManager) ) {
            deltaX = totalLength - getHorizontalSpace(layoutManager) - leftOffset;
            deltaReturn = -(temp - totalLength + getHorizontalSpace(layoutManager));
            leftOffset = totalLength - getHorizontalSpace(layoutManager);
        }
        beginTime = leftOffset * TIME_SPAN / getHorizontalSpace(layoutManager);

        layoutManager.offsetChildrenHorizontal(-deltaX);
        fillVisibleChildren(layoutManager, items, recycler);

        return deltaReturn;
    }

    @Override
    public int scrollVerticallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return dy;
    }

    private void fillVisibleChildren(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, RecyclerView.Recycler recycler) {
        SparseArray viewCache = new SparseArray();
        for (int i=0; i < layoutManager.getChildCount(); i++) {
            final View child = layoutManager.getChildAt(i);
            if( child instanceof StageLineCardView) {
                int position = ((StageLineCardView) child).getPosition();
                layoutManager.detachView(child);
                viewCache.put(position, child);
            }
        }
        viewsMaxHeight = new SparseArray();
        for(Object item: items) {
            if(item instanceof StageLine) {
                int index = items.indexOf(item);
                StageLine StageLine = (StageLine) item;
                if (isVisible(StageLine.beginTime, StageLine.voice.duration)) {
                    View view = (View) viewCache.get(index);
                    if (view != null) {
                        layoutManager.attachView(view);
                        viewCache.remove(index);
                    } else {
                        view = recycler.getViewForPosition(index);
                        layoutManager.addView(view);
                    }
                    layoutManager.measureChildWithMargins(view, 0, 0);
                    int mDecoratedChildWidth = layoutManager.getDecoratedMeasuredWidth(view);
                    int mDecoratedChildHeight = layoutManager.getDecoratedMeasuredHeight(view);

                    if (viewsMaxHeight.get((int) StageLine.roleId) != null) {
                        int height = (int) viewsMaxHeight.get((int) StageLine.roleId);
                        if (height < mDecoratedChildHeight) {
                            viewsMaxHeight.setValueAt((int) StageLine.roleId, mDecoratedChildHeight);
                        }
                    } else {
                        viewsMaxHeight.put((int) StageLine.roleId, mDecoratedChildHeight);
                    }
                }
            }
        }

        int totalHeight = 0;
        for(int i = 0, size = viewsMaxHeight.size(); i < size; i++) {
            totalHeight += (int) viewsMaxHeight.valueAt(i);
            viewsMaxHeight.setValueAt(i, totalHeight);
        }
        topOffset = (totalHeight - getVerticalSpace(layoutManager))/2;
        for(int i = 0; i < layoutManager.getChildCount(); i++) {
            View view = layoutManager.getChildAt(i);
            if(view instanceof StageLineCardView) {
                int position = ((StageLineCardView) view).getPosition();
                StageLine StageLine = (StageLine) items.get(position);

                int mDecoratedChildWidth = layoutManager.getDecoratedMeasuredWidth(view);
                int mDecoratedChildHeight = layoutManager.getDecoratedMeasuredHeight(view);

                layoutManager.layoutDecorated(view, (int) ((float) StageLine.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace(layoutManager)) - leftOffset,
                        (int) viewsMaxHeight.get((int) StageLine.roleId) - topOffset - mDecoratedChildHeight,
                        (int) ((float) StageLine.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace(layoutManager)) + mDecoratedChildWidth - leftOffset,
                        (int) viewsMaxHeight.get((int) StageLine.roleId) - topOffset);
            }
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

    private int getVerticalSpace(RecyclerView.LayoutManager layoutManager) {
        return layoutManager.getHeight() - layoutManager.getPaddingTop() - layoutManager.getPaddingBottom();
    }

    private int getHorizontalSpace(RecyclerView.LayoutManager layoutManager) {
        return layoutManager.getWidth() - layoutManager.getPaddingStart() - layoutManager.getPaddingEnd();
    }

    public void updateOneViewHolder(RecyclerView.LayoutManager layoutManager,  ComposeXScriptAdapter adapter, RecyclerView.ViewHolder viewHolder) {
        List<Object> dataSet = adapter.getDataSet();
        int position =  viewHolder.getAdapterPosition();
        StageLine StageLine = (StageLine) dataSet.get(position);
        long startTime = (long) ( viewHolder.itemView.getTranslationX() * TIME_SPAN * MS_PERSECOND / getHorizontalSpace(layoutManager) + (float) StageLine.beginTime );
        StageLine.beginTime = startTime;
        Log.i("LayoutManager","updateOneViewHolder startTime: "+startTime);

        View view = viewHolder.itemView;
        layoutManager.measureChildWithMargins(view, 0, 0);
        int mDecoratedChildWidth = layoutManager.getDecoratedMeasuredWidth(view);
        int mDecoratedChildHeight = layoutManager.getDecoratedMeasuredHeight(view);
        layoutManager.layoutDecorated(view, (int) ( (float) StageLine.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace(layoutManager)) - leftOffset,
                (int) viewsMaxHeight.get((int) StageLine.roleId) - topOffset - mDecoratedChildHeight,
                (int) ( (float) StageLine.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace(layoutManager)) + mDecoratedChildWidth - leftOffset,
                (int) viewsMaxHeight.get((int) StageLine.roleId) - topOffset);

        adapter.updateStageLine(StageLine); // "position -1" for the first place is for timeLine view
    }
}
