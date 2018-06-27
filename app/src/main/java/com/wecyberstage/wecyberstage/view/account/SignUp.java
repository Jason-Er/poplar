package com.wecyberstage.wecyberstage.view.account;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.app.WeCyberStageApp;
import com.wecyberstage.wecyberstage.data.dto.UserRequest;
import com.wecyberstage.wecyberstage.model.User;
import com.wecyberstage.wecyberstage.util.helper.Resource;
import com.wecyberstage.wecyberstage.view.helper.CustomView;
import com.wecyberstage.wecyberstage.view.helper.CustomViewBehavior;
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

public class SignUp extends CustomView {

    private AccountViewModel viewModel;
    private AppCompatActivity appCompatActivity;

    @BindView(R.id.signUp_component)
    View signUpComponent;
    @BindView(R.id.signUp_phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.signUp_password)
    EditText password;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public SignUp(AppCompatActivity activity, @Nullable ViewGroup container, ViewType viewType) {
        super(activity, container, viewType);
        this.appCompatActivity = activity;
    }

    @Override
    public void onCreate(AppCompatActivity activity, @Nullable ViewGroup container) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.frag_signup, container, false);
        ButterKnife.bind(this, view);
        CustomViewBehavior behavior = new CustomViewBehavior(activity, null);
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.setBehavior(behavior);

        ((WeCyberStageApp)activity.getApplication()).getAppComponent().inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(AccountViewModel.class);
        viewModel.userLiveData.observe(activity, new Observer<Resource<User>>(){
            @Override
            public void onChanged(@Nullable Resource<User> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        Timber.d("SUCCESS");

                        break;
                    case ERROR:

                        break;
                    case LOADING:

                        break;
                }
            }
        });
        signUpComponent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(phoneNumber.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                return false;
            }
        });
    }

    @OnClick(R.id.signUp_signUp)
    public void signIn(View view) {
        if(phoneNumber.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            throw new IllegalArgumentException();
        }
        viewModel.setRequestUser(new UserRequest(phoneNumber.getText().toString(), password.getText().toString()));
    }

    @OnClick(R.id.signUp_signIn)
    public void signUp(View view) {
        ((MainActivity) appCompatActivity).navigateToSignIn();
    }
}
