package com.gr232.restaurantadmin;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private Button mSignUpBtn;
    private Button mBackBtn;

    private EditText mUserNameEditTxt, mPwdEditTxt;
    private EditText mMailEditTxt, mPhoneEditTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        startAnimation();
        initViews();
        setButtonClickEvents();
    }

    private void setButtonClickEvents() {
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        mSignUpBtn = findViewById(R.id.signupBtnSignUp);
        mBackBtn = findViewById(R.id.backBtnSignUp);

        mUserNameEditTxt = findViewById(R.id.usernameEditTxtSignUp);
        mPwdEditTxt = findViewById(R.id.pwdEditTxtSignUp);
        mMailEditTxt = findViewById(R.id.mailEditTxtSignUp);
        mPhoneEditTxt = findViewById(R.id.phoneNumberEditTextSignUp);
    }

    private void startAnimation() {
        ConstraintLayout constraintLayout = findViewById(R.id.signupActivityLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();
    }
}
