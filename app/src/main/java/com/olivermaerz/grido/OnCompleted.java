package com.olivermaerz.grido;

import java.util.ArrayList;

/**
 * Created by omaerz on 11/27/15.
 * Interface to allow FetchMovies pass back movieListObj to the caller (MainActivity in this case).
 */
public interface OnCompleted {
    void onTaskCompleted(ArrayList<Movies> movieListObj);
}
