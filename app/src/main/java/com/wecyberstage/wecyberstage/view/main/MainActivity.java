package com.wecyberstage.wecyberstage.view.main;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
import com.wecyberstage.wecyberstage.model.StageLine;
import com.wecyberstage.wecyberstage.model.StagePlay;
import com.wecyberstage.wecyberstage.view.helper.BaseView;
import com.wecyberstage.wecyberstage.view.helper.ClickActionInterface;
import com.wecyberstage.wecyberstage.view.message.FooterEditBarEvent;
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
import com.wecyberstage.wecyberstage.view.browse.BrowsePrivate;
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
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.helper.ViewTypeHelper;
import com.wecyberstage.wecyberstage.view.message.StageLineContainerViewEvent;
import com.wecyberstage.wecyberstage.view.message.StageLineViewEvent;
import com.wecyberstage.wecyberstage.view.message.StagePlayPosterEvent;
import com.wecyberstage.wecyberstage.viewmodel.StagePlayViewModel;

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

public class MainActivity extends AppCompatActivity
        implements HasSupportFragmentInjector,
        NavigationView.OnNavigationItemSelectedListener,
        CustomViewSlideInterface, KeyboardHeightObserver {

    private final String TAG = "MainActivity";
    private final String NAVIGATION_SEMICOLON = ";";
    private final String NAVIGATION_COLON = ":";
    private final String STAGEPLAY_CURSOR = "stage_play_cursor";

    private KeyboardHeightProvider keyboardHeightProvider;
    private Handler mainHideHandler = new Handler();

    @Inject
    CharacterFactory characterFactory;
    private Character4Play character;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_main)
    ViewGroup appMain;
    // region tool components views
    @BindView(R.id.appBar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.player_control_bar)
    PlayerControlBar playerControlBar;
    @BindView(R.id.footer_edit_bar)
    FooterEditBar footerEditBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    // endregion

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    LocalSettings localSettings;
    private StagePlayViewModel viewModel;

    // region navigation
    private Stack<String> navigationStack;
    private static final String NAVIGATION_INFO_KEY = "navigation_info";
    private BaseView currentView;
    private BehaviorResponseInterface currentBehaviorResponse;

    private SparseArray viewArray;
    private SparseArray flingResponseArray;
    private StagePlayCursor stagePlayCursor; // for tracing stage play
    BrowsePrivate browsePrivate;
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

        character = characterFactory.getCharacter(CharacterFactory.USER_TYPE.UN_REGISTERED);

        stagePlayCursor = getIntent().getParcelableExtra(STAGEPLAY_CURSOR);
        if( stagePlayCursor == null ) {
            stagePlayCursor = localSettings.getStagePlayCursor(character.getId());
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StagePlayViewModel.class);
        viewModel.stagePlay.observe(this, new Observer<StagePlay>() {
            @Override
            public void onChanged(@Nullable StagePlay stagePlay) {
                Log.d(TAG, "current stagePlay");
            }
        });
        viewModel.setStagePlayCursor(stagePlayCursor);

        // region all views
        viewArray = new SparseArray();
        flingResponseArray = new SparseArray();
        baseViewList = new ArrayList<>();
        lifeCycleComponents = new ArrayList<>();

        ToolViewsDelegate delegate = new BrowseToolViewsDelegate(this, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
        browsePrivate = new BrowsePrivate(this, appMain, ViewType.BROWSE, delegate);
        delegate = new ComposeXToolViewsDelegate(this, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
        composeX = new ComposeX(this, appMain, ViewType.COMPOSE_X, delegate);
        delegate = new ComposeYToolViewsDelegate(this, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
        composeY = new ComposeY(this, appMain, ViewType.COMPOSE_Y, delegate);
        delegate = new ComposeZToolViewsDelegate(this, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
        composeZ = new ComposeZ(this, appMain, ViewType.COMPOSE_Z, delegate);
        delegate = new SignInToolViewsDelegate(this, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
        signIn = new SignIn(this, appMain, ViewType.SIGN_IN, delegate);
        delegate = new SignUpToolViewsDelegate(this, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
        signUp = new SignUp(this, appMain, ViewType.SIGN_UP, delegate);
        delegate = new UserProfileToolViewsDelegate(this, appBarLayout, playerControlBar, footerEditBar, drawerLayout, fab);
        userProfile = new UserProfile(this, appMain, ViewType.USER_PROFILE, delegate);

        addCustomView(browsePrivate, new BehaviorResponseBrowse(this), appMain, viewArray, flingResponseArray);
        addCustomView(composeX, new BehaviorResponseComposeX(this), appMain, viewArray, flingResponseArray);
        addCustomView(composeY, new BehaviorResponseComposeY(this), appMain, viewArray, flingResponseArray);
        addCustomView(composeZ, new BehaviorResponseComposeZ(this), appMain, viewArray, flingResponseArray);
        addCustomView(signIn, new BehaviorResponseSignIn(this), appMain, viewArray, flingResponseArray);
        addCustomView(signUp, new BehaviorResponseSignUp(this), appMain, viewArray, flingResponseArray);
        addCustomView(userProfile, new BehaviorResponseUserProfile(this), appMain, viewArray, flingResponseArray);

        constructLifeCycleComponents();
        navigationStack = new Stack<>();
        // endregion

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                switch (menuItemId) {
                    case R.id.menu_item_account:
                        Log.i("onMenuItemClick","menu_item_account");
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

        // change maskChoose height
        if( localSettings.getSoftKeyboardHeight() > 0 ) {
            footerEditBar.setSoftKeyBoardHeight(localSettings.getSoftKeyboardHeight());
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
        if( playerControlBar != null && playerControlBar instanceof RegisterBusEventInterface ) {
            lifeCycleComponents.add((RegisterBusEventInterface) playerControlBar);
        }
        if( appBarLayout != null && appBarLayout instanceof RegisterBusEventInterface ) {
            lifeCycleComponents.add((RegisterBusEventInterface) appBarLayout);
        }
        if( footerEditBar != null && footerEditBar instanceof RegisterBusEventInterface ) {
            lifeCycleComponents.add(footerEditBar);
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

        getIntent().putExtra(STAGEPLAY_CURSOR, stagePlayCursor);
        localSettings.saveStagePlayCursor(character.getId(), stagePlayCursor);
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
    public void onResponseMessageEvent(FooterEditBarEvent event) {
        Log.d(TAG,"receive footerEditMain");
        switch (event.getMessage()) {
            case "addStageLine":
                // adapter.addStageLine((StageLine) event.getData());
                viewModel.addStageLine((StageLine) event.getData());
                break;
            case "deleteStageSceneContent":
                // adapter.deleteStageSceneContent();
                viewModel.deleteStageLine((StageLine) event.getData());
                break;
            case "deleteStageScene":
                // adapter.deleteStageScene();
                viewModel.deleteStageScene();
                break;
            case "addStageScene":
                // adapter.addStageScene();
                viewModel.addStageScene();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseMessageEvent(StageLineViewEvent event) {
        switch ( event.getMessage() ) {
            case "onLongPress":
                Log.d("MainActivity",event.getMessage());
                if(event.getData() != null) {
                    fab.hide();
                    playerControlBar.setVisibility(View.VISIBLE);
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
    public void onResponseMessageEvent(StageLineContainerViewEvent event) {
        switch ( event.getMessage() ) {
            case "onLongPress":
                Log.d(TAG, event.getMessage());

                break;
            case "onSingleTapUp":
                if( currentView instanceof ClickActionInterface ) {
                    ((ClickActionInterface) currentView).containerClick();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseMessageEvent(StagePlayPosterEvent event) {
        switch (event.getMessage()) {
            case "onClick":
                slideTo(ViewType.COMPOSE_Z, Direction.TO_UP);
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
                viewModel.stop();
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).stop();
                    }
                }
                break;
            case "PLAY":
                viewModel.play();
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).play();
                    }
                }
                break;
            case "PAUSE":
                viewModel.pause();
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).pause();
                    }
                }
                break;
            case "SEEK":
                viewModel.seek(event.getSeekProcess());
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).seek(event.getSeekProcess());
                    }
                }
                break;
            case "PRE":
                viewModel.pre();
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).pre();
                    }
                }
                break;
            case "NEXT":
                viewModel.next();
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).next();
                    }
                }
                break;
            case "VOLUME":
                viewModel.volume(true);
                for(BaseView baseView : baseViewList) {
                    if(baseView instanceof PlayControlInterface) {
                        ((PlayControlInterface) baseView).volume(true);
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
                footerEditBar.setSoftKeyBoardHeight(height);
            }
            footerEditBar.showSoftKeyBoard();
        } else {
            footerEditBar.hideSoftKeyBoard();
        }
    }
    // endregion

}
