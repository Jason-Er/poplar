package com.wecyberstage.wecyberstage.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.view.browse.Browse;
import com.wecyberstage.wecyberstage.view.compose.Compose;
import com.wecyberstage.wecyberstage.view.helper.CustomViewSlideControl;
import com.wecyberstage.wecyberstage.view.helper.MessageEvent;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.participate.Participate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener {

    private Handler autoHideHandler = new Handler();
    private Runnable autoHideRunnable=new Runnable() {
        @Override
        public void run() {
            queueLock.lock();
            if(findViewById(R.id.header_main).getVisibility() == View.VISIBLE) {
                moveOutHeaderAndFooter(findViewById(R.id.header_main), findViewById(R.id.footer_main));
            }
        }
    };
    private final Lock queueLock=new ReentrantLock();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header_main)
    View header;
    @BindView(R.id.footer_main)
    View footer;
    @BindView(R.id.app_main)
    ViewGroup appMain;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    UICommon uiCommon;

    CustomViewSlideControl customViewSlideControl;

    Browse browse;
    Participate participate;
    Compose compose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        uiCommon.toImmersive();
        EventBus.getDefault().register(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // region all views
        browse = new Browse();
        participate = new Participate();
        compose = new Compose();

        customViewSlideControl = new CustomViewSlideControl(this, appMain);
        customViewSlideControl.addView(CustomViewSlideControl.ViewType.BROWSE, browse, 1);
        customViewSlideControl.addView(CustomViewSlideControl.ViewType.PARTICIPANT, participate, 1);
        customViewSlideControl.addView(CustomViewSlideControl.ViewType.COMPOSE, compose, 1);

        // endregion

        if (savedInstanceState == null) {
            navigateToBrowse();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        customViewSlideControl.intViewsPosition();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_account) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        autoHideHandler.postDelayed(autoHideRunnable, 3000);
    }
    */
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseEventBus(MessageEvent messageEvent) {
        Log.i("Main onResponseEventBus", messageEvent.getMessage());
        queueLock.lock();
        switch (messageEvent.getMessage()) {
            case "TO_LEFT":
                customViewSlideControl.flingResponse(CustomViewSlideControl.Direction.TO_LEFT);
                break;
            case "TO_RIGHT":
                customViewSlideControl.flingResponse(CustomViewSlideControl.Direction.TO_RIGHT);
                break;
            case "TO_UP":
                customViewSlideControl.flingResponse(CustomViewSlideControl.Direction.TO_UP);
                break;
            case "TO_DOWN":
                customViewSlideControl.flingResponse(CustomViewSlideControl.Direction.TO_DOWN);
                break;
            case "CLICK":
                if(header.getVisibility() == View.VISIBLE) {
                    moveOutHeaderAndFooter(header, footer);
                } else {
                    moveInHeaderAndFooter(header, footer);
                }
                break;
        }
    }

    public void moveOutHeaderAndFooter(final View header, final View footer) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(header, "alpha", 0.0f),
                ObjectAnimator.ofFloat(header, "translationY", -header.getHeight()),
                ObjectAnimator.ofFloat(footer, "alpha", 0.0f),
                ObjectAnimator.ofFloat(footer, "translationY", footer.getHeight())
        );
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                header.setVisibility(View.INVISIBLE);
                footer.setVisibility(View.INVISIBLE);
                queueLock.unlock();
            }
        });
        set.setDuration(300).start();
    }

    public void moveInHeaderAndFooter(final View header, final View footer) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(header, "alpha", 1.0f),
                ObjectAnimator.ofFloat(header, "translationY", 0),
                ObjectAnimator.ofFloat(footer, "alpha", 1.0f),
                ObjectAnimator.ofFloat(footer, "translationY", 0)
        );
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                header.setVisibility(View.VISIBLE);
                footer.setVisibility(View.VISIBLE);
                queueLock.unlock();
                autoHideHandler.postDelayed(autoHideRunnable, 3000);
            }
        });
        set.setDuration(300).start();
    }

    //region navigation methods
    public void navigateToBrowse() {
        getSupportActionBar().show();

    }

    public void navigateToParticipate(long playId) {
        getSupportActionBar().hide();
        participate.setPlayState(new PlayState(playId, 1L, 0L));
        customViewSlideControl.navigateToView(CustomViewSlideControl.ViewType.PARTICIPANT);
    }
    // endregion

    /*
    public void enlargeContentView(boolean isEnlarge) {
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) viewPager.getLayoutParams();
        if(isEnlarge) {
            params.setBehavior(null);
        } else {
            AppBarLayout.ScrollingViewBehavior behavior = new AppBarLayout.ScrollingViewBehavior();
            params.setBehavior(behavior);
        }

    }
    */

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }
}
