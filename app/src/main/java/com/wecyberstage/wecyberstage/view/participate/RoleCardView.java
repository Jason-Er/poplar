package com.wecyberstage.wecyberstage.view.participate;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.wecyberstage.wecyberstage.model.KeyFrame;

/**
 * Created by mike on 2018/3/17.
 */

public class RoleCardView extends CardView {

    public KeyFrame.RoleInfo roleInfo;

    public RoleCardView(Context context) {
        super(context);
    }

    public RoleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoleCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentHeight = ((View)getParent()).getHeight();
        super.onMeasure(
                MeasureSpec.makeMeasureSpec((int)(roleInfo.roleViewRect.width() * parentHeight), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec((int)(roleInfo.roleViewRect.height() * parentHeight), MeasureSpec.EXACTLY));
    }
}
