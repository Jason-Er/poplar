package com.wecyberstage.wecyberstage.app;

import android.app.Application;

import com.wecyberstage.wecyberstage.view.account.UserProfile;
import com.wecyberstage.wecyberstage.view.account.SignIn;
import com.wecyberstage.wecyberstage.view.account.SignUp;
import com.wecyberstage.wecyberstage.view.browse.Browse;
import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeY.ComposeY;
import com.wecyberstage.wecyberstage.view.main.MainActivityModule;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by mike on 2018/3/10.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        MainActivityModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(WeCyberStageApp app);
    void inject(SignIn signIn);
    void inject(SignUp signUp);
    void inject(UserProfile profile);
    void inject(Browse browse);
    void inject(ComposeX composeX);
    void inject(ComposeY composeY);
    void inject(ComposeZ composeZ);

}