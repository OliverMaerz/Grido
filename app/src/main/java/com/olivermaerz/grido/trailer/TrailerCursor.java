package com.olivermaerz.grido.trailer;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olivermaerz.grido.base.AbstractCursor;
import com.olivermaerz.grido.favorite.*;

/**
 * Cursor wrapper for the {@code trailer} table.
 */
public class TrailerCursor extends AbstractCursor implements TrailerModel {
    public TrailerCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(TrailerColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The foreign key _id of the favorite movie
     */
    public long getFavoriteId() {
        Long res = getLongOrNull(TrailerColumns.FAVORITE_ID);
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
     * Name of the trailer
     * Cannot be {@code null}.
     */
    @NonNull
    public String getName() {
        String res = getStringOrNull(TrailerColumns.NAME);
        if (res == null)
            throw new NullPointerException("The value of 'name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Youtube URL
     * Cannot be {@code null}.
     */
    @NonNull
    public String getSource() {
        String res = getStringOrNull(TrailerColumns.SOURCE);
        if (res == null)
            throw new NullPointerException("The value of 'source' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
