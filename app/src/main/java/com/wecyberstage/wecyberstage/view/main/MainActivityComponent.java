package com.wecyberstage.wecyberstage.view.main;

import com.wecyberstage.wecyberstage.view.helper.CustomView;

import dagger.Component;

@Component
public interface  MainActivityComponent {
    void inject(CustomView customView);
}
