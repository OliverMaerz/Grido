package com.olivermaerz.grido.provider.favorite;

import android.net.Uri;
import android.provider.BaseColumns;

import com.olivermaerz.grido.provider.FavoritesProvider;
import com.olivermaerz.grido.provider.favorite.FavoriteColumns;
import com.olivermaerz.grido.provider.review.ReviewColumns;
import com.olivermaerz.grido.provider.trailer.TrailerColumns;

/**
 * Favorite movies selected by the user.
 */
public class FavoriteColumns implements BaseColumns {
    public static final String TABLE_NAME = "favorite";
    public static final Uri CONTENT_URI = Uri.parse(FavoritesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Title of the movie
     */
    public static final String ORIGINALTITLE = "originalTitle";

    /**
     * Rating of the movie 0-10
     */
    public static final String RATED = "rated";

    /**
     * Release date of the movie
     */
    public static final String RELEASEDATE = "releaseDate";

    /**
     * Description of the movie
     */
    public static final String DESCRIPTION = "description";

    /**
     * Movie Poster
     */
    public static final String POSTER = "poster";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ORIGINALTITLE,
            RATED,
            RELEASEDATE,
            DESCRIPTION,
            POSTER
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(ORIGINALTITLE) || c.contains("." + ORIGINALTITLE)) return true;
            if (c.equals(RATED) || c.contains("." + RATED)) return true;
            if (c.equals(RELEASEDATE) || c.contains("." + RELEASEDATE)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(POSTER) || c.contains("." + POSTER)) return true;
        }
        return false;
    }

}
