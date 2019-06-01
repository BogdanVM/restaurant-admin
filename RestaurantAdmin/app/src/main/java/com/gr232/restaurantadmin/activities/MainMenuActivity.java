package com.gr232.restaurantadmin.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr232.restaurantadmin.R;

/**
 * Clasa in care va fi afisat meniul principal.
 *
 * TODO Implementeaza metodele "buildUIChef()", "buildUIWaiter()", care vor face aceasta clasa dinamica
 */
public class MainMenuActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestoreReference;
    private FirebaseUser mFirebaseUser;

    private Button mSeeEmployeesBtn;
    private Button mSeeLayoutBtn;
    private Button mSeeMenuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        startAnimation();
        initViews();
        initLayout();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        mSeeEmployeesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, EmployeesListActivity.class));
            }
        });

        mSeeLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, HallActivity.class));
            }
        });

        mSeeMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, MenuActivity.class));
            }
        });
    }

    private void initLayout() {
        String userType = getSignedUserType();
        if (userType.equalsIgnoreCase("admin")) {
            buildUIAdmin();
        } else if (userType.equalsIgnoreCase("waiter")) {
            buildUIWaiter();
        } else if (userType.equalsIgnoreCase("chef")) {
            buildUIChef();
        }
    }

    private void initViews() {
        mFirebaseUser = (FirebaseUser) getIntent().getExtras().get("user");
        mFirestoreReference = FirebaseFirestore.getInstance();

        mSeeEmployeesBtn = findViewById(R.id.seeEmployeeBtnMainMenu);
        mSeeLayoutBtn = findViewById(R.id.manageLayoutBtn);
        mSeeMenuBtn = findViewById(R.id.seeMenuBtnMainMenu);
    }

    private void buildUIChef() {
        /* TODO Build UI for a chef type user */
    }

    private void buildUIWaiter() {
        /* TODO Build UI for a waiter type user */
    }

    private void buildUIAdmin() {
        /* UI pentru admin este deja construit */
    }

    /**
     * Se cauta in Firebase utilizatorul cu id-ul lui mFirebaseUser.
     * Odata gasit, se intoarce tipul acestuia.
     * Pentru a putea salva tipul utilizatorului, s-a folosit un array final: "final String[]",
     * fiindca doar se astfel se pot retine date dintr-o clasa anonima (OnCompleteListener).
     *
     * @return un string reprezentand tipul utilizatorului conectat: "admin", "waiter", "chef".
     */
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
