package com.gr232.restaurantadmin.activities;

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
import com.gr232.restaurantadmin.recyclerviews.employee.EmployeeAdapter;
import com.gr232.restaurantadmin.R;
import com.gr232.restaurantadmin.models.Chef;
import com.gr232.restaurantadmin.models.Employee;
import com.gr232.restaurantadmin.models.Waiter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmployeesListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Employee> mEmployeeList;

    private Button mAddEmployeeBtn;

    private TextView mNotFoundTxtView;

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

    /**
     * Parcurge tabela "Employees" din baza de date si adauga in lista angajatii gasiti
     */
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

                            if (mEmployeeList.size() == 0) {
                                notFoundVisibility();
                            }
                        }
                    }
                });
    }

    private void notFoundVisibility() {
        mNotFoundTxtView.setVisibility(View.VISIBLE);

        mRecyclerView.setVisibility(View.GONE);
    }

    /**
     * Se seteaza campurile de date ale obiectului de tip angajat, in functie de datele retinute
     * in 'document', adica in baza de date.
     * @param document un rand din tabela employees
     * @param employee un obiect de tip angajat
     * @return angajatul dupa modificarile facute
     */
    private Employee setEmployeeData(QueryDocumentSnapshot document, Employee employee) {
        employee.setUserId(document.getString("userId"));
        employee.setFirstName(document.getString("firstName"));
        employee.setLastName(document.getString("lastName"));
        employee.setSalary(document.getDouble("salary"));
        employee.setHireDate((Calendar) document.get("hireDate"));

        return employee;
    }

    private void setLayoutManager() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter() {
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(mEmployeeList, this);
        mRecyclerView.setAdapter(employeeAdapter);
    }

    /**
     * Se initializeaza view-urile si se adauga niste date hardcodate in lista de employees
     */
    private void initViews() {
        mNotFoundTxtView = findViewById(R.id.notFoundTxtEmpList);

        mRecyclerView = findViewById(R.id.recyclerViewEmpList);
        mAddEmployeeBtn = findViewById(R.id.addEmployeeBtnEmpList);

        mEmployeeList = new ArrayList<>();
        mEmployeeList.add(new Waiter("ADSAD", "John", "Doe", "",
                200.5, null));
        mEmployeeList.add(new Chef("ADSAD", "Ion", "Popescu", "",
                200.5, null));
        mEmployeeList.add(new Waiter("ADSAD", "George", "Georgescu", "",
                200.5, null));
    }

    private void startAnimation() {
        ConstraintLayout constraintLayout = findViewById(R.id.constrLayoutEmpList);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();
    }
}
