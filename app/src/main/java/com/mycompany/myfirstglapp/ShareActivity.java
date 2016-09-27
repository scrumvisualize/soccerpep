package com.mycompany.myfirstglapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;


/**
 * Created by admin on 13/09/2016.
 */
public class ShareActivity extends Activity{


    Button share;
    File imagePath;
    private boolean isUpdate;
    ImageView image;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        share  = (Button)findViewById(R.id.share1);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bitmap bitmap = takeScreenshot();
                //saveBitmap(bitmap);
                shareImage(imagePath);
            }
        });

    }


   public void shareImage(File file){
        Uri uri = Uri.fromFile(file);
       if(uri!=null) {
           Intent intent = new Intent();
           intent.setAction(Intent.ACTION_SEND);
           intent.setType("image/*");

           intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
           intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
           intent.putExtra(Intent.EXTRA_STREAM, uri);
           startActivity(Intent.createChooser(intent, "Share Location"));
           //try {
               //startActivity(Intent.createChooser(intent, "Share Screenshot"));
          // } catch (ActivityNotFoundException e) {
               //Toast.makeText(ShareActivity.this, "No App Available", Toast.LENGTH_SHORT).show();
           //}

       }
    }

}
