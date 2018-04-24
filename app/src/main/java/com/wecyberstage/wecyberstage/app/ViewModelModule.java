package com.wecyberstage.wecyberstage.app;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.wecyberstage.wecyberstage.util.label.ViewModelKey;
import com.wecyberstage.wecyberstage.viewmodel.ComposeViewModel;
import com.wecyberstage.wecyberstage.viewmodel.ParticipateViewModel;
import com.wecyberstage.wecyberstage.viewmodel.ViewModelFactory;
import com.wecyberstage.wecyberstage.viewmodel.BrowseViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by mike on 2018/3/10.
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BrowseViewModel.class)
    abstract ViewModel bindBrowseViewModel(BrowseViewModel browseViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ParticipateViewModel.class)
    abstract ViewModel bindParticipateViewModel(ParticipateViewModel participateViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ComposeViewModel.class)
    abstract ViewModel bindComposeViewModel(ComposeViewModel composeViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}

