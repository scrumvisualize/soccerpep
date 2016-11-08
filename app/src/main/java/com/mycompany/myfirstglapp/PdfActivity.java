package com.mycompany.myfirstglapp;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by admin on 1/11/2016.
 */

public class PdfActivity extends Activity {
    private SurfaceView surface;
    Button pdfButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        surface = (SurfaceView) findViewById(R.id.pdfSurface);
        pdfButton = (Button) findViewById(R.id.pdfView1);

        pdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PdfActivity.this, "test", Toast.LENGTH_SHORT).show();

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "LawsofthegamewebEN_Neutral.pdf");
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(PdfActivity.this,
                            "No Application Available to View PDF",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });



    }



}




