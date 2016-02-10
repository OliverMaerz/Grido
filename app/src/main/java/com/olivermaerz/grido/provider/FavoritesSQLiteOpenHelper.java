package com.olivermaerz.grido.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.olivermaerz.grido.BuildConfig;
import com.olivermaerz.grido.provider.favorite.FavoriteColumns;
import com.olivermaerz.grido.provider.review.ReviewColumns;
import com.olivermaerz.grido.provider.trailer.TrailerColumns;

public class FavoritesSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = FavoritesSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    private static FavoritesSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final FavoritesSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE IF NOT EXISTS "
            + FavoriteColumns.TABLE_NAME + " ( "
            + FavoriteColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavoriteColumns.ORIGINALTITLE + " TEXT NOT NULL, "
            + FavoriteColumns.RATED + " TEXT, "
            + FavoriteColumns.RELEASEDATE + " TEXT, "
            + FavoriteColumns.DESCRIPTION + " TEXT, "
            + FavoriteColumns.POSTER + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_REVIEW = "CREATE TABLE IF NOT EXISTS "
            + ReviewColumns.TABLE_NAME + " ( "
            + ReviewColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ReviewColumns.FAVORITE_ID + " INTEGER NOT NULL, "
            + ReviewColumns.CONTENT + " TEXT NOT NULL, "
            + ReviewColumns.AUTHOR + " TEXT NOT NULL "
            + ", CONSTRAINT fk_favorite_id FOREIGN KEY (" + ReviewColumns.FAVORITE_ID + ") REFERENCES favorite (_id) ON DELETE CASCADE"
            + " );";

    public static final String SQL_CREATE_TABLE_TRAILER = "CREATE TABLE IF NOT EXISTS "
            + TrailerColumns.TABLE_NAME + " ( "
            + TrailerColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TrailerColumns.FAVORITE_ID + " INTEGER NOT NULL, "
            + TrailerColumns.NAME + " TEXT NOT NULL, "
            + TrailerColumns.SOURCE + " TEXT NOT NULL "
            + ", CONSTRAINT fk_favorite_id FOREIGN KEY (" + TrailerColumns.FAVORITE_ID + ") REFERENCES favorite (_id) ON DELETE CASCADE"
            + " );";

    // @formatter:on

    public static FavoritesSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static FavoritesSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static FavoritesSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new FavoritesSQLiteOpenHelper(context);
    }

    private FavoritesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new FavoritesSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static FavoritesSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new FavoritesSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private FavoritesSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new FavoritesSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
        db.execSQL(SQL_CREATE_TABLE_REVIEW);
        db.execSQL(SQL_CREATE_TABLE_TRAILER);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
