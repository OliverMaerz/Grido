package com.olivermaerz.grido;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by omaerz on 11/25/15.
 */
public class Movies implements Parcelable {
    public String originalTitle;
    public String posterUrl;
    public String description;
    public String releaseDate;
    public Double rating;
    public static final String EXTRA_MOVIE = "com.olivermaerz.grido.EXTRA_MOVIE";


    // write strings into parcel
    public Movies(String oTitle, String pUrl, String desc, String rDate, Double rating){
        this.originalTitle = oTitle;
        this.posterUrl = pUrl;
        this.description = desc;
        this.releaseDate = rDate;
        this.rating = rating;

    }

    // receive the parcel
    private Movies(Parcel in) {
        this.originalTitle = in.readString();
        this.posterUrl = in.readString();
        this.description = in.readString();
        this.releaseDate = in.readString();
        this.rating = in.readDouble();
        //this.poster = in.read
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(posterUrl);
        dest.writeString(description);
        dest.writeString(releaseDate);
        dest.writeDouble(rating);

    }

    public String getPosterUrl() {
        return this.posterUrl;
    }


    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        // calls our new constructor an pass along the un-marshaled `Parcel` and then return the new object
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
