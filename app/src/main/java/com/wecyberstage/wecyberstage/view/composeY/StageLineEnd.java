package com.wecyberstage.wecyberstage.view.composeY;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.StageLine;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StageLineEnd extends RelativeLayout {

    private StageLine stageLine;

    @BindView(R.id.composeY_stageLine_end_image)
    ImageView mask;
    @BindView(R.id.composeY_stageLine_end_text)
    TextView dialogue;

    public StageLineEnd(Context context) {
        super(context);
    }

    public StageLineEnd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StageLineEnd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public StageLine getStageLine() {
        return stageLine;
    }

    public void setStageLine(StageLine stageLine) {
        this.stageLine = stageLine;
        dialogue.setText(stageLine.dialogue);
        if(stageLine.getMaskGraph() != null)
            Glide.with(getContext()).load(stageLine.getMaskGraph().graphURL).into(mask);
    }

}
