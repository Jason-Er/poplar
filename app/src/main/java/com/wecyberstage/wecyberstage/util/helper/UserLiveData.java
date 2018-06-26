package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

import com.wecyberstage.wecyberstage.data.dto.UserRequest;
import com.wecyberstage.wecyberstage.data.repository.UserRepository;
import com.wecyberstage.wecyberstage.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserLiveData extends LiveData<Resource<User>> {

    UserRepository userRepository;

    @Inject
    public UserLiveData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setRequestUser(UserRequest userRequest) {
        // TODO: 2018/6/26 below is dummy data
        User user = new User();
        Resource<User> a = Resource.success(user);
        setValue(a);
    }

}
