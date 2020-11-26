package com.example.bookify;

public class User {
    public String name, email;
    public boolean contributor;
    public User(){

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.contributor = false;
    }
}
