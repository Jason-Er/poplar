package com.wecyberstage.wecyberstage.view.main;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MaskChooseBehavior extends CoordinatorLayout.Behavior {
    public MaskChooseBehavior() {
    }

    public MaskChooseBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.d("MaskChooseBehavior","layoutDependsOn");
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d("MaskChooseBehavior","onDependentViewChanged");
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        Log.d("MaskChooseBehavior","onDependentViewRemoved");
        super.onDependentViewRemoved(parent, child, dependency);
    }
}
