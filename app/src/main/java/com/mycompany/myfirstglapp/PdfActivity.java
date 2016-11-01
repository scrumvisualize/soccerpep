package com.mycompany.myfirstglapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by admin on 1/11/2016.
 */

public class PdfActivity extends Activity {
    private SurfaceView surface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        surface = (SurfaceView) findViewById(R.id.pdfSurface);
        showPdf();

    }


    private void showPdf()  {

        //File file = new File("/sdcard/example.pdf");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/LawsofthegamewebEN_Neutral.pdf");

        if (file.exists()) {
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

    }



}




