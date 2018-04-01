package com.wecyberstage.wecyberstage.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mike on 2018/3/10.
 */

@Module(includes = ViewModelModule.class)
class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    SharedPreferences provideSharedPrefs(Application application) {
        return application.getSharedPreferences("demo-prefs", Context.MODE_PRIVATE);
    }

    /*
    @Provides
    @Singleton
    KeyFrameAdapter provideKeyFrameAdapter(AdapterDelegatesManager delegates) {
        return new KeyFrameAdapter(delegates);
    }
    */
}
