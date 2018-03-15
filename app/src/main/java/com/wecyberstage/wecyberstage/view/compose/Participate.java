package com.wecyberstage.wecyberstage.view.compose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;

/**
 * Created by mike on 2018/3/5.
 */

public class Participate extends Fragment {

    private static final String ID_KEY = "id";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_participate, container,false);
        return view;
    }

    public static Participate create(long playId) {
        Participate fragment = new Participate();
        Bundle args = new Bundle();
        args.putLong(ID_KEY, playId);
        fragment.setArguments(args);
        return fragment;
    }
}
