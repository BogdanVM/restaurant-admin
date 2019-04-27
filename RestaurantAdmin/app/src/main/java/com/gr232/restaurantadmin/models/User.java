package com.gr232.restaurantadmin.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class User {
    protected String userId;
    protected String firstName;
    protected String lastName;
    protected String photo;
    protected String type;

    public User() { }

    public User(String userId, String firstName, String lastName, String photo) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }
}
