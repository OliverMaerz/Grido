package com.olivermaerz.grido.provider.review;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olivermaerz.grido.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code review} table.
 */
public class ReviewCursor extends AbstractCursor implements ReviewModel {
    public ReviewCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ReviewColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The foreign key _id of the favorite movie
     */
    public long getFavoriteId() {
        Long res = getLongOrNull(ReviewColumns.FAVORITE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'favorite_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Title of the movie
     * Cannot be {@code null}.
     */
    @NonNull
    public String getFavoriteOriginaltitle() {
        String res = getStringOrNull(FavoriteColumns.ORIGINALTITLE);
        if (res == null)
            throw new NullPointerException("The value of 'originaltitle' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Rating of the movie 0-10
     * Can be {@code null}.
     */
    @Nullable
    public String getFavoriteRated() {
        String res = getStringOrNull(FavoriteColumns.RATED);
        return res;
    }

    /**
     * Release date of the movie
     * Can be {@code null}.
     */
    @Nullable
    public String getFavoriteReleasedate() {
        String res = getStringOrNull(FavoriteColumns.RELEASEDATE);
        return res;
    }

    /**
     * Description of the movie
     * Can be {@code null}.
     */
    @Nullable
    public String getFavoriteDescription() {
        String res = getStringOrNull(FavoriteColumns.DESCRIPTION);
        return res;
    }

    /**
     * Movie Poster
     * Can be {@code null}.
     */
    @Nullable
    public String getFavoritePoster() {
        String res = getStringOrNull(FavoriteColumns.POSTER);
        return res;
    }

    /**
     * Reveiw of the movie
     * Cannot be {@code null}.
     */
    @NonNull
    public String getContent() {
        String res = getStringOrNull(ReviewColumns.CONTENT);
        if (res == null)
            throw new NullPointerException("The value of 'content' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Author of the review
     * Cannot be {@code null}.
     */
    @NonNull
    public String getAuthor() {
        String res = getStringOrNull(ReviewColumns.AUTHOR);
        if (res == null)
            throw new NullPointerException("The value of 'author' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
