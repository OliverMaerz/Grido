package com.olivermaerz.grido;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by omaerz on 11/27/15.
 */
public class Config implements Parcelable {
    public String sortOrder;

    //public static final String CONGIG = "com.olivermaerz.grido.CONFIG";


    // write strings into parcel
    public Config(String sortOrder){
        this.sortOrder = sortOrder;
    }

    // receive the parcel
    private Config(Parcel in) {
        this.sortOrder = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sortOrder);
    }

    public static final Parcelable.Creator<Config> CREATOR = new Parcelable.Creator<Config>() {
        // calls our new constructor an pass along the un-marshaled `Parcel` and then
        // return the new object
        @Override
        public Config createFromParcel(Parcel in) {
            return new Config(in);
        }

        @Override
        public Config[] newArray(int size) {
            return new Config[size];
        }
    };
}
