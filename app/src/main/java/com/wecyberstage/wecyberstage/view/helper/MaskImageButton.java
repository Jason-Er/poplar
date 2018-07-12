package com.wecyberstage.wecyberstage.view.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.PopupWindow;

import com.wecyberstage.wecyberstage.model.MaskGraph;

public class MaskImageButton extends android.support.v7.widget.AppCompatImageButton {
    private PopupWindow popupWindow;
    private MaskImageChoose maskImageChoose;
    private MaskGraph maskGraph;

    public MaskImageButton(Context context, MaskImageChoose maskImageChoose, PopupWindow popupWindow) {
        super(context);
        this.maskImageChoose = maskImageChoose;
        this.popupWindow = popupWindow;
    }

    public MaskImageButton(Context context) {
        super(context);
    }

    public MaskImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaskGraph getMaskGraph() {
        return maskGraph;
    }

    public void setMaskGraph(MaskGraph maskGraph) {
        this.maskGraph = maskGraph;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        maskImageChoose.setMaskGraph(maskGraph);
        popupWindow.dismiss();
        return false;
    }
}
