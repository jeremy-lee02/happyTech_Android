package com.example.happytechhomepageui.Modals;

public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber ;
    private String address;
    private String gender;

    public User() {
    }

    public User(String email, String firstName, String lastName, String phoneNumber, String address, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
