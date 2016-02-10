package com.olivermaerz.grido.provider.trailer;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.olivermaerz.grido.provider.base.AbstractSelection;

/**
 * Selection for the {@code trailer} table.
 */
public class TrailerSelection extends AbstractSelection<TrailerSelection> {
    @Override
    protected Uri baseUri() {
        return TrailerColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailerCursor} object, which is positioned before the first entry, or null.
     */
    public TrailerCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailerCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public TrailerCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailerCursor} object, which is positioned before the first entry, or null.
     */
    public TrailerCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailerCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public TrailerCursor query(Context context) {
        return query(context, null);
    }


    public TrailerSelection id(long... value) {
        addEquals("trailer." + TrailerColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection idNot(long... value) {
        addNotEquals("trailer." + TrailerColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection orderById(boolean desc) {
        orderBy("trailer." + TrailerColumns._ID, desc);
        return this;
    }

    public TrailerSelection orderById() {
        return orderById(false);
    }

    public TrailerSelection favoriteId(long... value) {
        addEquals(TrailerColumns.FAVORITE_ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection favoriteIdNot(long... value) {
        addNotEquals(TrailerColumns.FAVORITE_ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection favoriteIdGt(long value) {
        addGreaterThan(TrailerColumns.FAVORITE_ID, value);
        return this;
    }

    public TrailerSelection favoriteIdGtEq(long value) {
        addGreaterThanOrEquals(TrailerColumns.FAVORITE_ID, value);
        return this;
    }

    public TrailerSelection favoriteIdLt(long value) {
        addLessThan(TrailerColumns.FAVORITE_ID, value);
        return this;
    }

    public TrailerSelection favoriteIdLtEq(long value) {
        addLessThanOrEquals(TrailerColumns.FAVORITE_ID, value);
        return this;
    }

    public TrailerSelection orderByFavoriteId(boolean desc) {
        orderBy(TrailerColumns.FAVORITE_ID, desc);
        return this;
    }

    public TrailerSelection orderByFavoriteId() {
        orderBy(TrailerColumns.FAVORITE_ID, false);
        return this;
    }

    public TrailerSelection favoriteOriginaltitle(String... value) {
        addEquals(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public TrailerSelection favoriteOriginaltitleNot(String... value) {
        addNotEquals(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public TrailerSelection favoriteOriginaltitleLike(String... value) {
        addLike(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public TrailerSelection favoriteOriginaltitleContains(String... value) {
        addContains(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public TrailerSelection favoriteOriginaltitleStartsWith(String... value) {
        addStartsWith(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public TrailerSelection favoriteOriginaltitleEndsWith(String... value) {
        addEndsWith(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public TrailerSelection orderByFavoriteOriginaltitle(boolean desc) {
        orderBy(FavoriteColumns.ORIGINALTITLE, desc);
        return this;
    }

    public TrailerSelection orderByFavoriteOriginaltitle() {
        orderBy(FavoriteColumns.ORIGINALTITLE, false);
        return this;
    }

    public TrailerSelection favoriteRated(String... value) {
        addEquals(FavoriteColumns.RATED, value);
        return this;
    }

    public TrailerSelection favoriteRatedNot(String... value) {
        addNotEquals(FavoriteColumns.RATED, value);
        return this;
    }

    public TrailerSelection favoriteRatedLike(String... value) {
        addLike(FavoriteColumns.RATED, value);
        return this;
    }

    public TrailerSelection favoriteRatedContains(String... value) {
        addContains(FavoriteColumns.RATED, value);
        return this;
    }

    public TrailerSelection favoriteRatedStartsWith(String... value) {
        addStartsWith(FavoriteColumns.RATED, value);
        return this;
    }

    public TrailerSelection favoriteRatedEndsWith(String... value) {
        addEndsWith(FavoriteColumns.RATED, value);
        return this;
    }

    public TrailerSelection orderByFavoriteRated(boolean desc) {
        orderBy(FavoriteColumns.RATED, desc);
        return this;
    }

    public TrailerSelection orderByFavoriteRated() {
        orderBy(FavoriteColumns.RATED, false);
        return this;
    }

    public TrailerSelection favoriteReleasedate(String... value) {
        addEquals(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection favoriteReleasedateNot(String... value) {
        addNotEquals(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection favoriteReleasedateLike(String... value) {
        addLike(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection favoriteReleasedateContains(String... value) {
        addContains(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection favoriteReleasedateStartsWith(String... value) {
        addStartsWith(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection favoriteReleasedateEndsWith(String... value) {
        addEndsWith(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection orderByFavoriteReleasedate(boolean desc) {
        orderBy(FavoriteColumns.RELEASEDATE, desc);
        return this;
    }

    public TrailerSelection orderByFavoriteReleasedate() {
        orderBy(FavoriteColumns.RELEASEDATE, false);
        return this;
    }

    public TrailerSelection favoriteDescription(String... value) {
        addEquals(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection favoriteDescriptionNot(String... value) {
        addNotEquals(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection favoriteDescriptionLike(String... value) {
        addLike(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection favoriteDescriptionContains(String... value) {
        addContains(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection favoriteDescriptionStartsWith(String... value) {
        addStartsWith(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection favoriteDescriptionEndsWith(String... value) {
        addEndsWith(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection orderByFavoriteDescription(boolean desc) {
        orderBy(FavoriteColumns.DESCRIPTION, desc);
        return this;
    }

    public TrailerSelection orderByFavoriteDescription() {
        orderBy(FavoriteColumns.DESCRIPTION, false);
        return this;
    }

    public TrailerSelection favoritePoster(String... value) {
        addEquals(FavoriteColumns.POSTER, value);
        return this;
    }

    public TrailerSelection favoritePosterNot(String... value) {
        addNotEquals(FavoriteColumns.POSTER, value);
        return this;
    }

    public TrailerSelection favoritePosterLike(String... value) {
        addLike(FavoriteColumns.POSTER, value);
        return this;
    }

    public TrailerSelection favoritePosterContains(String... value) {
        addContains(FavoriteColumns.POSTER, value);
        return this;
    }

    public TrailerSelection favoritePosterStartsWith(String... value) {
        addStartsWith(FavoriteColumns.POSTER, value);
        return this;
    }

    public TrailerSelection favoritePosterEndsWith(String... value) {
        addEndsWith(FavoriteColumns.POSTER, value);
        return this;
    }

    public TrailerSelection orderByFavoritePoster(boolean desc) {
        orderBy(FavoriteColumns.POSTER, desc);
        return this;
    }

    public TrailerSelection orderByFavoritePoster() {
        orderBy(FavoriteColumns.POSTER, false);
        return this;
    }

    public TrailerSelection name(String... value) {
        addEquals(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameNot(String... value) {
        addNotEquals(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameLike(String... value) {
        addLike(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameContains(String... value) {
        addContains(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameStartsWith(String... value) {
        addStartsWith(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameEndsWith(String... value) {
        addEndsWith(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection orderByName(boolean desc) {
        orderBy(TrailerColumns.NAME, desc);
        return this;
    }

    public TrailerSelection orderByName() {
        orderBy(TrailerColumns.NAME, false);
        return this;
    }

    public TrailerSelection source(String... value) {
        addEquals(TrailerColumns.SOURCE, value);
        return this;
    }

    public TrailerSelection sourceNot(String... value) {
        addNotEquals(TrailerColumns.SOURCE, value);
        return this;
    }

    public TrailerSelection sourceLike(String... value) {
        addLike(TrailerColumns.SOURCE, value);
        return this;
    }

    public TrailerSelection sourceContains(String... value) {
        addContains(TrailerColumns.SOURCE, value);
        return this;
    }

    public TrailerSelection sourceStartsWith(String... value) {
        addStartsWith(TrailerColumns.SOURCE, value);
        return this;
    }

    public TrailerSelection sourceEndsWith(String... value) {
        addEndsWith(TrailerColumns.SOURCE, value);
        return this;
    }

    public TrailerSelection orderBySource(boolean desc) {
        orderBy(TrailerColumns.SOURCE, desc);
        return this;
    }

    public TrailerSelection orderBySource() {
        orderBy(TrailerColumns.SOURCE, false);
        return this;
    }
}
