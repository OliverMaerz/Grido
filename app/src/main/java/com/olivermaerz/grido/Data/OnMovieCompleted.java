package com.olivermaerz.grido.data;

/**
 * Created by omaerz on 11/27/15.
 * Interface to allow FetchMovies pass back movieListObj to the caller (MainActivity in this case).
 */
public interface OnMovieCompleted {
    void onMovieDetailTaskCompleted(MdbMovie movie);
}

