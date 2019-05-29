package com.gr232.restaurantadmin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gr232.restaurantadmin.activities.EditEmployeeActivity;
import com.gr232.restaurantadmin.activities.EmployeeInfoActivity;
import com.gr232.restaurantadmin.activities.EmployeesListActivity;
import com.gr232.restaurantadmin.models.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {

    private List<Employee> employeeList;
    private Context context;

    public EmployeeAdapter(List<Employee> employeeList, Context context) {
        this.employeeList = employeeList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.employee_view, viewGroup, false);

        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder employeeViewHolder, int i) {
        Employee employee = employeeList.get(i);

        if (employee != null) {
            if (employee.getFirstName() != null && employee.getLastName() != null) {
                String name = employee.getFirstName() + " " + employee.getLastName();
                employeeViewHolder.getmName().setText(name);
            }

            if (employee.getType() != null) {
                employeeViewHolder.getmType().setText(employee.getType());
            }

            setOnClickListeners(employeeViewHolder, employee);
        }
    }

    @Override
    public int getItemCount() {
        if (employeeList != null) {
            return employeeList.size();
        }

        return 0;
    }

    private void setOnClickListeners(@NonNull EmployeeViewHolder employeeViewHolder, final Employee emp) {

        //final Context contextLocal = context;
        employeeViewHolder.getmRemove().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        employeeViewHolder.getmEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),
                        EditEmployeeActivity.class);
                intent.putExtra("employee", emp);

                context.startActivity(intent);
            }
        });

        employeeViewHolder.getmInfo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),
                        EmployeeInfoActivity.class);

                intent.putExtra("employee", emp);
                context.startActivity(intent);
            }
        });
    }
}
