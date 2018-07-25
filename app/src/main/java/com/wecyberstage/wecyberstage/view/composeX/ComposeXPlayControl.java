package com.wecyberstage.wecyberstage.view.composeX;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wecyberstage.wecyberstage.view.helper.PlayControlInterface;

public class ComposeXPlayControl implements PlayControlInterface {
    private RecyclerView recyclerView;
    private Handler timer;
    private long startTime;
    private long currentTime;

    public ComposeXPlayControl(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        timer = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        timer.removeMessages(0);
                        currentTime = SystemClock.uptimeMillis();
                        // TODO: 7/25/2018 scroll recyclerView
                        Log.i("ComposeXPlayControl","millisecond passed: " + (currentTime - startTime));

                        startTime = currentTime;
                        timer.sendEmptyMessageDelayed(0, 16);
                        break;
                    case 1:
                        timer.removeMessages(0);
                        break;
                }
            }
        };
    }

    @Override
    public void play() {
        startTime = SystemClock.uptimeMillis();
        timer.sendEmptyMessage(0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void pre() {

    }

    @Override
    public void next() {

    }

    @Override
    public void stop() {
        timer.sendEmptyMessage(1);
    }

    @Override
    public void volume(boolean open) {

    }
}
