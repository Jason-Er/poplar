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

public class LineEditBar extends LinearLayout {

    @BindView(R.id.lineEdit_line)
    EditText line;

    public LineEditBar(Context context) {
        super(context);
    }

    public LineEditBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineEditBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.lineEdit_mask)
    public void onLineEditMaskClick(View view) {
        Log.d("LineEditBar","onLineEditMaskClick");
        
    }
}
