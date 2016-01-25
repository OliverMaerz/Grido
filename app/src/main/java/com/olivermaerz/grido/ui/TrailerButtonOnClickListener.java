package com.olivermaerz.grido.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

/**
 * Package: com.olivermaerz.grido
 * Created by omaerz on 1/24/16.
 */
public class TrailerButtonOnClickListener implements View.OnClickListener
{

    private String youtubeVideoId;
    private Context context;

    public TrailerButtonOnClickListener(Context context, String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        // start Youtube intent for trailer
        this.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + this.youtubeVideoId)));
    }

};
