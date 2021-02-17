package com.example.uman_android_project.tree;

import java.io.Serializable;
import java.util.UUID;

public class Tree implements Serializable {
    private String id;
    private String owner;
    private String area;
    private String position;
    private String date;
    private String photoUri;
    private String comment;

    public Tree(String area, String position, String date, String comment, String owner, String photoUri) {
        UUID uuid = UUID.randomUUID();
        id = uuid.toString();
        id = id.replace("-","");
        this.area = area;
        this.position = position;
        this.date = date;
        this.comment = comment;
        this.owner = owner;
        this.photoUri = photoUri;
    }

    public Tree(){
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
