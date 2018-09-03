package com.wecyberstage.wecyberstage.view.composeY;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.wecyberstage.wecyberstage.view.main.FooterEditMain;

public class ComposeYFABBehavior extends FloatingActionButton.Behavior {
    public ComposeYFABBehavior() {
    }

    public ComposeYFABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /*
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (child.getVisibility() == View.VISIBLE && dyConsumed > 0) {
            child.hide();
        } else if (child.getVisibility() == View.GONE && dyConsumed < 0) {
            child.show();
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
    */

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof FooterEditMain;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        // child.setSize(FloatingActionButton.SIZE_MINI);
        int offset = dependency.getTop() - child.getTop();
        ViewCompat.offsetTopAndBottom(child, offset);
        return true;
    }
}
