package com.wecyberstage.wecyberstage.view.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.util.helper.UICommon;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FooterEditMain extends LinearLayout {

    enum PANEL_VISIBLE {
        MASK_VISIBLE, FILE_VISIBLE, BOTH_VISIBLE, BOTH_GONE
    }

    @BindView(R.id.mask_choose_sub)
    ViewGroup maskChoose;
    @BindView(R.id.file_choose_sub)
    ViewGroup fileChoose;
    @BindView(R.id.line_edit_sub)
    ViewGroup lineEdit;
    @BindView(R.id.lineEditSub_mask)
    ImageButton imageButtonMask;
    @BindView(R.id.lineEditSub_ok)
    ImageButton imageButtonOK;
    @BindView(R.id.lineEditSub_line)
    EditText editText;

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
        setPanelVisible(PANEL_VISIBLE.BOTH_GONE);
        editText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    switchToCheckState(imageButtonOK);
                } else {
                    switchToPlusState(imageButtonOK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
    private void switchToCheckState(ImageButton imageButton) {
        imageButton.setImageResource(R.drawable.ic_check);
        imageButton.setTag("check");
    }
    private void switchToPlusState(ImageButton imageButton) {
        imageButton.setImageResource(R.drawable.ic_plus);
        imageButton.setTag("plus");
    }
    private void setPanelVisible(PANEL_VISIBLE visible) {
        switch (visible) {
            case BOTH_GONE:
                maskChoose.setVisibility(GONE);
                fileChoose.setVisibility(GONE);
                break;
            case MASK_VISIBLE:
                maskChoose.setVisibility(VISIBLE);
                fileChoose.setVisibility(GONE);
                break;
            case FILE_VISIBLE:
                maskChoose.setVisibility(GONE);
                fileChoose.setVisibility(VISIBLE);
                break;
            case BOTH_VISIBLE:
                maskChoose.setVisibility(VISIBLE);
                fileChoose.setVisibility(VISIBLE);
                break;
        }
    }
    // endregion

    @OnClick(R.id.lineEditSub_mask)
    public void onLineEditMaskClick(View view) {
        switch ((String)view.getTag()) {
            case "mask":
                setPanelVisible(PANEL_VISIBLE.MASK_VISIBLE);
                UICommon.hideSoftKeyboard(view);
                switchToKeyBoardState((ImageButton)view);
                break;
            case "keyboard":
                setPanelVisible(PANEL_VISIBLE.MASK_VISIBLE);
                UICommon.showSoftKeyboard(view);
                switchToMaskState((ImageButton)view);
                break;
        }
    }

    @OnClick(R.id.lineEditSub_ok)
    public void onLineEditOKClick(View view) {
        switch((String)view.getTag()) {
            case "plus":
                setPanelVisible(PANEL_VISIBLE.FILE_VISIBLE);
                UICommon.hideSoftKeyboard(view);
                break;
            case "check":
                // TODO: 9/3/2018 add text to line
                Log.d("FooterEditMain","onLineEditOKClick " + editText.getText());
                hide();
                editText.setText("");
                break;
        }
    }

    public void show() {

    }

    public void hide() {
        UICommon.hideSoftKeyboard(this);
        setPanelVisible(PANEL_VISIBLE.BOTH_GONE);
    }

    public void showSoftKeyBoard() {
        setPanelVisible(PANEL_VISIBLE.MASK_VISIBLE);
        switchToMaskState(imageButtonMask);
    }

    public void hideSoftKeyBoard() {
        /*
        if( imageButtonMask.getTag().equals("mask")) {
            setPanelVisible(PANEL_VISIBLE.BOTH_GONE);
        }
        */
    }

    public void setSoftKeyBoardHeight(int height) {
        ViewGroup.LayoutParams params = maskChoose.getLayoutParams();
        params.height = height;
        params = fileChoose.getLayoutParams();
        params.height = height;
    }
}
