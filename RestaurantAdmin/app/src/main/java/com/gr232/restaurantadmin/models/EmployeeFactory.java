package com.gr232.restaurantadmin.models;

import java.util.Calendar;
import java.util.List;

public class EmployeeFactory {
    public Employee getEmployee(String type, List<String> userData, Double salary,
                                Calendar hireDate) {
        if (type == null) {
            return null;
        }

        if (type.equalsIgnoreCase("WAITER")) {
            return new Waiter(userData.get(0), userData.get(1), userData.get(2), userData.get(3),
                    salary, hireDate);
        } else if (type.equalsIgnoreCase("CHEF")) {
            return null;
        }

        return null;
    }
}
