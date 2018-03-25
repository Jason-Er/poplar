package com.wecyberstage.wecyberstage.view.compose;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.wecyberstage.wecyberstage.R;

/**
 * Created by mike on 2018/3/17.
 */

public class StageCardView extends CardView {

    private float ratio;
    private float[] stageVertices;

    public StageCardView(Context context) {
        super(context);
    }

    public StageCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StageCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StageCardView,
                0, 0);
        try {
            ratio = a.getFloat(R.styleable.StageCardView_ratio, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);

        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);

        int calculatedHeight = (int)(originalWidth / ratio);

        int finalWidth, finalHeight;

        if (calculatedHeight > originalHeight)
        {
            finalWidth = (int)(originalHeight * ratio);
            finalHeight = originalHeight;
        }
        else
        {
            finalWidth = originalWidth;
            finalHeight = calculatedHeight;
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }

    public float[] getStageVertices() {
        return stageVertices;
    }

    public void setStageVertices(float[] stageVertices) {
        this.stageVertices = stageVertices;
    }

}
