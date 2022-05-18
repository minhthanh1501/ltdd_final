package com.example.android.finalproject.model;

public class User {
    int id;
    String fullname ;
    String email;
    String password;
    String mobile;

    public User(int id, String fullname, String email, String password, String mobile) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
