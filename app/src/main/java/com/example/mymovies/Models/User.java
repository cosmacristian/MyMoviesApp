package com.example.mymovies.Models;

import java.io.Serializable;

public class User implements Serializable {
    public String id;
    public String userName;
    public String email;
    public String password;
    public String imageURI;


    public User(String id, String userName, String email, String password, String image) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.imageURI = image;
    }

    public User(User that) {
        this.id = that.id;
        this.userName = that.userName;
        this.email = that.email;
        this.password = that.password;
        this.imageURI = that.imageURI;
    }
}
