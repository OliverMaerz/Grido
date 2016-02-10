package com.olivermaerz.grido.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.olivermaerz.grido.BuildConfig;
import com.olivermaerz.grido.provider.base.BaseContentProvider;
import com.olivermaerz.grido.provider.favorite.FavoriteColumns;
import com.olivermaerz.grido.provider.review.ReviewColumns;
import com.olivermaerz.grido.provider.trailer.TrailerColumns;

import java.util.Arrays;

public class FavoritesProvider extends BaseContentProvider {
    private static final String TAG = FavoritesProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.olivermaerz.grido";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_FAVORITE = 0;
    private static final int URI_TYPE_FAVORITE_ID = 1;

    private static final int URI_TYPE_REVIEW = 2;
    private static final int URI_TYPE_REVIEW_ID = 3;

    private static final int URI_TYPE_TRAILER = 4;
    private static final int URI_TYPE_TRAILER_ID = 5;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, FavoriteColumns.TABLE_NAME, URI_TYPE_FAVORITE);
        URI_MATCHER.addURI(AUTHORITY, FavoriteColumns.TABLE_NAME + "/#", URI_TYPE_FAVORITE_ID);
        URI_MATCHER.addURI(AUTHORITY, ReviewColumns.TABLE_NAME, URI_TYPE_REVIEW);
        URI_MATCHER.addURI(AUTHORITY, ReviewColumns.TABLE_NAME + "/#", URI_TYPE_REVIEW_ID);
        URI_MATCHER.addURI(AUTHORITY, TrailerColumns.TABLE_NAME, URI_TYPE_TRAILER);
        URI_MATCHER.addURI(AUTHORITY, TrailerColumns.TABLE_NAME + "/#", URI_TYPE_TRAILER_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return FavoritesSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_FAVORITE:
                return TYPE_CURSOR_DIR + FavoriteColumns.TABLE_NAME;
            case URI_TYPE_FAVORITE_ID:
                return TYPE_CURSOR_ITEM + FavoriteColumns.TABLE_NAME;

            case URI_TYPE_REVIEW:
                return TYPE_CURSOR_DIR + ReviewColumns.TABLE_NAME;
            case URI_TYPE_REVIEW_ID:
                return TYPE_CURSOR_ITEM + ReviewColumns.TABLE_NAME;

            case URI_TYPE_TRAILER:
                return TYPE_CURSOR_DIR + TrailerColumns.TABLE_NAME;
            case URI_TYPE_TRAILER_ID:
                return TYPE_CURSOR_ITEM + TrailerColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_FAVORITE:
            case URI_TYPE_FAVORITE_ID:
                res.table = FavoriteColumns.TABLE_NAME;
                res.idColumn = FavoriteColumns._ID;
                res.tablesWithJoins = FavoriteColumns.TABLE_NAME;
                res.orderBy = FavoriteColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_REVIEW:
            case URI_TYPE_REVIEW_ID:
                res.table = ReviewColumns.TABLE_NAME;
                res.idColumn = ReviewColumns._ID;
                res.tablesWithJoins = ReviewColumns.TABLE_NAME;
                if (FavoriteColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + FavoriteColumns.TABLE_NAME + " AS " + ReviewColumns.PREFIX_FAVORITE + " ON " + ReviewColumns.TABLE_NAME + "." + ReviewColumns.FAVORITE_ID + "=" + ReviewColumns.PREFIX_FAVORITE + "." + FavoriteColumns._ID;
                }
                res.orderBy = ReviewColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TRAILER:
            case URI_TYPE_TRAILER_ID:
                res.table = TrailerColumns.TABLE_NAME;
                res.idColumn = TrailerColumns._ID;
                res.tablesWithJoins = TrailerColumns.TABLE_NAME;
                if (FavoriteColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + FavoriteColumns.TABLE_NAME + " AS " + TrailerColumns.PREFIX_FAVORITE + " ON " + TrailerColumns.TABLE_NAME + "." + TrailerColumns.FAVORITE_ID + "=" + TrailerColumns.PREFIX_FAVORITE + "." + FavoriteColumns._ID;
                }
                res.orderBy = TrailerColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_FAVORITE_ID:
            case URI_TYPE_REVIEW_ID:
            case URI_TYPE_TRAILER_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
