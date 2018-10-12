package com.wecyberstage.wecyberstage.view.main;

import com.wecyberstage.wecyberstage.view.helper.BaseView;

import dagger.Component;

@Component
public interface  MainActivityComponent {
    void inject(BaseView baseView);
}
