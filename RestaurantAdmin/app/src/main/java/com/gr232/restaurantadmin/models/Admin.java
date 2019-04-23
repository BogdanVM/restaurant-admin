package com.gr232.restaurantadmin.models;

import java.util.Calendar;

public class Admin extends Employee{

    public Admin(String userId, String firstName, String lastName, String photo,
                 Double salary, Calendar hireDate) {
        super(userId, firstName, lastName, photo, salary, hireDate);

        this.type = "admin";
    }
}
