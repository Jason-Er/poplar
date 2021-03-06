package com.wecyberstage.wecyberstage.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
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
import com.wecyberstage.wecyberstage.view.account.SignIn;
import com.wecyberstage.wecyberstage.view.browse.Browse;
import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeY.ComposeY;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.CustomViewSlideHelper;
import com.wecyberstage.wecyberstage.view.helper.Direction;
import com.wecyberstage.wecyberstage.view.helper.FlingResponseBrowse;
import com.wecyberstage.wecyberstage.view.helper.FlingResponseComposeX;
import com.wecyberstage.wecyberstage.view.helper.FlingResponseComposeY;
import com.wecyberstage.wecyberstage.view.helper.FlingResponseComposeZ;
import com.wecyberstage.wecyberstage.view.helper.FlingResponseInterface;
import com.wecyberstage.wecyberstage.view.helper.FlingResponseSignIn;
import com.wecyberstage.wecyberstage.view.helper.MessageEvent;
import com.wecyberstage.wecyberstage.view.helper.PlayState;
import com.wecyberstage.wecyberstage.view.helper.CustomViewSlideInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;
import com.wecyberstage.wecyberstage.view.helper.ViewType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener, CustomViewSlideInterface {

    private final String NAVIGATION_SEMICOLON = ";";
    private final String NAVIGATION_COLON = ":";

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

    // region navigation
    private Stack<String> navigationStack;
    private static final String NAVIGATION_INFO_KEY = "navigation_info";
    private CustomView currentView;
    private FlingResponseInterface currentFlingResponse;

    private SparseArray viewArray;
    private SparseArray flingResponseArray;
    Browse browse;
    ComposeX composeX;
    ComposeY composeY;
    ComposeZ composeZ;
    SignIn signIn;

    // endregion
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
        browse = new Browse(this, appMain, ViewType.BROWSE);
        composeX = new ComposeX(this, appMain, ViewType.COMPOSE_X);
        composeY = new ComposeY(this, appMain, ViewType.COMPOSE_Y);
        composeZ = new ComposeZ(this, appMain, ViewType.COMPOSE_Z);
        signIn = new SignIn(this, appMain, ViewType.SIGN_IN);
        viewArray = new SparseArray();
        flingResponseArray = new SparseArray();
        addCustomView(browse, new FlingResponseBrowse(this), appMain, viewArray, flingResponseArray);
        addCustomView(composeX, new FlingResponseComposeX(this), appMain, viewArray, flingResponseArray);
        addCustomView(composeY, new FlingResponseComposeY(this), appMain, viewArray, flingResponseArray);
        addCustomView(composeZ, new FlingResponseComposeZ(this), appMain, viewArray, flingResponseArray);
        addCustomView(signIn, new FlingResponseSignIn(this), appMain, viewArray, flingResponseArray);
        navigationStack = new Stack<>();
        // endregion

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                switch (menuItemId) {
                    case R.id.menu_item_account:
                        Log.i("onMenuItemClick","menu_item_account");
                        slideView(currentView, signIn, Direction.TO_DOWN);
                        break;
                }
                return false;
            }
        });

        if (savedInstanceState == null) {
            restoreToView(ViewType.BROWSE);
        } else {
            String navigationState = savedInstanceState.getString(NAVIGATION_INFO_KEY);
            List<String> stringList = Arrays.asList(navigationState.split(NAVIGATION_SEMICOLON));
            navigationStack = new Stack<>();
            for(String str: stringList) {
                Log.i("onCreate","stack will push: "+str);
                navigationStack.push(str);
            }
            String item = navigationStack.peek();
            restoreToView(ViewType.valueOf(item.split(NAVIGATION_COLON)[1]));
        }
    }

    private void addCustomView(CustomView customView, FlingResponseInterface flingResponseInterface, ViewGroup viewGroup, SparseArray viewArray, SparseArray flingResponseArray) {
        viewArray.put(customView.getViewType().ordinal(), customView);
        flingResponseArray.put(customView.getViewType().ordinal(), flingResponseInterface);
        viewGroup.addView(customView.getView(), 1);
        customView.getView().setVisibility(View.INVISIBLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(navigationStack.size() > 0) {
                String item = navigationStack.pop();
                slideView(ViewType.valueOf(item.split(NAVIGATION_COLON)[1]), ViewType.valueOf(item.split(NAVIGATION_COLON)[0]), oppositeDirection(Direction.valueOf(item.split(NAVIGATION_COLON)[2])), false);
            } else {
                // TODO: 2018/6/13 double click quit
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds dataSet to the action bar if it is present.
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        List<String> stringList =  new ArrayList<>(navigationStack);
        String navigationState = "";
        for(String str: stringList) {
            navigationState += str + ";";
        }
        if(navigationState.length() > 1) {
            navigationState = navigationState.substring(0,navigationState.length() - 1);
        }
        outState.putString(NAVIGATION_INFO_KEY, navigationState);
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
                currentFlingResponse.toLeft();
                break;
            case "TO_RIGHT":
                currentFlingResponse.toRight();
                break;
            case "TO_UP":
                currentFlingResponse.toUp();
                break;
            case "TO_DOWN":
                currentFlingResponse.toDown();
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

    public void navigateToComposeZ() {
        getSupportActionBar().hide();
        slideView(ViewType.BROWSE, ViewType.COMPOSE_Z, Direction.TO_UP);
        currentFlingResponse = (FlingResponseInterface) flingResponseArray.get(ViewType.COMPOSE_Z.ordinal());
    }

    public void navigateToComposeX(long playId) {
        getSupportActionBar().hide();
        composeX.setPlayState(new PlayState(playId, 1L, 0L));
//        customViewSlideControl.navigateToView(ViewType.COMPOSE_X, Direction.TO_UP);
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

    @Override
    public CustomView getCustomView(ViewType viewType) {
        return (CustomView) viewArray.get(viewType.ordinal());
    }

    private void restoreToView(ViewType viewType) {
        CustomView customView = getCustomView(viewType);
        View view = customView.getView();
        view.setVisibility(View.VISIBLE);
        currentFlingResponse = (FlingResponseInterface) flingResponseArray.get(viewType.ordinal());
        this.currentView = customView;
    }

    @Override
    public void slideView(CustomView from, CustomView to, Direction direction) {
        // TODO: 2018/5/24 save navigation track
        View currentView = from.getView();
        View followView = to.getView();
        followView.setVisibility(View.VISIBLE);
        switch (direction) {
            case TO_UP:
                followView.setTranslationY(followView.getHeight());
                CustomViewSlideHelper.SlideVertical(currentView, followView, -1);
                break;
            case TO_RIGHT:
                Log.i("Slide", "To right +++++");
                followView.setTranslationX(-followView.getWidth());
                CustomViewSlideHelper.SlideHorizontal(currentView, followView, 1);
                break;
            case TO_DOWN:
                followView.setTranslationY(-followView.getHeight());
                CustomViewSlideHelper.SlideVertical(currentView, followView, 1);
                break;
            case TO_LEFT:
                Log.i("Slide", "To left +++++");
                followView.setTranslationX(followView.getWidth());
                CustomViewSlideHelper.SlideHorizontal(currentView, followView, -1);
                break;
        }
        this.currentView = to;
    }

    private Direction oppositeDirection(Direction direction) {
        Direction directionL = Direction.TO_LEFT;
        switch (direction){
            case TO_UP:
                directionL = Direction.TO_DOWN;
                break;
            case TO_RIGHT:
                directionL = Direction.TO_LEFT;
                break;
            case TO_DOWN:
                directionL = Direction.TO_UP;
                break;
            case TO_LEFT:
                directionL = Direction.TO_RIGHT;
                break;
        }
        return directionL;
    }

    @Override
    public void slideView(ViewType from, ViewType to, Direction direction) {
        slideView(from, to, direction, true);
    }

    public void slideView(ViewType from, ViewType to, Direction direction, boolean saveTrack) {
        CustomView fromCustomView = getCustomView(from);
        CustomView toCustomView = getCustomView(to);
        ((PlayStateInterface)toCustomView).setPlayState(((PlayStateInterface)fromCustomView).getPlayState());
        slideView(fromCustomView, toCustomView, direction);
        currentFlingResponse = (FlingResponseInterface) flingResponseArray.get(to.ordinal());
        if(saveTrack) {
            navigationStack.push(from.name() + NAVIGATION_COLON + to.name() + NAVIGATION_COLON + direction.name());
        }
    }
}
