package com.olivermaerz.grido.provider.trailer;

import com.olivermaerz.grido.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Movie trailers
 */
public interface TrailerModel extends BaseModel {

    /**
     * The foreign key _id of the favorite movie
     */
    long getFavoriteId();

    /**
     * Name of the trailer
     * Cannot be {@code null}.
     */
    @NonNull
    String getName();

    /**
     * Youtube URL
     * Cannot be {@code null}.
     */
    @NonNull
    String getSource();
}
