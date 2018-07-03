package com.wecyberstage.wecyberstage.view.helper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.wecyberstage.wecyberstage.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupChooseAvatar extends PopupWindow{

    private View anchorView;
    public PopupChooseAvatar(View view) {
        super(view.getContext());
        anchorView = view;
        View contentView = LayoutInflater.from(view.getContext()).inflate(R.layout.popup_avatar_choose,null);
        ButterKnife.bind(this, contentView);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.i("AvatarLineAdapter","dismissed");
                anchorView = null;
            }
        });
        if(Build.VERSION.SDK_INT>=21){
            setElevation(5.0f);
        }
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @OnClick(R.id.ib_close)
    public void cancel(View view) {
        dismiss();
    }

    public void show() {
        showAsDropDown(anchorView);
    }
}
