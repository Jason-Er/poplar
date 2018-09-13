package com.wecyberstage.wecyberstage.view.main;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.util.helper.UICommon;

public class MaskGridLayout extends AutoGridLayout {

    public MaskGridLayout(Context context) {
        super(context);
        setColumnWidth(UICommon.dp2px( getContext(), 48));
        // setColumnCount(3);
        // setRowCount(3);
    }

    public MaskGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private StageRole stageRole;

    public void setStageRole(StageRole stageRole) {
        this.stageRole = stageRole;
        removeAllViews();
        for(MaskGraph maskGraph : stageRole.mask.maskGraphList) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(maskGraph.graphURL).into(imageView);
            addView(imageView);
            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.width = UICommon.dp2px( getContext(), 48);
            layoutParams.height = UICommon.dp2px( getContext(), 48);
        }
    }
}
