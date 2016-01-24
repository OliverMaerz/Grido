package com.olivermaerz.grido;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by omaerz on 11/25/15.
 */
public class Reviews implements Parcelable {
    public String id;
    public String author;
    public String content;


    // write strings into parcel
    public Reviews(String id, String author, String content){
        this.id = id;
        this.author = author;
        this.content = content;

    }

    // receive the parcel
    private Reviews(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
    }


    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        // calls our new constructor an pass along the un-marshaled `Parcel` and then return the new object
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}
