package com.gr232.restaurantadmin;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr232.restaurantadmin.models.Employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmployeesListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Employee> mEmployeeList;

    private Button mAddEmployeeBtn;

    private TextView mNotFoundTxtView;
    private TextView mTitleTxtView;
    private TextView mBorderTitleTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);

        startAnimation();
        initViews();
        setLayoutManager();
        initData();
        setAdapter();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        mAddEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeesListActivity.this,
                        NewEmployeeActivity.class));
            }
        });
    }

    private void initData() {
        FirebaseFirestore firestoreReference = FirebaseFirestore.getInstance();
        firestoreReference.collection("Employees")
                .whereGreaterThan("type", "admin")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Employee employee = new Employee();
                                employee = setEmployeeData(document, employee);

                                mEmployeeList.add(employee);
                            }
                        } else {
                            notFoundVisibility();
                        }
                    }
                });
    }

    private void notFoundVisibility() {
        mNotFoundTxtView.setVisibility(View.VISIBLE);

        mAddEmployeeBtn.setVisibility(View.GONE);
        mBorderTitleTxtView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mTitleTxtView.setVisibility(View.GONE);
    }

    private Employee setEmployeeData(QueryDocumentSnapshot document, Employee employee) {
        employee.setUserId(document.getString("userId"));
        employee.setFirstName(document.getString("firstName"));
        employee.setLastName(document.getString("lastName"));
        employee.setPhoto(document.getString("photo"));
        employee.setSalary(document.getDouble("salary"));
        employee.setHireDate((Calendar) document.get("hireDate"));

        return employee;
    }

    private void setLayoutManager() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter() {
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(mEmployeeList);
        mRecyclerView.setAdapter(employeeAdapter);
    }

    private void initViews() {
        mTitleTxtView = findViewById(R.id.titleTxtViewEmpList);
        mBorderTitleTxtView = findViewById(R.id.bottomBorderEmpList);
        mNotFoundTxtView = findViewById(R.id.notFoundTxtEmpList);

        mRecyclerView = findViewById(R.id.recyclerViewEmpList);
        mAddEmployeeBtn = findViewById(R.id.addEmployeeBtnEmpList);

        mEmployeeList = new ArrayList<>();
    }

    private void startAnimation() {
        ConstraintLayout constraintLayout = findViewById(R.id.constrLayoutEmpList);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();
    }
}
