package com.wecyberstage.wecyberstage.view.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.util.helper.UICommon;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FooterEditMain extends LinearLayout {

    private boolean isShowMaskChoose = false;

    @BindView(R.id.mask_choose_sub)
    ViewGroup maskChoose;
    @BindView(R.id.file_choose_sub)
    ViewGroup fileChoose;
    @BindView(R.id.lineEditSub_mask)
    ImageButton imageButtonMask;

    public FooterEditMain(Context context) {
        super(context);
    }

    public FooterEditMain(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterEditMain(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        maskChoose.setVisibility(GONE);
        fileChoose.setVisibility(GONE);
    }

    // region for mask image button switch state
    private void switchToMaskState(ImageButton imageButton) {
        imageButton.setImageResource(R.drawable.emoticon_happy);
        imageButton.setTag("mask");
    }
    private void switchToKeyBoardState(ImageButton imageButton) {
        imageButton.setImageResource(R.drawable.keyboard_variant);
        imageButton.setTag("keyboard");
    }
    // endregion

    @OnClick(R.id.lineEditSub_mask)
    public void onLineEditMaskClick(View view) {
        switch ((String)view.getTag()) {
            case "mask":
                UICommon.hideSoftKeyboard(view);
                switchToKeyBoardState((ImageButton)view);
                break;
            case "keyboard":
                UICommon.showSoftKeyboard(view);
                switchToMaskState((ImageButton)view);
                break;
        }
    }

    @OnClick(R.id.lineEdit_ok)
    public void onLineEditOKClick(View view) {
        Log.d("FooterEditMain","onLineEditMaskClick");
        maskChoose.setVisibility(View.GONE);
        UICommon.hideSoftKeyboard(view);
    }

    public void showSoftKeyBoard() {
        maskChoose.setVisibility(VISIBLE);
        fileChoose.setVisibility(GONE);
        switchToMaskState(imageButtonMask);
    }

    public void hideSoftKeyBoard() {
        if( imageButtonMask.getTag().equals("mask")) {
            maskChoose.setVisibility(GONE);
            fileChoose.setVisibility(GONE);
        }
    }

    public void setSoftKeyBoardHeight(int height) {
        ViewGroup.LayoutParams params = maskChoose.getLayoutParams();
        params.height = height;
        params = fileChoose.getLayoutParams();
        params.height = height;
    }
}
