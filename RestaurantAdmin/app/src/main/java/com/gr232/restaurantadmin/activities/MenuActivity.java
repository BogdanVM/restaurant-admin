package com.gr232.restaurantadmin.activities;

import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.gr232.restaurantadmin.R;
import com.gr232.restaurantadmin.models.Dish;
import com.gr232.restaurantadmin.models.Employee;
import com.gr232.restaurantadmin.recyclerviews.dish.DishAdapter;
import com.gr232.restaurantadmin.recyclerviews.employee.EmployeeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa activitatii in care va fi afisat meniul de preparate.
 */
public class MenuActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Dish> dishList;
    private Button mAddDishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startAnimation();
        initViews();
        setLayoutManager();
        initData();
        setAdapter();
        setOnClickListener();
    }

    private void setOnClickListener() {
        mAddDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Folosind referinta catre firebase sunt extrase preparatele din colectia ”Dishes”
     * si sunt apoi adaugate in lista ”dishList”.
     */
    private void initData() {
        FirebaseFirestore firestoreReference = FirebaseFirestore.getInstance();
        firestoreReference.collection("Dishes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Dish dish = new Dish(document.getString("name"),
                                        document.getString("ingredients"),
                                        Integer.parseInt(document.getString("weight")),
                                        document.getDouble("price"));

                                dishList.add(dish);
                            }
                        }
                    }
                });
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recyclerViewMenu);
        mAddDishBtn = findViewById(R.id.addDishBtnMenu);
        dishList = new ArrayList<>();

        dishList.add(new Dish("Preparat1", "ingredient1, ingredient2, ingredient3",
                200, 20.5));
        dishList.add(new Dish("Preparat1", "ingredient1, ingredient2, ingredient3",
                200, 20.5));
        dishList.add(new Dish("Preparat1", "ingredient1, ingredient2, ingredient3",
                200, 20.5));
    }

    private void startAnimation() {
        ConstraintLayout constraintLayout = findViewById(R.id.constrLayoutMenu);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();
    }

    private void setLayoutManager() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter() {
        DishAdapter dishAdapter = new DishAdapter(dishList);
        mRecyclerView.setAdapter(dishAdapter);
    }
}
