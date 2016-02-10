package com.olivermaerz.grido.provider.trailer;

import android.net.Uri;
import android.provider.BaseColumns;

import com.olivermaerz.grido.provider.FavoritesProvider;
import com.olivermaerz.grido.provider.favorite.FavoriteColumns;
import com.olivermaerz.grido.provider.review.ReviewColumns;
import com.olivermaerz.grido.provider.trailer.TrailerColumns;

/**
 * Movie trailers
 */
public class TrailerColumns implements BaseColumns {
    public static final String TABLE_NAME = "trailer";
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
     * Name of the trailer
     */
    public static final String NAME = "name";

    /**
     * Youtube URL
     */
    public static final String SOURCE = "source";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            FAVORITE_ID,
            NAME,
            SOURCE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(FAVORITE_ID) || c.contains("." + FAVORITE_ID)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(SOURCE) || c.contains("." + SOURCE)) return true;
        }
        return false;
    }

    public static final String PREFIX_FAVORITE = TABLE_NAME + "__" + FavoriteColumns.TABLE_NAME;
}
