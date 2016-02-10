package com.olivermaerz.grido.favorite;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olivermaerz.grido.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code favorite} table.
 */
public class FavoriteCursor extends AbstractCursor implements FavoriteModel {
    public FavoriteCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(FavoriteColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Title of the movie
     * Cannot be {@code null}.
     */
    @NonNull
    public String getOriginaltitle() {
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
    public String getRated() {
        String res = getStringOrNull(FavoriteColumns.RATED);
        return res;
    }

    /**
     * Release date of the movie
     * Can be {@code null}.
     */
    @Nullable
    public String getReleasedate() {
        String res = getStringOrNull(FavoriteColumns.RELEASEDATE);
        return res;
    }

    /**
     * Description of the movie
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(FavoriteColumns.DESCRIPTION);
        return res;
    }

    /**
     * Movie Poster
     * Can be {@code null}.
     */
    @Nullable
    public String getPoster() {
        String res = getStringOrNull(FavoriteColumns.POSTER);
        return res;
    }
}
