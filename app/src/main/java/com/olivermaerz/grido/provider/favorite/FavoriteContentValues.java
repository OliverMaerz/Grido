package com.olivermaerz.grido.provider.favorite;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olivermaerz.grido.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code favorite} table.
 */
public class FavoriteContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return FavoriteColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable FavoriteSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable FavoriteSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Title of the movie
     */
    public FavoriteContentValues putOriginaltitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("originaltitle must not be null");
        mContentValues.put(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }


    /**
     * Rating of the movie 0-10
     */
    public FavoriteContentValues putRated(@Nullable String value) {
        mContentValues.put(FavoriteColumns.RATED, value);
        return this;
    }

    public FavoriteContentValues putRatedNull() {
        mContentValues.putNull(FavoriteColumns.RATED);
        return this;
    }

    /**
     * Release date of the movie
     */
    public FavoriteContentValues putReleasedate(@Nullable String value) {
        mContentValues.put(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public FavoriteContentValues putReleasedateNull() {
        mContentValues.putNull(FavoriteColumns.RELEASEDATE);
        return this;
    }

    /**
     * Description of the movie
     */
    public FavoriteContentValues putDescription(@Nullable String value) {
        mContentValues.put(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public FavoriteContentValues putDescriptionNull() {
        mContentValues.putNull(FavoriteColumns.DESCRIPTION);
        return this;
    }

    /**
     * Movie Poster
     */
    public FavoriteContentValues putPoster(@Nullable String value) {
        mContentValues.put(FavoriteColumns.POSTER, value);
        return this;
    }

    public FavoriteContentValues putPosterNull() {
        mContentValues.putNull(FavoriteColumns.POSTER);
        return this;
    }
}
