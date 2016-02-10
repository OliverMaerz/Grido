package com.olivermaerz.grido.provider.favorite;

import com.olivermaerz.grido.provider.base.BaseModel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Favorite movies selected by the user.
 */
public interface FavoriteModel extends BaseModel {

    /**
     * Title of the movie
     * Cannot be {@code null}.
     */
    @NonNull
    String getOriginaltitle();

    /**
     * Rating of the movie 0-10
     * Can be {@code null}.
     */
    @Nullable
    String getRated();

    /**
     * Release date of the movie
     * Can be {@code null}.
     */
    @Nullable
    String getReleasedate();

    /**
     * Description of the movie
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();

    /**
     * Movie Poster
     * Can be {@code null}.
     */
    @Nullable
    String getPoster();
}
