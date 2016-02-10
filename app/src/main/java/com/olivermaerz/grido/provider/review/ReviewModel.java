package com.olivermaerz.grido.provider.review;

import com.olivermaerz.grido.provider.base.BaseModel;

import android.support.annotation.NonNull;

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
