package com.example.uman_android_project.model;

import java.io.Serializable;

public class Order implements Serializable {

    private String id;
    private String owner;
    private String area;
    private String position;
    private String date;
    private String photoUri;
    private String comment;

    public Order(){

    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getArea() {
        return area;
    }

    public String getPosition() {
        return position;
    }

    public String getDate() {
        return date;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public String getComment() {
        return comment;
    }
}
