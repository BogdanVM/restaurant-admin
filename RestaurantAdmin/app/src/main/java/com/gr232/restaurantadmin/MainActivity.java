package com.gr232.restaurantadmin;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mUserNameEditTxt;
    private EditText mPwdEditTxt;

    private Button mLoginBtn;
    private Button mSignUpBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAnimation();
        initViews();
        setButtonClickEvents();
    }

    private void setButtonClickEvents() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initViews() {
        mLoginBtn = findViewById(R.id.loginBtn);
        mSignUpBtn = findViewById(R.id.signupBtn);

        mUserNameEditTxt = findViewById(R.id.usernameEditTxt);
        mPwdEditTxt = findViewById(R.id.pwdEditTxt);
    }

    private void startAnimation() {
        ConstraintLayout mConstraintLayout = findViewById(R.id.loginActivityLayout);

        AnimationDrawable mAnimationDrawable = (AnimationDrawable) mConstraintLayout.getBackground();
        mAnimationDrawable.setEnterFadeDuration(2500);
        mAnimationDrawable.setExitFadeDuration(2500);
        mAnimationDrawable.start();
    }


}
