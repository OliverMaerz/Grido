package com.olivermaerz.grido.provider.review;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.olivermaerz.grido.provider.base.AbstractSelection;
import com.olivermaerz.grido.provider.favorite.*;

/**
 * Selection for the {@code review} table.
 */
public class ReviewSelection extends AbstractSelection<ReviewSelection> {
    @Override
    protected Uri baseUri() {
        return ReviewColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ReviewCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ReviewCursor query(Context context) {
        return query(context, null);
    }


    public ReviewSelection id(long... value) {
        addEquals("review." + ReviewColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection idNot(long... value) {
        addNotEquals("review." + ReviewColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection orderById(boolean desc) {
        orderBy("review." + ReviewColumns._ID, desc);
        return this;
    }

    public ReviewSelection orderById() {
        return orderById(false);
    }

    public ReviewSelection favoriteId(long... value) {
        addEquals(ReviewColumns.FAVORITE_ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection favoriteIdNot(long... value) {
        addNotEquals(ReviewColumns.FAVORITE_ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection favoriteIdGt(long value) {
        addGreaterThan(ReviewColumns.FAVORITE_ID, value);
        return this;
    }

    public ReviewSelection favoriteIdGtEq(long value) {
        addGreaterThanOrEquals(ReviewColumns.FAVORITE_ID, value);
        return this;
    }

    public ReviewSelection favoriteIdLt(long value) {
        addLessThan(ReviewColumns.FAVORITE_ID, value);
        return this;
    }

    public ReviewSelection favoriteIdLtEq(long value) {
        addLessThanOrEquals(ReviewColumns.FAVORITE_ID, value);
        return this;
    }

    public ReviewSelection orderByFavoriteId(boolean desc) {
        orderBy(ReviewColumns.FAVORITE_ID, desc);
        return this;
    }

    public ReviewSelection orderByFavoriteId() {
        orderBy(ReviewColumns.FAVORITE_ID, false);
        return this;
    }

    public ReviewSelection favoriteOriginaltitle(String... value) {
        addEquals(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public ReviewSelection favoriteOriginaltitleNot(String... value) {
        addNotEquals(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public ReviewSelection favoriteOriginaltitleLike(String... value) {
        addLike(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public ReviewSelection favoriteOriginaltitleContains(String... value) {
        addContains(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public ReviewSelection favoriteOriginaltitleStartsWith(String... value) {
        addStartsWith(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public ReviewSelection favoriteOriginaltitleEndsWith(String... value) {
        addEndsWith(FavoriteColumns.ORIGINALTITLE, value);
        return this;
    }

    public ReviewSelection orderByFavoriteOriginaltitle(boolean desc) {
        orderBy(FavoriteColumns.ORIGINALTITLE, desc);
        return this;
    }

    public ReviewSelection orderByFavoriteOriginaltitle() {
        orderBy(FavoriteColumns.ORIGINALTITLE, false);
        return this;
    }

    public ReviewSelection favoriteRated(String... value) {
        addEquals(FavoriteColumns.RATED, value);
        return this;
    }

    public ReviewSelection favoriteRatedNot(String... value) {
        addNotEquals(FavoriteColumns.RATED, value);
        return this;
    }

    public ReviewSelection favoriteRatedLike(String... value) {
        addLike(FavoriteColumns.RATED, value);
        return this;
    }

    public ReviewSelection favoriteRatedContains(String... value) {
        addContains(FavoriteColumns.RATED, value);
        return this;
    }

    public ReviewSelection favoriteRatedStartsWith(String... value) {
        addStartsWith(FavoriteColumns.RATED, value);
        return this;
    }

    public ReviewSelection favoriteRatedEndsWith(String... value) {
        addEndsWith(FavoriteColumns.RATED, value);
        return this;
    }

    public ReviewSelection orderByFavoriteRated(boolean desc) {
        orderBy(FavoriteColumns.RATED, desc);
        return this;
    }

    public ReviewSelection orderByFavoriteRated() {
        orderBy(FavoriteColumns.RATED, false);
        return this;
    }

    public ReviewSelection favoriteReleasedate(String... value) {
        addEquals(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection favoriteReleasedateNot(String... value) {
        addNotEquals(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection favoriteReleasedateLike(String... value) {
        addLike(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection favoriteReleasedateContains(String... value) {
        addContains(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection favoriteReleasedateStartsWith(String... value) {
        addStartsWith(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection favoriteReleasedateEndsWith(String... value) {
        addEndsWith(FavoriteColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection orderByFavoriteReleasedate(boolean desc) {
        orderBy(FavoriteColumns.RELEASEDATE, desc);
        return this;
    }

    public ReviewSelection orderByFavoriteReleasedate() {
        orderBy(FavoriteColumns.RELEASEDATE, false);
        return this;
    }

    public ReviewSelection favoriteDescription(String... value) {
        addEquals(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection favoriteDescriptionNot(String... value) {
        addNotEquals(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection favoriteDescriptionLike(String... value) {
        addLike(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection favoriteDescriptionContains(String... value) {
        addContains(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection favoriteDescriptionStartsWith(String... value) {
        addStartsWith(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection favoriteDescriptionEndsWith(String... value) {
        addEndsWith(FavoriteColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection orderByFavoriteDescription(boolean desc) {
        orderBy(FavoriteColumns.DESCRIPTION, desc);
        return this;
    }

    public ReviewSelection orderByFavoriteDescription() {
        orderBy(FavoriteColumns.DESCRIPTION, false);
        return this;
    }

    public ReviewSelection favoritePoster(String... value) {
        addEquals(FavoriteColumns.POSTER, value);
        return this;
    }

    public ReviewSelection favoritePosterNot(String... value) {
        addNotEquals(FavoriteColumns.POSTER, value);
        return this;
    }

    public ReviewSelection favoritePosterLike(String... value) {
        addLike(FavoriteColumns.POSTER, value);
        return this;
    }

    public ReviewSelection favoritePosterContains(String... value) {
        addContains(FavoriteColumns.POSTER, value);
        return this;
    }

    public ReviewSelection favoritePosterStartsWith(String... value) {
        addStartsWith(FavoriteColumns.POSTER, value);
        return this;
    }

    public ReviewSelection favoritePosterEndsWith(String... value) {
        addEndsWith(FavoriteColumns.POSTER, value);
        return this;
    }

    public ReviewSelection orderByFavoritePoster(boolean desc) {
        orderBy(FavoriteColumns.POSTER, desc);
        return this;
    }

    public ReviewSelection orderByFavoritePoster() {
        orderBy(FavoriteColumns.POSTER, false);
        return this;
    }

    public ReviewSelection favoriteMovieId(Long... value) {
        addEquals(FavoriteColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection favoriteMovieIdNot(Long... value) {
        addNotEquals(FavoriteColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection favoriteMovieIdGt(long value) {
        addGreaterThan(FavoriteColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection favoriteMovieIdGtEq(long value) {
        addGreaterThanOrEquals(FavoriteColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection favoriteMovieIdLt(long value) {
        addLessThan(FavoriteColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection favoriteMovieIdLtEq(long value) {
        addLessThanOrEquals(FavoriteColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection orderByFavoriteMovieId(boolean desc) {
        orderBy(FavoriteColumns.MOVIE_ID, desc);
        return this;
    }

    public ReviewSelection orderByFavoriteMovieId() {
        orderBy(FavoriteColumns.MOVIE_ID, false);
        return this;
    }

    public ReviewSelection content(String... value) {
        addEquals(ReviewColumns.CONTENT, value);
        return this;
    }

    public ReviewSelection contentNot(String... value) {
        addNotEquals(ReviewColumns.CONTENT, value);
        return this;
    }

    public ReviewSelection contentLike(String... value) {
        addLike(ReviewColumns.CONTENT, value);
        return this;
    }

    public ReviewSelection contentContains(String... value) {
        addContains(ReviewColumns.CONTENT, value);
        return this;
    }

    public ReviewSelection contentStartsWith(String... value) {
        addStartsWith(ReviewColumns.CONTENT, value);
        return this;
    }

    public ReviewSelection contentEndsWith(String... value) {
        addEndsWith(ReviewColumns.CONTENT, value);
        return this;
    }

    public ReviewSelection orderByContent(boolean desc) {
        orderBy(ReviewColumns.CONTENT, desc);
        return this;
    }

    public ReviewSelection orderByContent() {
        orderBy(ReviewColumns.CONTENT, false);
        return this;
    }

    public ReviewSelection author(String... value) {
        addEquals(ReviewColumns.AUTHOR, value);
        return this;
    }

    public ReviewSelection authorNot(String... value) {
        addNotEquals(ReviewColumns.AUTHOR, value);
        return this;
    }

    public ReviewSelection authorLike(String... value) {
        addLike(ReviewColumns.AUTHOR, value);
        return this;
    }

    public ReviewSelection authorContains(String... value) {
        addContains(ReviewColumns.AUTHOR, value);
        return this;
    }

    public ReviewSelection authorStartsWith(String... value) {
        addStartsWith(ReviewColumns.AUTHOR, value);
        return this;
    }

    public ReviewSelection authorEndsWith(String... value) {
        addEndsWith(ReviewColumns.AUTHOR, value);
        return this;
    }

    public ReviewSelection orderByAuthor(boolean desc) {
        orderBy(ReviewColumns.AUTHOR, desc);
        return this;
    }

    public ReviewSelection orderByAuthor() {
        orderBy(ReviewColumns.AUTHOR, false);
        return this;
    }
}
