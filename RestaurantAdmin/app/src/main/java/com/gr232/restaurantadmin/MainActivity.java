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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gr232.restaurantadmin.auth.UserAuth;
import com.gr232.restaurantadmin.models.InvalidCredentialsException;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailEditTxt;
    private EditText mPwdEditTxt;
    private Button mLoginBtn;
    private UserAuth mUserAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAnimation();
        initViews();
        setButtonClickEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mUserAuth.getLoggedUser();
        getSignedUser(user);
    }

    private void getSignedUser(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, MainMenu.class);
            intent.putExtra("user", user);

            startActivity(intent);
            finish();
        }
    }

    private void setButtonClickEvents() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditTxt.getText().toString().trim();
                String password = mPwdEditTxt.getText().toString().trim();

                try {
                    FirebaseUser user = mUserAuth.signInUser(email, password);
                    getSignedUser(user);

                } catch (InvalidCredentialsException e) {
                    mEmailEditTxt.setText("");
                    mPwdEditTxt.setText("");

                    mPwdEditTxt.setError("Incorrect email or password");
                }
            }
        });
    }


    private void initViews() {
        mLoginBtn = findViewById(R.id.loginBtn);

        mEmailEditTxt = findViewById(R.id.mailEditTxtLogIn);
        mPwdEditTxt = findViewById(R.id.pwdEditTxt);

        mUserAuth = new UserAuth(getApplicationContext());
    }

    private void startAnimation() {
        ConstraintLayout constraintLayout = findViewById(R.id.loginActivityLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();
    }
}
