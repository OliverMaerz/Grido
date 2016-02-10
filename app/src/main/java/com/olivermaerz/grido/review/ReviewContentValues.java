package com.olivermaerz.grido.review;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.olivermaerz.grido.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code review} table.
 */
public class ReviewContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ReviewColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ReviewSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable ReviewSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * The foreign key _id of the favorite movie
     */
    public ReviewContentValues putFavoriteId(long value) {
        mContentValues.put(ReviewColumns.FAVORITE_ID, value);
        return this;
    }


    /**
     * Reveiw of the movie
     */
    public ReviewContentValues putContent(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("content must not be null");
        mContentValues.put(ReviewColumns.CONTENT, value);
        return this;
    }


    /**
     * Author of the review
     */
    public ReviewContentValues putAuthor(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("author must not be null");
        mContentValues.put(ReviewColumns.AUTHOR, value);
        return this;
    }

}
