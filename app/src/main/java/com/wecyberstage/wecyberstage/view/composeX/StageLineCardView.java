package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.message.OutsideClickEvent;
import com.wecyberstage.wecyberstage.model.StageLine;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StageLineCardView extends RelativeLayout {

    private int position;
    private StageLine stageLine;

    @BindView(R.id.stageLine_mask)
    ImageView mask;
    @BindView(R.id.stageLine_dialogue)
    TextView dialogue;

    private GestureDetectorCompat detectorCompat;
    final GestureDetector.SimpleOnGestureListener gestureDetector = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("StageLineCardView","Long pressed");
        }
    };

    public StageLineCardView(@NonNull Context context) {
        super(context);
        detectorCompat = new GestureDetectorCompat(context, gestureDetector);
    }

    public StageLineCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        detectorCompat = new GestureDetectorCompat(context, gestureDetector);
    }

    public StageLineCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    private long lastTouchDown;
    private static int CLICK_ACTION_THRESHHOLD = 200;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (detectorCompat.onTouchEvent(ev)) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchDown = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHHOLD) {
                    Log.w("App", "You clicked!");
                    OutsideClickEvent event = new OutsideClickEvent("OUTSIDE_CLICK");
                    EventBus.getDefault().post(event);
                }
                break;
        }
        return true;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public StageLine getStageLine() {
        return stageLine;
    }

    public void setStageLine(StageLine stageLine) {
        this.stageLine = stageLine;
        dialogue.setText(stageLine.dialogue);
        Glide.with(getContext()).load(stageLine.getMaskGraph().graphURL).into(mask);
    }
}
