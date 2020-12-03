package com.example.bookify;

public class User {
    private String name, email;
    private boolean contributor;
    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isContributor() {
        return contributor;
    }

    public void setContributor(boolean contributor) {
        this.contributor = contributor;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.contributor = false;
    }
}
