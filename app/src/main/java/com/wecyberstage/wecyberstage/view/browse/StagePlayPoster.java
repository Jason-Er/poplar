package com.wecyberstage.wecyberstage.view.browse;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.StagePlayInfo;
import com.wecyberstage.wecyberstage.view.main.MainActivity;
import com.wecyberstage.wecyberstage.view.main.StagePlayCursor;
import com.wecyberstage.wecyberstage.view.message.StagePlayPosterEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mike on 2018/3/7.
 */

public class StagePlayPoster extends CardView {

    private final String TAG = "StagePlayPoster";
    private StagePlayInfo stagePlayInfo;

    @BindView(R.id.stagePlay_poster)
    ImageView poster;
    @BindView(R.id.stagePlay_title)
    TextView title;
    @BindView(R.id.stagePlay_brief)
    TextView brief;

    public StagePlayPoster(Context context) {
        super(context);
    }

    public StagePlayPoster(final Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                StagePlayInfo playInfo = ((StagePlayPoster) v).getStagePlayInfo();
                StagePlayPosterEvent event = new StagePlayPosterEvent(playInfo, "onClick");
                EventBus.getDefault().post(event);
            }
        });
    }

    public StagePlayPoster(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public StagePlayInfo getStagePlayInfo() {
        return stagePlayInfo;
    }

    public void setStagePlayInfo(StagePlayInfo stagePlayInfo) {
        this.stagePlayInfo = stagePlayInfo;
        title.setText(stagePlayInfo.name);
        brief.setText(stagePlayInfo.briefIntro);
        Glide.with(getContext()).load(stagePlayInfo.posterURL).into(poster);
    }
}
