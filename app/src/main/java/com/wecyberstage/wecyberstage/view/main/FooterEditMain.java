package com.wecyberstage.wecyberstage.view.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StageRole;
import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.message.FooterEditMainEvent;
import com.wecyberstage.wecyberstage.view.message.StageLineCardViewEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FooterEditMain extends LinearLayout implements MaskGridLayoutCallBack, RegisterBusEventInterface {

    enum PANEL_VISIBLE {
        MASK_VISIBLE, FILE_VISIBLE, BOTH_VISIBLE, BOTH_GONE
    }

    @BindView(R.id.mask_choose_sub)
    MaskChooseTabLayout maskChoose;
    @BindView(R.id.file_choose_sub)
    ViewGroup fileChoose;
    @BindView(R.id.line_edit_sub)
    ViewGroup lineEdit;
    @BindView(R.id.lineEditSub_selected)
    ImageView selectedMask;
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

    private List<StageLine> stageLines;
    private StageLine stageLine;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        stageLine = new StageLine();
        maskChoose.setMaskGridLayoutCallBack(this);
        setPanelVisible(PANEL_VISIBLE.BOTH_GONE);
        editText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    stageLine.dialogue = s.toString();
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
                selectedMask.setVisibility(GONE);
                FooterEditMainEvent event = new FooterEditMainEvent(stageLine);
                EventBus.getDefault().post(event);
                break;
        }
    }

    @OnClick(R.id.fileChooseSub_word)
    public void setImageButtonWord(View view) {
        Log.d("FooterEditMain","setImageButtonWord click");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType(“application/msword;application/vnd.openxmlformats-officedocument.wordprocessingml.document”);//同时选择doc docx
        intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"); // docx
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            ((AppCompatActivity)getContext()).startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    ACTIVITY_REQUEST_CODE.FILE_SELECT_CODE.ordinal());
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getContext(), "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public void setStageRoles(List<StageRole> stageRoles) {
        maskChoose.setStageRoles(stageRoles);
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

    @Override
    public void selectedMask(StageRole stageRole, int maskOrdinal) {
        MaskGraph maskGraph = stageRole.mask.maskGraphList.get(maskOrdinal);
        Glide.with(getContext()).load(maskGraph.graphURL).into(selectedMask);
        selectedMask.setVisibility(VISIBLE);
        stageLine.setStageRole(stageRole);
        stageLine.maskOrdinal = maskOrdinal;
    }

    @Override
    public void register(Activity activity) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unRegister(Activity activity) {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseMessageEvent(StageLineCardViewEvent event) {
        switch (event.getMessage()) {
            case "onLongPress":
                Log.d("FooterEditMain","onLongPress");
                if(event.getData() != null) {
                    StageLine stageLine = (StageLine) event.getData();
                    selectedMask(stageLine.stageRole, stageLine.maskOrdinal);
                    editText.setText(stageLine.dialogue);
                }
                break;
        }
    }

}
