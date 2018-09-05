package com.wecyberstage.wecyberstage.view.account;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.data.dto.UserRequest;
import com.wecyberstage.wecyberstage.util.character.CharacterFactory;
import com.wecyberstage.wecyberstage.util.helper.Resource;
import com.wecyberstage.wecyberstage.util.helper.UICommon;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.Direction;
import com.wecyberstage.wecyberstage.view.helper.SlideInterface;
import com.wecyberstage.wecyberstage.view.helper.ToolViewsDelegate;
import com.wecyberstage.wecyberstage.view.helper.ViewType;
import com.wecyberstage.wecyberstage.view.main.MainActivity;
import com.wecyberstage.wecyberstage.viewmodel.AccountViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by mike on 2018/3/5.
 */

public class SignIn extends CustomView implements SlideInterface {

    private AccountViewModel viewModel;
    @BindView(R.id.signIn_component)
    View signInComponent;
    @BindView(R.id.signIn_phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.signIn_password)
    EditText password;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    CharacterFactory characterFactory;

    public SignIn(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType, ToolViewsDelegate toolViewsDelegate) {
        super(activity, container, viewType, toolViewsDelegate);
    }

    @Override
    public void onCreate(final AppCompatActivity activity, @Nullable ViewGroup container) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.frag_signin, container, false);
        ButterKnife.bind(this, view);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(AccountViewModel.class);
        viewModel.signInLiveData.observe(activity, new Observer<Resource<Boolean>>(){
            @Override
            public void onChanged(@Nullable Resource<Boolean> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        Timber.d("SUCCESS");
                        if(resource.data) {
                            Log.i("SignIn","Sign in success");
                            ((MainActivity) activity).setCharacter(characterFactory.getCharacter(CharacterFactory.USER_TYPE.REGISTERED));
                            hideSoftKeyBoard();
                            ((MainActivity) activity).slideUp();
                        }
                        break;
                    case ERROR:

                        break;
                    case LOADING:

                        break;
                }
            }
        });
        signInComponent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyBoard();
                return false;
            }
        });
    }

    @Override
    public void onStop(AppCompatActivity activity, @Nullable ViewGroup container) {

    }


    private void hideSoftKeyBoard() {
        UICommon.hideSoftKeyboard(phoneNumber);
        UICommon.hideSoftKeyboard(password);
    }

    @OnClick(R.id.signIn_showPwd)
    public void showPassword(View view) {
        if(view.getTag() == null || !(Boolean) view.getTag()) {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ((ImageButton)view).setImageResource(R.drawable.eye_outline);
            view.setTag(true);
        } else {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ((ImageButton)view).setImageResource(R.drawable.eye_off_outline);
            view.setTag(false);
        }
    }

    @OnClick(R.id.signIn_signIn)
    public void signIn(View view) {
        if(phoneNumber.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            throw new IllegalArgumentException();
        }
        viewModel.signIn(new UserRequest(phoneNumber.getText().toString(), password.getText().toString()));
    }

    @OnClick(R.id.signIn_signUp)
    public void signUp(View view) {
        ((MainActivity) activity).slideTo(ViewType.SIGN_UP, Direction.TO_LEFT);
    }

    @OnClick(R.id.signIn_navigateUp)
    public void navigateUp(View view) {
        ((MainActivity) activity).slideUp();
    }

    @Override
    public void slideEnd() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
