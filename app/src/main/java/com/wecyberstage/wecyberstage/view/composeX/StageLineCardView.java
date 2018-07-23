package com.wecyberstage.wecyberstage.view.composeX;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.message.OutsideClickEvent;
import com.wecyberstage.wecyberstage.model.StageLine;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StageLineCardView extends RelativeLayout {

    private int position;
    private StageLine stageLine;

    @BindView(R.id.composeXCardLine_dragHandle)
    ImageView dragHandle;
    @BindView(R.id.composeXCardLine_lineMask)
    ImageView mask;
    @BindView(R.id.composeXCardLine_lineDialogue)
    TextView dialogue;

    public StageLineCardView(@NonNull Context context) {
        super(context);
    }

    public StageLineCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StageLineCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private long lastTouchDown;
    private static int CLICK_ACTION_THRESHHOLD = 200;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
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

    public StageLine getStageLine() {
        return stageLine;
    }

    public void setStageLine(StageLine stageLine) {
        this.stageLine = stageLine;
        dialogue.setText(stageLine.dialogue);
    }
}
