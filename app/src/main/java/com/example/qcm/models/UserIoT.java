package com.example.qcm.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserIoT implements Parcelable {

    private int id;
    private String token;
    private String first_name;
    private String last_name;
    private int last_score;
    private String profile_url;
    private String random_color;
    private int is_present;

    protected UserIoT(Parcel in) {
        id = in.readInt();
        token = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        last_score = in.readInt();
        profile_url = in.readString();
        random_color = in.readString();
        is_present = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(token);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeInt(last_score);
        dest.writeString(profile_url);
        dest.writeString(random_color);
        dest.writeInt(is_present);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserIoT> CREATOR = new Creator<UserIoT>() {
        @Override
        public UserIoT createFromParcel(Parcel in) {
            return new UserIoT(in);
        }

        @Override
        public UserIoT[] newArray(int size) {
            return new UserIoT[size];
        }
    };

    public int getId() {
        return this.id;
    }
    public String getToken() {
        return this.token;
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
        return this.is_present == 1 ? true : false;
    }

}
