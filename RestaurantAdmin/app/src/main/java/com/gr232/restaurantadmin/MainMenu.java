package com.gr232.restaurantadmin;

import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainMenu extends AppCompatActivity {

    FirebaseFirestore mFirestoreReference;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        startAnimation();

        mFirebaseUser = (FirebaseUser) getIntent().getExtras().get("user");
        mFirestoreReference = FirebaseFirestore.getInstance();

        String userType = getSignedUserType();
        if (userType.equalsIgnoreCase("admin")) {
            buildUIAdmin();
        } else if (userType.equalsIgnoreCase("waiter")) {
            buildUIWaiter();
        } else if (userType.equalsIgnoreCase("chef")) {
            buildUIChef();
        }
    }

    private void buildUIChef() {
        /* TODO Build UI for a chef type user */
    }

    private void buildUIWaiter() {
        /* TODO Build UI for a waiter type user */
    }

    private void buildUIAdmin() {
        /* TODO Build UI for an admin type user */
    }

    private String getSignedUserType() {
        final String[] userType = {""};
        mFirestoreReference.collection("Employees")
                .whereEqualTo("userId", mFirebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userType[0] = (String) document.get("type");
                            }
                        }
                    }
                });

        return userType[0];
    }

    private void startAnimation() {
        ConstraintLayout constraintLayout = findViewById(R.id.mainMenuLayout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();
    }
}
