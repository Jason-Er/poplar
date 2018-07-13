package com.wecyberstage.wecyberstage.view.composeX;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.wecyberstage.wecyberstage.model.ComposeLine;
import com.wecyberstage.wecyberstage.view.recycler.LayoutDelegateInterface;
import com.wecyberstage.wecyberstage.view.recycler.ViewTypeDelegateClass;

import java.util.List;

public class MaskLineLayoutDelegate extends ViewTypeDelegateClass implements LayoutDelegateInterface<List<Object>> {

    private int leftOffset = 0;
    private int topOffset = 0;
    private float beginTime = 0;
    private final float TIME_SPAN = 10f; // 10 second for this view
    private final float MS_PERSECOND = 1000f;
    private SparseArray viewsMaxHeight;

    public MaskLineLayoutDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public void onLayoutChildren(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("ComposeLine","onLayoutChildren");
        fillVisibleChildren(layoutManager, items, recycler);
    }

    @Override
    public int scrollHorizontallyBy(RecyclerView.LayoutManager layoutManager, @NonNull List<Object> items, int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // calc totalLength
        int totalLength;
        if (items != null && items.size() > 1) {
            ComposeLine composeLine = (ComposeLine) items.get(items.size() -1);
            int tempLength = (int) (( (float) composeLine.line.beginTime / MS_PERSECOND + composeLine.line.duration) / TIME_SPAN * getHorizontalSpace(layoutManager));
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
            if( child instanceof MaskLineCardView) {
                int position = ((MaskLineCardView) child).getPosition();
                layoutManager.detachView(child);
                viewCache.put(position, child);
            }
        }
        viewsMaxHeight = new SparseArray();
        for(Object item: items) {
            if(item instanceof ComposeLine) {
                int index = items.indexOf(item);
                ComposeLine composeLine = (ComposeLine) item;
                if (isVisible(composeLine.line.beginTime, composeLine.line.duration)) {
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

                    if (viewsMaxHeight.get((int) composeLine.line.roleId) != null) {
                        int height = (int) viewsMaxHeight.get((int) composeLine.line.roleId);
                        if (height < mDecoratedChildHeight) {
                            viewsMaxHeight.setValueAt((int) composeLine.line.roleId, mDecoratedChildHeight);
                        }
                    } else {
                        viewsMaxHeight.put((int) composeLine.line.roleId, mDecoratedChildHeight);
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
            if(view instanceof MaskLineCardView) {
                int position = ((MaskLineCardView) view).getPosition();
                ComposeLine composeLine = (ComposeLine) items.get(position);

                int mDecoratedChildWidth = layoutManager.getDecoratedMeasuredWidth(view);
                int mDecoratedChildHeight = layoutManager.getDecoratedMeasuredHeight(view);

                layoutManager.layoutDecorated(view, (int) ((float) composeLine.line.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace(layoutManager)) - leftOffset,
                        (int) viewsMaxHeight.get((int) composeLine.line.roleId) - topOffset - mDecoratedChildHeight,
                        (int) ((float) composeLine.line.beginTime / MS_PERSECOND / TIME_SPAN * getHorizontalSpace(layoutManager)) + mDecoratedChildWidth - leftOffset,
                        (int) viewsMaxHeight.get((int) composeLine.line.roleId) - topOffset);
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
}
