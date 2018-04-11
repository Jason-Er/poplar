package com.wecyberstage.wecyberstage.view.browse;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.wecyberstage.wecyberstage.model.PlayInfo;

/**
 * Created by mike on 2018/3/7.
 */

public class PlayProfileCardView extends CardView
        implements View.OnClickListener{

    public View.OnClickListener onClickCallBack = null;

    public PlayInfo playInfo;

    public PlayProfileCardView(Context context) {
        super(context);
    }

    public PlayProfileCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);
    }

    public PlayProfileCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {
        if (onClickCallBack != null)
            onClickCallBack.onClick(v);
    }
}
