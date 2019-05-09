package com.gr232.restaurantadmin.models;

import java.io.Serializable;
import java.util.Calendar;

public class Employee extends User implements Serializable {
    protected Double salary;
    protected Calendar hireDate;

    public Employee(String userId, String firstName, String lastName, String photo,
                    Double salary, Calendar hireDate) {
        super(userId, firstName, lastName, photo);
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public Employee() {
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
