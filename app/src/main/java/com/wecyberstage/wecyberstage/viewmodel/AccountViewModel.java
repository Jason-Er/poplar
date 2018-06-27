package com.wecyberstage.wecyberstage.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.wecyberstage.wecyberstage.data.dto.UserRequest;
import com.wecyberstage.wecyberstage.model.User;
import com.wecyberstage.wecyberstage.util.helper.Resource;
import com.wecyberstage.wecyberstage.util.helper.SignInLiveData;
import com.wecyberstage.wecyberstage.util.helper.UserLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountViewModel extends ViewModel {

    public final LiveData<Resource<User>> userLiveData;
    public final LiveData<Resource<Boolean>> signInLiveData;

    @Inject
    public AccountViewModel(UserLiveData userLiveData, SignInLiveData signInLiveData) {
        this.userLiveData = userLiveData;
        this.signInLiveData = signInLiveData;
    }

    public void signIn(UserRequest userRequest) {
        ((SignInLiveData)signInLiveData).signIn(userRequest);
    }

    public void createUser(User user) {

    }

    public void retrieveUser(long userId) {

    }

    public void updateUser(User user) {

    }

    public void deleteUser(long userId) {

    }
}
