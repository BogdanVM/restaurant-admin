package com.example.dana_maria.testapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;

public class OspatarTask extends AppCompatActivity {
    private ImageView avatar;
    private Button saveEmployeeBtn;

    private EditText mailEditTxt;
    private EditText nameEditTxt;
    private EditText phoneEditTxt;
    private EditText cnpEditTxt;

    private Spinner jobsSpinner;
    private Spinner genderSpinner;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_ospatar_task);
        identifyControls();

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Waiters");

        saveEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmployee();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();

                    Picasso.get()
                            .load(selectedImage)
                            .transform(new PicassoCircleTransformation(this, selectedImage))
                            .resize(1080, 720)
                            .centerInside()
                            .into(avatar);

                    Toast.makeText(this, "Poza adaugata cu succes", Toast.LENGTH_LONG).show();
                }
        }

    };

    private void identifyControls() {
        avatar = (ImageView) findViewById(R.id.chef_ImgView);
        saveEmployeeBtn = (Button) findViewById(R.id.saveEmployeeBtn);

        nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        mailEditTxt = (EditText) findViewById(R.id.mailEditTxt);
        phoneEditTxt = (EditText) findViewById(R.id.phoneEditTxt);
        cnpEditTxt = (EditText) findViewById(R.id.cnpEditTxt);

        jobsSpinner = (Spinner) findViewById(R.id.jobsSpinner);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
    }

    private void saveEmployee() {
        String job = jobsSpinner.getSelectedItem().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString().trim();

        String name = nameEditTxt.getText().toString().trim();
        String mail = mailEditTxt.getText().toString().trim();
        String phoneNo = phoneEditTxt.getText().toString().trim();
        String cnp = cnpEditTxt.getText().toString().trim();

        switch(job) {
            case "Ospatar": {
                Waiter newWaiter = new Waiter(name, gender, phoneNo, mail, cnp);
                String id = databaseReference.push().getKey();

                databaseReference.child(id).setValue(newWaiter);
                break;
            }
            case "Bucatar": {
                break;
            }
        }
    }
}
