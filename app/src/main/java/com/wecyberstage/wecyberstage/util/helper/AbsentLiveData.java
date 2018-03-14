package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;

/**
 * Created by mike on 2018/3/13.
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }
    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
