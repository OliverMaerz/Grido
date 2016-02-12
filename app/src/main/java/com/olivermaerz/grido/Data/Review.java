package com.olivermaerz.grido.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by omaerz on 11/25/15.
 */
public class Review implements Parcelable {
    public String author;
    public String content;


    // write strings into parcel
    public Review(String author, String content){
        this.author = author;
        this.content = content;

    }

    // receive the parcel
    private Review(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
    }


    public static final Creator<Review> CREATOR = new Creator<Review>() {
        // calls our new constructor an pass along the un-marshaled `Parcel` and then return the new object
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
