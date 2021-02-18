package com.example.uman_android_project.model;

import java.io.Serializable;

public class Farmer implements Serializable {

    private String userId;
    private String userName;
    private String lastName;
    //private String pictureURL;

    public Farmer(){

    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastName() {
        return lastName;
    }
}
