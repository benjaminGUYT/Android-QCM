package com.example.qcm.models;

public class FriendsIoT {
    private int id;
    private String first_name;
    private String last_name;
    private int last_score;
    private String profile_url;
    private String random_color;
    private boolean is_present;

    public int getId() {
        return this.id;
    }
    public String getFirstName() {
        return this.first_name;
    }
    public String getLastName() {
        return this.last_name;
    }
    public int getLastScore() {
        return this.last_score;
    }
    public String getProfileUrl() {
        return this.profile_url;
    }
    public String getRandomColor() {
        return this.random_color;
    }
    public boolean isPresent() {
        return this.is_present;
    }
}
