package com.wecyberstage.wecyberstage.view.main;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.message.PlayerControlEvent;
import com.wecyberstage.wecyberstage.view.helper.LifeCycle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerControlBar extends RelativeLayout implements LifeCycle {

    @BindView(R.id.footerMain_playerControl_play)
    ImageButton imageButtonPlay;

    @OnClick({R.id.footerMain_playerControl_stop,
            R.id.footerMain_playerControl_pre,
            R.id.footerMain_playerControl_play,
            R.id.footerMain_playerControl_next,
            R.id.footerMain_playerControl_volume})
    public void responseClick(ImageButton button) {
        PlayerControlEvent event = new PlayerControlEvent("NULL");
        switch (button.getId()) {
            case R.id.footerMain_playerControl_stop:
                imageButtonPlay.setTag(false);
                imageButtonPlay.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_play));
                event.setMessage("STOP");
                break;
            case R.id.footerMain_playerControl_pre:
                event.setMessage("PRE");
                break;
            case R.id.footerMain_playerControl_play:
                if( button.getTag() == null || !(boolean) button.getTag() ) {
                    button.setTag(true);
                    event.setMessage("PLAY");
                    button.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_pause));
                } else {
                    button.setTag(false);
                    event.setMessage("PAUSE");
                    button.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_play));
                }
                break;
            case R.id.footerMain_playerControl_next:
                event.setMessage("NEXT");
                break;
            case R.id.footerMain_playerControl_volume:
                event.setMessage("VOLUME");
                break;
        }
        EventBus.getDefault().post(event);
    }

    public PlayerControlBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void onResume(Activity activity) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause(Activity activity) {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseEvent(PlayerControlEvent event) {
        switch (event.getMessage()) {
            case "END":
                Log.d("PlayerControlBar","Play to End");
                break;
        }
    }
}
