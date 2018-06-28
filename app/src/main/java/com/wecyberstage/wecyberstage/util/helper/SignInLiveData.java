package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

import com.wecyberstage.wecyberstage.data.dto.UserRequest;
import com.wecyberstage.wecyberstage.data.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SignInLiveData extends LiveData<Resource<Boolean>> {
    UserRepository userRepository;

    @Inject
    public SignInLiveData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signIn(UserRequest userRequest) {
        // TODO: 2018/6/26 below is dummy data
        boolean status = false;
        if( userRequest.phoneNumber.equals("123") && userRequest.password.equals("123")) {
            status = true;
        }
        Resource<Boolean> a = Resource.success(status);
        setValue(a);
    }
}
