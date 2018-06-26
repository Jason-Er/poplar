package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.wecyberstage.wecyberstage.data.dto.UserRequest;
import com.wecyberstage.wecyberstage.model.User;
import com.wecyberstage.wecyberstage.util.helper.Resource;
import com.wecyberstage.wecyberstage.util.helper.UserLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountViewModel extends ViewModel {

    public final LiveData<Resource<User>> userLiveData;

    @Inject
    public AccountViewModel(UserLiveData userLiveData) {
        this.userLiveData = userLiveData;
    }

    public void setRequestUser(UserRequest userRequest) {
        ((UserLiveData)userLiveData).setRequestUser(userRequest);
    }
}
