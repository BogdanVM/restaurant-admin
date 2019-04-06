package com.gr232.restaurantadmin.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

abstract class User {
    private String firstName;
    private String lastName;
    private String photo;
    private List<Shift> shifts;

    public User() { }

    public User(String firstName, String lastName, String photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;

        this.shifts = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String profilePhoto) {
        this.photo = profilePhoto;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void addShift(Calendar startDate, Calendar endDate) {
        this.shifts.add(new Shift(startDate, endDate));
    }

    public void addShift(Shift shift) {
        this.shifts.add(shift);
    }


}
