package com.gr232.restaurantadmin.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gr232.restaurantadmin.R;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailEditTxt;
    private EditText mPwdEditTxt;
    private Button mLoginBtn;

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getSignedUser(user);
    }

    private void getSignedUser(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
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

                /* Autentificam utilizatorul utilizand API-ul Firebase */
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    getSignedUser(FirebaseAuth.getInstance().getCurrentUser());
                                } else {
                                    Toast.makeText(MainActivity.this, "A avut loc o eroare " +
                                            "la autentificare" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }


    private void initViews() {
        mLoginBtn = findViewById(R.id.loginBtn);

        mEmailEditTxt = findViewById(R.id.mailEditTxtLogIn);
        mPwdEditTxt = findViewById(R.id.pwdEditTxt);

    }

    private void startAnimation() {
        ConstraintLayout constraintLayout = findViewById(R.id.loginActivityLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();
    }
}
