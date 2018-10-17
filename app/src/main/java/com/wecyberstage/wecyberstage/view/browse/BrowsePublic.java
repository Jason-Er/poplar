package com.wecyberstage.wecyberstage.view.browse;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.view.helper.BaseView;
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.helper.ViewType;

public class BrowsePublic extends BaseView {

    public BrowsePublic(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType, ToolViewsDelegate toolViewsDelegate) {
        super(activity, container, viewType, toolViewsDelegate);
    }

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.view_recycler, container, false);
    }

    @Override
    public void onStop(AppCompatActivity activity, @Nullable ViewGroup container) {

    }
}