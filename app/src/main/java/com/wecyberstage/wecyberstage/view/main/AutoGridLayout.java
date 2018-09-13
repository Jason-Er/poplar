package com.wecyberstage.wecyberstage.view.main;

import android.widget.GridLayout;
import android.content.Context;
import android.util.AttributeSet;

public class AutoGridLayout extends GridLayout {

    private int columnWidth;

    public AutoGridLayout(Context context) {
        super(context);
    }

    public AutoGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        int width = MeasureSpec.getSize(widthSpec);
        if (columnWidth > 0 && width > 0) {
            int totalSpace = width - getPaddingRight() - getPaddingLeft();
            int columnCount = Math.max(1, totalSpace / columnWidth);
            setColumnCount(columnCount);
        }
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }
}
