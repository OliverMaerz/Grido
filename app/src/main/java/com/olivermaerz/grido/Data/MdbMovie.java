package com.olivermaerz.grido.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by omaerz on 11/25/15.
 */
public class MdbMovie implements Parcelable {
    public long id;
    public String originalTitle;
    public String posterUrl;
    public String description;
    public String releaseDate;
    public Double rating;
    public Boolean detailsRetrieved;
    public ArrayList<Review> reviews;
    public ArrayList<Trailer> trailers;
    public static final String EXTRA_MOVIE = "com.olivermaerz.grido.EXTRA_MOVIE";

    public MdbMovie() {
       reviews = new ArrayList<>();
    }

    // write data into parcel
    public MdbMovie(int id, String oTitle, String pUrl, String desc, String rDate, Double rating, Boolean detailsRetrieved, ArrayList<Review> reviews, ArrayList<Trailer> trailers){
        this.id = id;
        this.originalTitle = oTitle;
        this.posterUrl = pUrl;
        this.description = desc;
        this.releaseDate = rDate;
        this.rating = rating;
        this.detailsRetrieved = detailsRetrieved;
        this.reviews = reviews;
        this.trailers = trailers;

    }

    // read from parcel
    private MdbMovie(Parcel in) {
        this();

        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.posterUrl = in.readString();
        this.description = in.readString();
        this.releaseDate = in.readString();
        this.rating = in.readDouble();
        this.detailsRetrieved = in.readByte() != 0; //detailsRetrieved == true if byte != 0
        this.reviews = in.readArrayList(Review.class.getClassLoader());
        this.trailers = in.readArrayList(Trailer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.posterUrl);
        dest.writeString(this.description);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.rating);
        dest.writeByte((byte) (this.detailsRetrieved ? 1 : 0));
        dest.writeList(this.reviews);
        dest.writeList(this.trailers);
    }

    public String getPosterUrl() {
        return this.posterUrl;
    }


    public static final Parcelable.Creator<MdbMovie> CREATOR = new Parcelable.Creator<MdbMovie>() {
        // calls our new constructor an pass along the un-marshaled `Parcel` and then return the new object
        @Override
        public MdbMovie createFromParcel(Parcel in) {
            return new MdbMovie(in);
        }

        @Override
        public MdbMovie[] newArray(int size) {
            return new MdbMovie[size];
        }
    };
}
