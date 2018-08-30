package com.wecyberstage.wecyberstage.view.main;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MaskChooseBehavior extends CoordinatorLayout.Behavior {
    public MaskChooseBehavior() {
    }

    public MaskChooseBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof LineEditBar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d("MaskChooseBehavior","onDependentViewChanged x:" + dependency.getX() + " y: " + dependency.getY());
        Log.d("MaskChooseBehavior","onDependentViewChanged parent.getMeasuredHeight(): " + parent.getMeasuredHeight()
                + " parent.getHeight(): " + parent.getHeight());
        /*
        child.getLayoutParams().height = (int) (parent.getHeight() - dependency.getY() - dependency.getHeight());
        child.setY(dependency.getY() - dependency.getHeight());
        */
        return true;
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        Log.d("MaskChooseBehavior","onDependentViewRemoved");
        super.onDependentViewRemoved(parent, child, dependency);
    }

}
