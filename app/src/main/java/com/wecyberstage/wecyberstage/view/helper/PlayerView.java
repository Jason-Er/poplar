package com.wecyberstage.wecyberstage.view.helper;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class PlayerView extends BaseView
        implements PlayControlInterface, ClickActionInterface {

    public PlayerView(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType, ToolViewsDelegate toolViewsDelegate) {
        super(activity, container, viewType, toolViewsDelegate);
    }

    // region implementation of PlayControlInterface
    @Override
    public void play() {
        if( isVisible() ) {
            if(view instanceof PlayControlSub1Interface) {
                ((PlayControlSub1Interface) view).play();
            }
        }
    }

    @Override
    public void pause() {
        if( isVisible() ) {
            if(view instanceof PlayControlSub1Interface) {
                ((PlayControlSub1Interface) view).pause();
            }
        }
    }

    @Override
    public void stop() {
        if( isVisible() ) {
            if(view instanceof PlayControlSub1Interface) {
                ((PlayControlSub1Interface) view).stop();
            }
        }
    }

    @Override
    public void seek(float percent) {
        if( isVisible() ) {
            if(view instanceof PlayControlSub1Interface) {
                ((PlayControlSub1Interface) view).seek(percent);
            }
        }
    }

    @Override
    public void pre() {
        if( isVisible() ) {
            if(((RecyclerView)view).getAdapter() instanceof PlayControlSub2Interface) {
                ((PlayControlSub2Interface) ((RecyclerView)view).getAdapter()).pre();
            }
        }
    }

    @Override
    public void next() {
        if( isVisible() ) {
            if(((RecyclerView)view).getAdapter() instanceof PlayControlSub2Interface) {
                ((PlayControlSub2Interface) ((RecyclerView)view).getAdapter()).next();
            }
        }
    }

    @Override
    public void volume(boolean open) {
        if( isVisible() ) {
            if(((RecyclerView)view).getAdapter() instanceof PlayControlSub2Interface) {
                ((PlayControlSub2Interface) ((RecyclerView)view).getAdapter()).volume(open);
            }
        }
    }
    // endregion

    // region implementation of ClickActionInterface
    @Override
    public void itemClick() {
        if( toolViewsDelegate instanceof ClickActionInterface ) {
            ((ClickActionInterface) toolViewsDelegate).itemClick();
        }
    }

    @Override
    public void containerClick() {
        if( toolViewsDelegate instanceof ClickActionInterface ) {
            ((ClickActionInterface) toolViewsDelegate).containerClick();
        }
    }
    // endregion
}
