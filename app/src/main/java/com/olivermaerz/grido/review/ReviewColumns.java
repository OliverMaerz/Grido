package com.olivermaerz.grido.review;

import android.net.Uri;
import android.provider.BaseColumns;

import com.olivermaerz.grido.FavoritesProvider;
import com.olivermaerz.grido.favorite.FavoriteColumns;
import com.olivermaerz.grido.review.ReviewColumns;
import com.olivermaerz.grido.trailer.TrailerColumns;

/**
 * Favorite movies selected by the user.
 */
public class ReviewColumns implements BaseColumns {
    public static final String TABLE_NAME = "review";
    public static final Uri CONTENT_URI = Uri.parse(FavoritesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * The foreign key _id of the favorite movie
     */
    public static final String FAVORITE_ID = "favorite_id";

    /**
     * Reveiw of the movie
     */
    public static final String CONTENT = "content";

    /**
     * Author of the review
     */
    public static final String AUTHOR = "author";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            FAVORITE_ID,
            CONTENT,
            AUTHOR
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(FAVORITE_ID) || c.contains("." + FAVORITE_ID)) return true;
            if (c.equals(CONTENT) || c.contains("." + CONTENT)) return true;
            if (c.equals(AUTHOR) || c.contains("." + AUTHOR)) return true;
        }
        return false;
    }

    public static final String PREFIX_FAVORITE = TABLE_NAME + "__" + FavoriteColumns.TABLE_NAME;
}
