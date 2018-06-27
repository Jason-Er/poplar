package com.wecyberstage.wecyberstage.util.character;

import com.wecyberstage.wecyberstage.view.helper.Direction;
import com.wecyberstage.wecyberstage.view.helper.Navigate2Account;
import com.wecyberstage.wecyberstage.view.main.MainActivity;

public class UnRegistered extends Character4Play implements Navigate2Account{
    @Override
    public void navigate2Account(MainActivity activity) {
        activity.navigateToSignIn(Direction.TO_DOWN);
    }
}
