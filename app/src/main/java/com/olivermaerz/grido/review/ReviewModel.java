package com.olivermaerz.grido.review;

import com.olivermaerz.grido.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Favorite movies selected by the user.
 */
public interface ReviewModel extends BaseModel {

    /**
     * The foreign key _id of the favorite movie
     */
    long getFavoriteId();

    /**
     * Reveiw of the movie
     * Cannot be {@code null}.
     */
    @NonNull
    String getContent();

    /**
     * Author of the review
     * Cannot be {@code null}.
     */
    @NonNull
    String getAuthor();
}
