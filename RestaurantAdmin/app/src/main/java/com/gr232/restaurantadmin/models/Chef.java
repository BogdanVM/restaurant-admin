package com.gr232.restaurantadmin.models;

import java.io.Serializable;
import java.util.Calendar;

public class Chef extends Employee {
    public Chef(String userId, String firstName, String lastName, String photo,
                Double salary, Calendar hireDate) {
        super(userId, firstName, lastName, photo, salary, hireDate);
        this.type = "chef";
    }
}
