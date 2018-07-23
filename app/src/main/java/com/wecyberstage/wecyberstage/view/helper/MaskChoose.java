package com.wecyberstage.wecyberstage.view.helper;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.util.helper.UICommon;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaskChoose extends RelativeLayout {

    @BindView(R.id.mask_choose_frame)
    GridLayout masksFrame;

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
            ImageButton imageButton = new ImageButton(getContext());
            imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // imageButton.setMaskGraph(mg);
            masksFrame.addView(imageButton, layoutParams);
            Glide.with(getContext()).load(mg.graphURL).into(imageButton);
        }
    }
}
