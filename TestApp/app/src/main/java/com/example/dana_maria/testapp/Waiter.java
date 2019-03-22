package com.example.dana_maria.testapp;

public class Waiter {
    private String name;
    private String gender;
    private String phoneNumber;
    private String email;
    private String cnp;

    public Waiter() {
    }

    public Waiter(String name, String gender, String phoneNumber, String email, String cnp) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getCnp() {
        return cnp;
    }
}
