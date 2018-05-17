package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class TimeLineView extends View {

    private Paint mPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;

    public TimeLineView(Context context) {
        super(context);
        init();
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(1);

        mPaint.setTextSize(30);
        mPaint.setTextAlign(Paint.Align.LEFT);
    }

    float duration;
    float intervalPixel;
    int leftOffset = 0;

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setLeftOffset(int leftOffset) {
        Log.i("TimeLineView", "setLeftOffset");
        this.leftOffset = leftOffset;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("TimeLineView", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        // 初始化bitmap,Canvas
        if( mCanvas == null || mBitmap == null) {
            Log.i("TimeLineView", "create buffer");
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }
        intervalPixel =  ((float) width) / (duration * 5f);
        Log.i("TimeLineView", "width: "+width +" intervalPixel: "+ intervalPixel);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("TimeLineView", "onDraw");
        drawPath();
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private void drawPath() {

        int offset = 0;
        int startCount = (int) (leftOffset / intervalPixel);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for(int i= startCount; i<duration*5+startCount+1; i++) {
            offset = (int) (intervalPixel * i) - leftOffset;
            if(i%5 == 0) {
                mCanvas.drawLine(offset,0, offset, 50, mPaint);
                mCanvas.drawText(""+ (int)((float)i/5f), offset + 8,50, mPaint);
            } else {
                mCanvas.drawLine(offset,0, offset, 20, mPaint);
            }
        }
    }

}