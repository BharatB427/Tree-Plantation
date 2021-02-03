package com.example.uman_android_project.tree;

import java.io.Serializable;

public class Tree implements Serializable {
    private String id;
    private String owner;
    private String name;
    private String category;
    private String size;
    private String gps;
    private String date;
    private String photo;
    private String comment;

    public Tree(String name, String size, String category, String gps, String date, String comment, String owner) {
        this.id = "1";
        this.photo = "photo";
        this.name = name;
        this.size = size;
        this.category = category;
        this.gps = gps;
        this.date = date;
        this.comment = comment;
        this.owner = owner;
    }

    public Tree(String id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
