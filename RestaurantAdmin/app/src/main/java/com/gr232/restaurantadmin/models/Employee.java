package com.gr232.restaurantadmin.models;

import java.util.Calendar;

public abstract class Employee extends User {
    protected Double salary;
    protected Calendar hireDate;

    public Employee(String userId, String firstName, String lastName, String photo,
                    Double salary, Calendar hireDate) {
        super(userId, firstName, lastName, photo);
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Calendar getHireDate() {
        return hireDate;
    }

    public void setHireDate(Calendar hireDate) {
        this.hireDate = hireDate;
    }
}
