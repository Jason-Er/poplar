package com.wecyberstage.wecyberstage.view.compose;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.Matrix;
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


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO: 8/30/2017 need be removed later
        super.onDraw(canvas);
        Paint paint = new Paint();
        float[] VPMatrix = {1.6875f,
                0.0f,
                0.0f,
                0.0f,
                0.0f,
                0.23138356f,
                1.2185816f,
                0.99702126f,
                0.0f,
                2.9910638f,
                -0.094267376f,
                -0.07712785f,
                0.0f,
                -8.262117f,
                13.207706f,
                16.26085f};

        float[] stageVerticesTrans = new float[stageVertices.length];
        for(int i=0; i< stageVertices.length/4; i++) {
            Matrix.multiplyMV(stageVerticesTrans, i*4, VPMatrix, 0, stageVertices, i*4);
            stageVerticesTrans[i*4] /= stageVerticesTrans[i*4 + 3];
            stageVerticesTrans[i*4 + 1] /= stageVerticesTrans[i*4 + 3];
            stageVerticesTrans[i*4 + 2] /= stageVerticesTrans[i*4 + 3];
        }
        int[] stageViewPoints = new int[stageVertices.length/2];
        for(int i=0; i<stageViewPoints.length/2; i++) {
            stageViewPoints[i * 2] = (int)((stageVerticesTrans[i * 4]+ 1) * (canvas.getWidth() / 2));
            stageViewPoints[i * 2 + 1] = (int)(-(stageVerticesTrans[i * 4 + 1]  + 1) * (canvas.getHeight() / 2) + canvas.getHeight());
        }
        for(int i=0; i<stageViewPoints.length/2; i++) {
            canvas.drawLine(stageViewPoints[i%(stageViewPoints.length/2)*2], stageViewPoints[i%(stageViewPoints.length/2)*2+1],
                    stageViewPoints[(i+1)%(stageViewPoints.length/2)*2], stageViewPoints[(i+1)%(stageViewPoints.length/2)*2+1], paint);
        }

        /*
        canvas.drawLine(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1, paint);
        canvas.drawLine(canvas.getWidth() - 1, 0, 0, canvas.getHeight() - 1, paint);
        */
    }
}
