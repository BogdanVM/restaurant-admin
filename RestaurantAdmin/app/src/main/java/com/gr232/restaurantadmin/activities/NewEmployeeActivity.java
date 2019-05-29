package com.gr232.restaurantadmin.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gr232.restaurantadmin.R;
import com.gr232.restaurantadmin.models.Chef;
import com.gr232.restaurantadmin.models.Waiter;

public class NewEmployeeActivity extends AppCompatActivity {


    private EditText mFullNameEditTxt;
    private EditText mEmailEditTxt;
    private EditText mPwdEditTxt;

    private Spinner mJobSpinner;

    private Button mAddEmployeeBtn;
    private Button mBackBtn;

    private FirebaseFirestore mFirebaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_employee);

        initViews();
        initSpinner();
        initClickListeners();
    }

    private void initSpinner() {
        String[] jobs = {"Ospatar", "Bucatar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                jobs);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mJobSpinner.setAdapter(adapter);
    }

    /**
     * Se trateaza evenimentele de onclick ale celor doua butoane.
     * - In cazul selectarii butonului "Adauga angajat", se insereaza in baza de date noul angajat;
     * - In cazul selectarii butonului "Inapoi", se inchide activitatea curenta
     */
    private void initClickListeners() {
        mAddEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                String fullName = mFullNameEditTxt.getText().toString().trim();
                String email = mEmailEditTxt.getText().toString().trim();
                String job = mJobSpinner.getSelectedItem().toString().trim();
                String password = mPwdEditTxt.getText().toString().trim();

                /* Aflam numele si prenumele din 'fullName' */
                String[] splitName = fullName.split(" ");
                String lastName;
                String firstName;

                if (splitName.length == 2) {
                    firstName = splitName[0];
                    lastName = splitName[1];
                } else {
                    mFullNameEditTxt.setError("Numele nu a fost introdus corect. Numele si prenumele" +
                            " trebuie separate prin spatii");

                    return;
                }

                /* Prima data incercam sa inregistram utilizatorul */
                final String[] uid = {""};
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    uid[0] = FirebaseAuth.getInstance().getUid();
                                }
                            }
                        });

                /* Daca id-ul nu s-a modificat, atunci s-a produs o eroare si oprim executia metodei */
                if (uid[0].equals("")) {
                    Toast.makeText(NewEmployeeActivity.this, "A avut loc o eroare la inregistrare ",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                /* Delogam utilizatorul nou creat*/
                FirebaseAuth.getInstance().signOut();

                if (job.equalsIgnoreCase("Ospatar")) {

                    Waiter waiter = new Waiter(uid[0], firstName, lastName, null, null,
                            null);

                    mFirebaseReference.collection("Employees")
                            .add(waiter)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                    } else {
                                        Toast.makeText(NewEmployeeActivity.this,
                                                "A avut loc o eroare la inserarea datelor",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Chef chef = new Chef(uid[0], firstName, lastName, null, null,
                            null);

                    mFirebaseReference.collection("Employees")
                            .add(chef)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                    } else {
                                        Toast.makeText(NewEmployeeActivity.this,
                                                "A avut loc o eroare la inserarea datelor",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

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
        mFullNameEditTxt = findViewById(R.id.fullNameEditTxtAddEmp);
        mEmailEditTxt = findViewById(R.id.mailEditTxtAddEmp);
        mPwdEditTxt = findViewById(R.id.pwdEditTextAddEmp);

        mJobSpinner = findViewById(R.id.jobSpinnerAddEmp);

        mAddEmployeeBtn = findViewById(R.id.addEmployeeBtnAddEmp);
        mBackBtn = findViewById(R.id.backBtnAddEmp);

        mFirebaseReference = FirebaseFirestore.getInstance();
    }
}
