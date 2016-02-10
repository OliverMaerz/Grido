package com.olivermaerz.grido.favorite;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.olivermaerz.grido.base.AbstractSelection;

/**
 * Selection for the {@code favorite} table.
 */
public class FavoriteSelection extends AbstractSelection<FavoriteSelection> {
    @Override
    protected Uri baseUri() {
        return FavoriteColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoriteCursor} object, which is positioned before the first entry, or null.
     */
    public FavoriteCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoriteCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public FavoriteCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoriteCursor} object, which is positioned before the first entry, or null.
     */
    public FavoriteCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoriteCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public FavoriteCursor query(Context context) {
        return query(context, null);
    }


    public FavoriteSelection id(long... value) {
        addEquals("favorite." + FavoriteColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoriteSelection idNot(long... value) {
        addNotEquals("favorite." + FavoriteColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoriteSelection orderById(boolean desc) {
        orderBy("favorite." + FavoriteColumns._ID, desc);
        return this;
    }

    public FavoriteSelection orderById() {
        return orderById(false);
    }

    public FavoriteSelection originaltitle(String... value) {
        addEquals(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public FavoriteSelection originaltitleNot(String... value) {
        addNotEquals(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public FavoriteSelection originaltitleLike(String... value) {
        addLike(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public FavoriteSelection originaltitleContains(String... value) {
        addContains(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public FavoriteSelection originaltitleStartsWith(String... value) {
        addStartsWith(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public FavoriteSelection originaltitleEndsWith(String... value) {
        addEndsWith(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public FavoriteSelection orderByOriginaltitle(boolean desc) {
        orderBy(FavoriteColumns.ORIGINALTITLE, desc);
        return this;
    }

    public FavoriteSelection orderByOriginaltitle() {
        orderBy(FavoriteColumns.ORIGINALTITLE, false);
        return this;
    }

    public FavoriteSelection rated(String... value) {
        addEquals(FavoriteColumns.RATED, value);
        return this;
    }

    public FavoriteSelection ratedNot(String... value) {
        addNotEquals(FavoriteColumns.RATED, value);
        return this;
    }

    public FavoriteSelection ratedLike(String... value) {
        addLike(FavoriteColumns.RATED, value);
        return this;
    }

    public FavoriteSelection ratedContains(String... value) {
        addContains(FavoriteColumns.RATED, value);
        return this;
    }

    public FavoriteSelection ratedStartsWith(String... value) {
        addStartsWith(FavoriteColumns.RATED, value);
        return this;
    }

    public FavoriteSelection ratedEndsWith(String... value) {
        addEndsWith(FavoriteColumns.RATED, value);
        return this;
    }

    public FavoriteSelection orderByRated(boolean desc) {
        orderBy(FavoriteColumns.RATED, desc);
        return this;
    }

    public FavoriteSelection orderByRated() {
        orderBy(FavoriteColumns.RATED, false);
        return this;
    }

    public FavoriteSelection releasedate(String... value) {
        addEquals(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public FavoriteSelection releasedateNot(String... value) {
        addNotEquals(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public FavoriteSelection releasedateLike(String... value) {
        addLike(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public FavoriteSelection releasedateContains(String... value) {
        addContains(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public FavoriteSelection releasedateStartsWith(String... value) {
        addStartsWith(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public FavoriteSelection releasedateEndsWith(String... value) {
        addEndsWith(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public FavoriteSelection orderByReleasedate(boolean desc) {
        orderBy(FavoriteColumns.RELEASEDATE, desc);
        return this;
    }

    public FavoriteSelection orderByReleasedate() {
        orderBy(FavoriteColumns.RELEASEDATE, false);
        return this;
    }

    public FavoriteSelection description(String... value) {
        addEquals(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public FavoriteSelection descriptionNot(String... value) {
        addNotEquals(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public FavoriteSelection descriptionLike(String... value) {
        addLike(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public FavoriteSelection descriptionContains(String... value) {
        addContains(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public FavoriteSelection descriptionStartsWith(String... value) {
        addStartsWith(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public FavoriteSelection descriptionEndsWith(String... value) {
        addEndsWith(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public FavoriteSelection orderByDescription(boolean desc) {
        orderBy(FavoriteColumns.DESCRIPTION, desc);
        return this;
    }

    public FavoriteSelection orderByDescription() {
        orderBy(FavoriteColumns.DESCRIPTION, false);
        return this;
    }

    public FavoriteSelection poster(String... value) {
        addEquals(FavoriteColumns.POSTER, value);
        return this;
    }

    public FavoriteSelection posterNot(String... value) {
        addNotEquals(FavoriteColumns.POSTER, value);
        return this;
    }

    public FavoriteSelection posterLike(String... value) {
        addLike(FavoriteColumns.POSTER, value);
        return this;
    }

    public FavoriteSelection posterContains(String... value) {
        addContains(FavoriteColumns.POSTER, value);
        return this;
    }

    public FavoriteSelection posterStartsWith(String... value) {
        addStartsWith(FavoriteColumns.POSTER, value);
        return this;
    }

    public FavoriteSelection posterEndsWith(String... value) {
        addEndsWith(FavoriteColumns.POSTER, value);
        return this;
    }

    public FavoriteSelection orderByPoster(boolean desc) {
        orderBy(FavoriteColumns.POSTER, desc);
        return this;
    }

    public FavoriteSelection orderByPoster() {
        orderBy(FavoriteColumns.POSTER, false);
        return this;
    }
}
