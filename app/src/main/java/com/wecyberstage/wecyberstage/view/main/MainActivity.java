package com.wecyberstage.wecyberstage.view.main;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
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
import com.wecyberstage.wecyberstage.data.file.LocalSettings;
import com.wecyberstage.wecyberstage.view.helper.BaseView;
import com.wecyberstage.wecyberstage.view.helper.ClickActionInterface;
import com.wecyberstage.wecyberstage.view.message.MainActivityEvent;
import com.wecyberstage.wecyberstage.view.message.PlayerControlEvent;
import com.wecyberstage.wecyberstage.util.character.CharacterFactory;
import com.wecyberstage.wecyberstage.util.character.Character4Play;
import com.wecyberstage.wecyberstage.view.account.SignIn;
import com.wecyberstage.wecyberstage.view.account.SignInToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.account.SignUp;
import com.wecyberstage.wecyberstage.view.account.SignUpToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.account.UserProfile;
import com.wecyberstage.wecyberstage.view.account.UserProfileToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.browse.Browse;
import com.wecyberstage.wecyberstage.view.browse.BrowseToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeX.ComposeXToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.composeY.ComposeY;
import com.wecyberstage.wecyberstage.view.composeY.ComposeYToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.helper.FlingViewSlideHelper;
import com.wecyberstage.wecyberstage.view.helper.Direction;
import com.wecyberstage.wecyberstage.view.helper.BehaviorResponseBrowse;
import com.wecyberstage.wecyberstage.view.helper.BehaviorResponseComposeX;
import com.wecyberstage.wecyberstage.view.helper.BehaviorResponseComposeY;
import com.wecyberstage.wecyberstage.view.helper.BehaviorResponseComposeZ;
import com.wecyberstage.wecyberstage.view.helper.BehaviorResponseInterface;
import com.wecyberstage.wecyberstage.view.helper.BehaviorResponseSignIn;
import com.wecyberstage.wecyberstage.view.helper.BehaviorResponseSignUp;
import com.wecyberstage.wecyberstage.view.helper.BehaviorResponseUserProfile;
import com.wecyberstage.wecyberstage.view.message.MessageEvent;
import com.wecyberstage.wecyberstage.view.helper.KeyboardHeightObserver;
import com.wecyberstage.wecyberstage.view.helper.KeyboardHeightProvider;
import com.wecyberstage.wecyberstage.view.helper.RegisterBusEventInterface;
import com.wecyberstage.wecyberstage.view.helper.Navigate2Account;
import com.wecyberstage.wecyberstage.view.helper.CustomViewSlideInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayControlInterface;
import com.wecyberstage.wecyberstage.view.helper.PlayStateInterface;
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.helper.ViewTypeHelper;
import com.wecyberstage.wecyberstage.view.message.StageLineCardViewEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        NavigationView.OnNavigationItemSelectedListener,
        CustomViewSlideInterface, KeyboardHeightObserver {

    private final String TAG = "MainActivity";
    private final String NAVIGATION_SEMICOLON = ";";
    private final String NAVIGATION_COLON = ":";

    private KeyboardHeightProvider keyboardHeightProvider;
    private Handler mainHideHandler = new Handler();

    @Inject
    CharacterFactory characterFactory;
    private Character4Play character;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header_main)
    View header;
    @BindView(R.id.player_control)
    View playerControl;
    @BindView(R.id.line_edit_sub)
    View lineEditBar;
    @BindView(R.id.app_main)
    ViewGroup appMain;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.footer_edit_main)
    FooterEditMain footerEditMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    LocalSettings localSettings;

    // region navigation
    private Stack<String> navigationStack;
    private static final String NAVIGATION_INFO_KEY = "navigation_info";
    private BaseView currentView;
    private BehaviorResponseInterface currentBehaviorResponse;

    private SparseArray viewArray;
    private SparseArray flingResponseArray;
    // private int softKeyBroadHeight;
    Browse browse;
    ComposeX composeX;
    ComposeY composeY;
    ComposeZ composeZ;
    SignIn signIn;
    SignUp signUp;
    UserProfile userProfile;
    List<BaseView> baseViewList;
    List<RegisterBusEventInterface> lifeCycleComponents;

    // endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Debug.startMethodTracing("/data/data/com.wecyberstage.wecyberstage/love_world_");
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        keyboardHeightProvider = new KeyboardHeightProvider(this);

        // UICommon.toImmersive(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // region all views
        viewArray = new SparseArray();
        flingResponseArray = new SparseArray();
        baseViewList = new ArrayList<>();
        lifeCycleComponents = new ArrayList<>();

        ToolViewsDelegate delegate = new BrowseToolViewsDelegate(this, toolbar, playerControl, lineEditBar, this.drawerLayout, fab);
        browse = new Browse(this, appMain, ViewType.BROWSE, delegate);
        delegate = new ComposeXToolViewsDelegate(this, toolbar, playerControl, lineEditBar, this.drawerLayout, fab);
        composeX = new ComposeX(this, appMain, ViewType.COMPOSE_X, delegate);
        delegate = new ComposeYToolViewsDelegate(this, toolbar, playerControl, lineEditBar, this.drawerLayout, fab);
        composeY = new ComposeY(this, appMain, ViewType.COMPOSE_Y, delegate);
        delegate = new ComposeZToolViewsDelegate(this, toolbar, playerControl, lineEditBar, this.drawerLayout, fab);
        composeZ = new ComposeZ(this, appMain, ViewType.COMPOSE_Z, delegate);
        delegate = new SignInToolViewsDelegate(this, toolbar, playerControl, lineEditBar, this.drawerLayout, fab);
        signIn = new SignIn(this, appMain, ViewType.SIGN_IN, delegate);
        delegate = new SignUpToolViewsDelegate(this, toolbar, playerControl, lineEditBar, this.drawerLayout, fab);
        signUp = new SignUp(this, appMain, ViewType.SIGN_UP, delegate);
        delegate = new UserProfileToolViewsDelegate(this, toolbar, playerControl, lineEditBar, this.drawerLayout, fab);
        userProfile = new UserProfile(this, appMain, ViewType.USER_PROFILE, delegate);

        addCustomView(browse, new BehaviorResponseBrowse(this), appMain, viewArray, flingResponseArray);
        addCustomView(composeX, new BehaviorResponseComposeX(this), appMain, viewArray, flingResponseArray);
        addCustomView(composeY, new BehaviorResponseComposeY(this), appMain, viewArray, flingResponseArray);
        addCustomView(composeZ, new BehaviorResponseComposeZ(this), appMain, viewArray, flingResponseArray);
        addCustomView(signIn, new BehaviorResponseSignIn(this), appMain, viewArray, flingResponseArray);
        addCustomView(signUp, new BehaviorResponseSignUp(this), appMain, viewArray, flingResponseArray);
        addCustomView(userProfile, new BehaviorResponseUserProfile(this), appMain, viewArray, flingResponseArray);

        constructLifeCycleComponents();
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
                        // slideTo(currentView, signIn, Direction.TO_DOWN);
                        if( character instanceof Navigate2Account) {
                            ((Navigate2Account) character).navigate2Account(MainActivity.this);
                        }
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

        character = characterFactory.getCharacter(CharacterFactory.USER_TYPE.UN_REGISTERED);

        // change maskChoose height
        if( localSettings.getSoftKeyboardHeight() > 0 ) {
            footerEditMain.setSoftKeyBoardHeight(localSettings.getSoftKeyboardHeight());
        }

        appMain.post(new Runnable() {
            @Override
            public void run() {
                keyboardHeightProvider.start();
            }
        });

    }

    public void setCharacter(Character4Play character) {
        this.character = character;
    }

    private void addCustomView(BaseView baseView, BehaviorResponseInterface behaviorResponseInterface, ViewGroup viewGroup, SparseArray viewArray, SparseArray flingResponseArray) {
        baseViewList.add(baseView);
        viewArray.put(baseView.getViewType().ordinal(), baseView);
        flingResponseArray.put(baseView.getViewType().ordinal(), behaviorResponseInterface);
        viewGroup.addView(baseView.getView(), 1);
        baseView.getView().setVisibility(View.INVISIBLE);
    }

    private void constructLifeCycleComponents() {
        if( playerControl != null && playerControl instanceof RegisterBusEventInterface ) {
            lifeCycleComponents.add((RegisterBusEventInterface) playerControl);
        }
        if( header != null && header instanceof RegisterBusEventInterface ) {
            lifeCycleComponents.add((RegisterBusEventInterface) header);
        }
        if( footerEditMain!= null && footerEditMain instanceof RegisterBusEventInterface ) {
            lifeCycleComponents.add(footerEditMain);
        }
        for(BaseView baseView : baseViewList) {
            if(baseView instanceof RegisterBusEventInterface) {
                lifeCycleComponents.add((RegisterBusEventInterface) baseView);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if(navigationStack.size() > 0) {
                String item = navigationStack.pop();
                slideTo(ViewType.valueOf(item.split(NAVIGATION_COLON)[1]), ViewType.valueOf(item.split(NAVIGATION_COLON)[0]), oppositeDirection(Direction.valueOf(item.split(NAVIGATION_COLON)[2])), false);
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

    @Override
    protected void onStop() {
        super.onStop();
        for(BaseView baseView : baseViewList) {
            baseView.onStop(this, appMain);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        for(RegisterBusEventInterface lifeCycle: lifeCycleComponents) {
            lifeCycle.register(this);
        }
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        for(RegisterBusEventInterface lifeCycle: lifeCycleComponents) {
            lifeCycle.unRegister(this);
        }
        keyboardHeightProvider.setKeyboardHeightObserver(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
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

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseMessageEvent(StageLineCardViewEvent event) {
        switch ( event.getMessage() ) {
            case "onLongPress":
                Log.d("MainActivity",event.getMessage());
                if(event.getData() != null) {
                    fab.hide();
                    lineEditBar.setVisibility(View.VISIBLE);
                }
                break;
            case "onSingleTapUp":
                if( currentView instanceof ClickActionInterface ) {
                    ((ClickActionInterface) currentView).itemClick();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseMessageEvent(MessageEvent event) {
        Log.i(TAG, event.getMessage());
        // queueLock.lock();
        switch (event.getMessage()) {
            case "TO_LEFT":
                currentBehaviorResponse.toLeft();
                break;
            case "TO_RIGHT":
                currentBehaviorResponse.toRight();
                break;
            case "TO_UP":
                currentBehaviorResponse.toUp();
                break;
            case "TO_DOWN":
                currentBehaviorResponse.toDown();
                break;
            case "CLICK":
                currentBehaviorResponse.click();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseMessageEvent(PlayerControlEvent event) {
        Log.i("Main", "onResponsePlayerControlEvent: " + event.getMessage());
        switch (event.getMessage()) {
            case "STOP":
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).stop();
                    }
                }
                break;
            case "PRE":
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).pre();
                    }
                }
                break;
            case "PLAY":
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).play();
                    }
                }
                break;
            case "PAUSE":
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).pause();
                    }
                }
                break;
            case "NEXT":
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).next();
                    }
                }
                break;
            case "VOLUME":
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).volume(true);
                    }
                }
                break;
            case "SEEK":
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).seek(event.getSeekProcess());
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode != Activity.RESULT_OK ) {
            Log.d("MainActivity", "onActivityResult() error, resultCode: " + resultCode);
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if ( requestCode == 0 ) {
            Uri uri = data.getData();
            MainActivityEvent.FileEvent fileEvent = new MainActivityEvent.FileEvent(uri, getContentResolver(), mainHideHandler);
            MainActivityEvent event = new MainActivityEvent(fileEvent, "File Selected");
            EventBus.getDefault().postSticky(event);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
    public void enlargeContentView(boolean isEnlarge) {
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) appMain.getLayoutParams();
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
    public BaseView getCustomView(ViewType viewType) {
        return (BaseView) viewArray.get(viewType.ordinal());
    }

    private void restoreToView(ViewType viewType) {
        BaseView baseView = getCustomView(viewType);
        baseView.becomeVisible();
        baseView.slideBegin();
        currentBehaviorResponse = (BehaviorResponseInterface) flingResponseArray.get(viewType.ordinal());
        this.currentView = baseView;
        baseView.slideEnd();
    }

    private void slideTo(BaseView from, BaseView to, Direction direction) {
        View currentView = from.getView();
        to.becomeVisible();
        View followView = to.getView();
        to.slideBegin();

        switch (direction) {
            case TO_UP:
                followView.setTranslationX(0);
                followView.setTranslationY(followView.getHeight());
                FlingViewSlideHelper.SlideVertical(currentView, followView, -1, to);
                break;
            case TO_RIGHT:
                followView.setTranslationX(-followView.getWidth());
                followView.setTranslationY(0);
                FlingViewSlideHelper.SlideHorizontal(currentView, followView, 1, to);
                break;
            case TO_DOWN:
                followView.setTranslationX(0);
                followView.setTranslationY(-Math.max(followView.getHeight(),currentView.getHeight()));
                FlingViewSlideHelper.SlideVertical(currentView, followView, 1, to);
                break;
            case TO_LEFT:
                followView.setTranslationX(followView.getWidth());
                followView.setTranslationY(0);
                FlingViewSlideHelper.SlideHorizontal(currentView, followView, -1, to);
                break;
        }
        this.currentView = to;
    }

    private Direction oppositeDirection(Direction direction) {
        Direction directionOpposite = Direction.TO_LEFT;
        switch (direction){
            case TO_UP:
                directionOpposite = Direction.TO_DOWN;
                break;
            case TO_RIGHT:
                directionOpposite = Direction.TO_LEFT;
                break;
            case TO_DOWN:
                directionOpposite = Direction.TO_UP;
                break;
            case TO_LEFT:
                directionOpposite = Direction.TO_RIGHT;
                break;
        }
        return directionOpposite;
    }

    @Override
    public void slideUp() {
        while ( navigationStack.size() > 0 ) {
            String track = navigationStack.pop();
            if(ViewTypeHelper.isTwoTypesSameLevel(currentView.getViewType(), ViewType.valueOf(track.split(NAVIGATION_COLON)[0]))) {
                Log.i("isTwoTypesSameLevel"," track: "+ track +" currentViewType: " + currentView.getViewType());
                continue;
            } else {
                slideTo(currentView.getViewType(), ViewType.valueOf(track.split(NAVIGATION_COLON)[0]), oppositeDirection(Direction.valueOf(track.split(NAVIGATION_COLON)[2])), false);
                break;
            }
        }
    }

    @Override
    public void slideTo(ViewType to, Direction direction) {
        slideTo(currentView.getViewType(), to, direction, true);
    }

    @Override
    public void slideTo(ViewType to, Direction direction, boolean saveTrack) {
        slideTo(currentView.getViewType(), to, direction, saveTrack);
    }

    @Override
    public void slideTo(ViewType from, ViewType to, Direction direction) {
        slideTo(from, to, direction, true);
    }

    @Override
    public void slideTo(ViewType from, ViewType to, Direction direction, boolean saveTrack) {
        BaseView fromBaseView = getCustomView(from);
        BaseView toBaseView = getCustomView(to);
        if(fromBaseView instanceof PlayStateInterface && toBaseView instanceof PlayStateInterface) {
            ((PlayStateInterface) toBaseView).setPlayState(((PlayStateInterface) fromBaseView).getPlayState());
        }
        slideTo(fromBaseView, toBaseView, direction);
        currentBehaviorResponse = (BehaviorResponseInterface) flingResponseArray.get(to.ordinal());
        if(saveTrack) {
            navigationStack.push(from.name() + NAVIGATION_COLON + to.name() + NAVIGATION_COLON + direction.name());
        }
    }

    // region surrounding components tools
    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        String or = orientation == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape";
        Log.i("mainActivity", "onKeyboardHeightChanged in pixels: " + height + " " + or);
        if( height > 0 ) {
            if ( localSettings.getSoftKeyboardHeight() == 0 ) {
                localSettings.saveSoftKeyboardHeight(height);
                footerEditMain.setSoftKeyBoardHeight(height);
            }
            footerEditMain.showSoftKeyBoard();
        } else {
            footerEditMain.hideSoftKeyBoard();
        }
    }
    // endregion

}
