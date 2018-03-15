package com.wecyberstage.wecyberstage.view.main;

import android.support.v4.app.FragmentManager;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.view.browse.Browse;
import com.wecyberstage.wecyberstage.view.compose.Participate;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/15.
 */

public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;
    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.content_main;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToBrowse() {
        Browse searchFragment = new Browse();
        fragmentManager.beginTransaction()
                .replace(containerId, searchFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToParticipate(long playId) {
        String tag = "playId" + "/" + playId;
        Participate participate = Participate.create(playId);
        fragmentManager.beginTransaction()
                .replace(containerId, participate, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

}

