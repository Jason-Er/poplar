package com.wecyberstage.wecyberstage.view.helper;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.message.MaskChooseEvent;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.util.helper.UICommon;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaskChoose extends RelativeLayout {

    @BindView(R.id.mask_choose_frame)
    GridLayout masksFrame;
    private StageLine stageLine;

    private class MaskIcon extends android.support.v7.widget.AppCompatImageButton {

        private MaskGraph maskGraph;

        public MaskIcon(Context context) {
            super(context);
        }

        public MaskGraph getMaskGraph() {
            return maskGraph;
        }

        public void setMaskGraph(MaskGraph maskGraph) {
            this.maskGraph = maskGraph;
        }

    }

    public MaskChoose(Context context) {
        super(context);
    }

    public MaskChoose(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskChoose(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void init(Mask mask) {
        if(mask.maskGraphList.size() <= 4) {
            masksFrame.setColumnCount(2);
            masksFrame.setRowCount(2);
        } else if(mask.maskGraphList.size() <= 9) {
            masksFrame.setColumnCount(3);
            masksFrame.setRowCount(3);
        } else if(mask.maskGraphList.size() <= 16) {
            masksFrame.setColumnCount(4);
            masksFrame.setRowCount(4);
        }

        // add mask to content view
        for(MaskGraph mg: mask.maskGraphList) {
            int height = UICommon.dp2px((Activity)getContext(), 48);
            int width =  UICommon.dp2px((Activity)getContext(), 48);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(height, width);
            MaskIcon imageButton = new MaskIcon(getContext());
            imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageButton.setMaskGraph(mg);
            masksFrame.addView(imageButton, layoutParams);
            Glide.with(getContext()).load(mg.graphURL).into(imageButton);
            imageButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("MaskChoose","click");
                    MaskChooseEvent event = new MaskChooseEvent("Click", stageLine, ((MaskIcon)v).getMaskGraph());
                    EventBus.getDefault().post(event);
                }
            });
        }
    }

    public StageLine getStageLine() {
        return stageLine;
    }

    public void setStageLine(StageLine stageLine) {
        this.stageLine = stageLine;
    }
}
