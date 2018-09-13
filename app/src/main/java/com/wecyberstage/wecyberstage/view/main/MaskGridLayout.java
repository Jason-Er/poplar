package com.wecyberstage.wecyberstage.view.main;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.util.helper.UICommon;

public class MaskGridLayout extends AutoGridLayout {

    public MaskGridLayout(Context context, MaskGridLayoutCallBack callBack) {
        super(context);
        setColumnWidth(UICommon.dp2px( getContext(), 48));
        this.maskGridLayoutCallBack = callBack;
        // setColumnCount(3);
        // setRowCount(3);
    }

    public MaskGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private StageRole mStageRole;
    private MaskGridLayoutCallBack maskGridLayoutCallBack;

    public void setStageRole(StageRole stageRole) {
        mStageRole = stageRole;
        removeAllViews();
        for(MaskGraph maskGraph : stageRole.mask.maskGraphList) {
            Mask imageView = new Mask(getContext());
            imageView.setOrdinal(stageRole.mask.maskGraphList.indexOf(maskGraph));
            Glide.with(getContext()).load(maskGraph.graphURL).into(imageView);
            addView(imageView);
            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.width = UICommon.dp2px( getContext(), 48);
            layoutParams.height = UICommon.dp2px( getContext(), 48);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ordinal = ((Mask)v).getOrdinal();
                    if(maskGridLayoutCallBack != null) {
                        maskGridLayoutCallBack.selectedMask(mStageRole, ordinal);
                    }
                }
            });
        }
    }

    class Mask extends AppCompatImageView {

        public Mask(Context context) {
            super(context);
        }

        int ordinal;

        public void setOrdinal(int ordinal) {
            this.ordinal = ordinal;
        }

        public int getOrdinal() {
            return ordinal;
        }
    }
}
