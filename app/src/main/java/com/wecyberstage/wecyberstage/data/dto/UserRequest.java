package com.wecyberstage.wecyberstage.data.dto;

public class UserRequest {
    public String phoneNumber;
    public String password;

    public UserRequest(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
