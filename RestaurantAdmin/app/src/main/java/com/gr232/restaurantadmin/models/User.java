package com.gr232.restaurantadmin.models;

public abstract class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String photo;

    String type;

    User() { }

    User(String userId, String firstName, String lastName, String photo) {
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
