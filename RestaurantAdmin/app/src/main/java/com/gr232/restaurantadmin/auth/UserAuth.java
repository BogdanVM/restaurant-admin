package com.gr232.restaurantadmin.auth;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gr232.restaurantadmin.models.InvalidCredentialsException;

public class UserAuth {
    private FirebaseAuth mAuth;
    private Context mContext;

    public UserAuth(Context context) {
        this.mAuth = FirebaseAuth.getInstance();
        this.mContext = context;
    }

    public FirebaseUser getLoggedUser() {
        return mAuth.getCurrentUser();
    }


    /**
     *
     * @param email = emailul utilizatorului
     * @param password = parola utilizatorului
     * @return = utilizatorul logat, daca logarea s-a efectuat cu succes
     * @throws InvalidCredentialsException = daca logarea nu s-a efectuat cu succes, atunci
     * este intoarsa aceasta exceptie;
     *
     */
    public FirebaseUser signInUser(String email, String password) throws InvalidCredentialsException {
        if (!email.isEmpty()) {
            if (!password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password);
            }
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            throw new InvalidCredentialsException();
        }

        return user;

    }
}
