package com.mycompany.myfirstglapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;


/**
 * Created by admin on 21/09/2016.
 */
public class PlayYoutubeActivity extends Activity {
    private SurfaceView surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer);
        surface = (SurfaceView) findViewById(R.id.idSurface);
        watchYoutubeVideo(video_id);

    }

    String video_id = "3rE4sHJxdBg";

    public void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+video_id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://m.youtube.com/watch?v="+video_id));
        appIntent.putExtra("VIDEO_ID", video_id);
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }


}
