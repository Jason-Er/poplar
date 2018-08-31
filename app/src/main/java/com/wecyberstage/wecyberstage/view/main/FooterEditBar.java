package com.wecyberstage.wecyberstage.view.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wecyberstage.wecyberstage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FooterEditBar extends LinearLayout {

    public FooterEditBar(Context context) {
        super(context);
    }

    public FooterEditBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterEditBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

}
