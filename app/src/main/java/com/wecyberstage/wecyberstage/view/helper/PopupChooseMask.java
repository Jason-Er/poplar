package com.wecyberstage.wecyberstage.view.helper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.Mask;
import com.wecyberstage.wecyberstage.model.MaskGraph;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupChooseMask extends PopupWindow{

    private View anchorView;
    private MaskGraph maskGraph;
    @BindView(R.id.mask_choose_frame)
    ViewGroup masksFrame;

    public PopupChooseMask(View view, MaskGraph maskGraph, Mask mask) {
        super(view.getContext());
        anchorView = view;
        this.maskGraph = maskGraph;
        View contentView = LayoutInflater.from(view.getContext()).inflate(R.layout.popup_mask_choose,null);
        ButterKnife.bind(this, contentView);
        if(mask.maskGraphList.size() <= 4) {
            ((GridLayout) masksFrame).setColumnCount(2);
            ((GridLayout) masksFrame).setRowCount(2);
        } else if(mask.maskGraphList.size() <= 9) {
            ((GridLayout) masksFrame).setColumnCount(3);
            ((GridLayout) masksFrame).setRowCount(3);
        } else if(mask.maskGraphList.size() <= 16) {
            ((GridLayout) masksFrame).setColumnCount(4);
            ((GridLayout) masksFrame).setRowCount(4);
        }
        // add mask to content view
        for(MaskGraph mg: mask.maskGraphList) {
            Log.i("PopupChooseMask","maskGraph ID: " + mg.id);
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, view.getContext().getResources().getDisplayMetrics());
            int width =  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, view.getContext().getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(height, width);
            ImageButton imageButton = new ImageButton(view.getContext());
            imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // imageButton.setId(R.id.image_view_1);
            masksFrame.addView(imageButton, layoutParams);
            Glide.with(view.getContext()).load(mg.graphURL).into(imageButton);
        }
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
