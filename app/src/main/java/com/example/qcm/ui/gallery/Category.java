package com.example.qcm.ui.gallery;

public class Category {

    private int id;

    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Text show in Spinner
    @Override
    public String toString()  {
        return this.getName();
    }
}
