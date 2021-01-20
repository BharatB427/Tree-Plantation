package com.example.uman_android_project;

public class Tree {
    private String first;
    private String last;
    private String born;
    private String owner;

    public Tree() {
    }

    public Tree(String first, String last, String born, String owner) {
        this.first = first;
        this.last = last;
        this.born = born;
        this.owner = owner;
    }

    public Tree(String first, String last, String born) {
        this.first = first;
        this.last = last;
        this.born = born;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
