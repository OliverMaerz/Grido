package com.olivermaerz.grido.provider.trailer;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olivermaerz.grido.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code trailer} table.
 */
public class TrailerContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TrailerColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable TrailerSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable TrailerSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * The foreign key _id of the favorite movie
     */
    public TrailerContentValues putFavoriteId(long value) {
        mContentValues.put(TrailerColumns.FAVORITE_ID, value);
        return this;
    }


    /**
     * Name of the trailer
     */
    public TrailerContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(TrailerColumns.NAME, value);
        return this;
    }


    /**
     * Youtube URL
     */
    public TrailerContentValues putSource(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("source must not be null");
        mContentValues.put(TrailerColumns.SOURCE, value);
        return this;
    }

}
