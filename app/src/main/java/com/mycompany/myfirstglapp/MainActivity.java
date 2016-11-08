package com.mycompany.myfirstglapp;


import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.geocoder.ui.GeocoderAutoCompleteView;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.geocoding.v5.GeocodingCriteria;
import com.mapbox.services.geocoding.v5.models.CarmenFeature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private MapView mapView;
    private MapboxMap map;
    Button myButton;
    Button share;
    Button videoCapture;
    protected LocationManager locationManager;
    private Marker customMarker;
    private Marker userMarker;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    File imagePath;
    private boolean isUpdate;
    ImageView image;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

        MapboxAccountManager.start(this, "pk.eyJ1Ijoic2NydW12aXN1YWxpemUiLCJhIjoiNGE3NGVjNjAyM2Y1MGE3NDQ4OTc2NmI3M2E1Y2UwYTEifQ.WwFcNIwdabPx_irzZ3jDiA");
        final boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        share  = (Button) findViewById(R.id.share1);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        onMapReady(map);
        addDrawerItems();
        setupDrawer();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mapView = (MapView) findViewById(R.id.mapview);
        myButton = (Button) findViewById(R.id.locate1);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                Log.i("MapAsync", " is called");
                //you need to initialize 'map' with 'mapboxMap';
                map = mapboxMap;
                //map.setOnMapLongClickListener(new LatLng);

                map.setOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng point) {
                        if (customMarker != null) {
                            // Remove previous added marker
                            map.removeAnnotation(customMarker);
                            customMarker = null;
                        }
                        customMarker = map.addMarker(new MarkerOptions()
                                .title("Custom Marker")
                                .snippet(new DecimalFormat("#.#####").format(point.getLatitude()) + ", "
                                        + new DecimalFormat("#.#####").format(point.getLongitude()))
                                .position(point));

                    }
                }); // Long click Ends here


            }

        });


        final GPSTracker gps = new GPSTracker(MainActivity.this);
        // show location button click event
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (permissionGranted){
                    // check if GPS is enabled
                    if (gps.canGetLocation()) {

                        if (userMarker != null) {
                            // Remove previous added marker
                            map.removeAnnotation(userMarker);
                            userMarker = null;
                        }

                        gps.location = gps.getLocation();

                        final double latitude = gps.getLatitude();
                        final double longitude = gps.getLongitude();
                        //mapView.getMapAsync(new OnMapReadyCallback() {
                            //@Override
                            //public void onMapReady(MapboxMap mapboxMap) {
                        IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);
                        Drawable iconDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.vinod_1);
                        Icon icon = iconFactory.fromDrawable(iconDrawable);
                        userMarker = map.addMarker(new MarkerOptions()
                                        .position(new LatLng(gps.location.getLatitude(), gps.location.getLongitude()))
                                        .title("Hello Vinod !")
                                        .snippet("Welcome to mapbox")
                                        .icon(icon));
                        //Adding the camera here as suggeted
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(gps.location.getLatitude(), gps.location.getLongitude()))      // Sets the center of the map to Mountain View
                                .zoom(12)                   // Sets the zoom
                                .bearing(90)                // Sets the orientation of the camera to east
                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                .build();                   // Creates a CameraPosition from the builder

                                //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);

                        // }

                        //});
                        //  \n is for new line
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }

                }else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                }


            }


        });


        share = (Button) findViewById(R.id.share1);
       //final ShareActivity shareactivity = new ShareActivity();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.snapshot(new MapboxMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap snapshot) {
                        ImageView snapshotView = (ImageView) findViewById(R.id.screenshots);
                        if(snapshotView!=null) {
                            snapshotView.setImageBitmap(snapshot);
                            //Bitmap bitmap = shareactivity.takeScreenshot();
                            saveBitmap(snapshot);
                            shareImage(imagePath);

                        }
                    }
                });
            }


        });
        videoCapture = (Button) findViewById(R.id.camera_button);
        videoCapture.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                Toast.makeText(MainActivity.this, "Recording Started", Toast.LENGTH_SHORT).show();



            }});

        // Set up autocomplete widget and GeoCoder search in Mapbox map
        GeocoderAutoCompleteView autocomplete = (GeocoderAutoCompleteView) findViewById(R.id.query);
        autocomplete.setAccessToken(MapboxAccountManager.getInstance().getAccessToken());
        autocomplete.setType(GeocodingCriteria.TYPE_POI);
        autocomplete.setOnFeatureListener(new GeocoderAutoCompleteView.OnFeatureListener() {
            @Override
            public void OnFeatureClick(CarmenFeature feature) {
                Position position = feature.asPosition();
                updateMap(position.getLatitude(), position.getLongitude());
            }
        });


    }

    private void updateMap(double latitude, double longitude) {
        // Build marker
        map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("Cool, your searched place !"));

        // Animate camera to geocoder result location
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(12)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000, null);
    }

    public void saveBitmap(Bitmap bitmap) {
        verifyStoragePermissions(MainActivity.this);
        imagePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
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
//            try {
//                startActivity(Intent.createChooser(intent, "Share Screenshot"));
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(MainActivity.this, "No App Available", Toast.LENGTH_SHORT).show();
//            }

        }
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

// Initialize the onMapReady
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.map = mapboxMap;


    }



    private void addDrawerItems() {
        String[] osArray = { "Map", "Players", "Video", "My Profile", "Soccer Rules", "Test Screen"};
        //int ICONS[] = {R.drawable.ic_home,R.drawable.ic_events,R.drawable.ic_mail,R.drawable.ic_shop,R.drawable.ic_travel};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // depending on the position in your drawer list change this
                switch (position) {
                    case 0: {

                        Toast.makeText(MainActivity.this, "Access map", Toast.LENGTH_SHORT).show();
                        break;

                    }
                    case 1:{
                        Intent intent = new Intent(MainActivity.this, ListPlayerActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "See players arena", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case 2:{

                        Intent appIntent = new Intent(MainActivity.this, PlayYoutubeActivity.class);
                        startActivity(appIntent);
                        Toast.makeText(MainActivity.this, "See Video", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case 3:{

                        Intent appIntent = new Intent(MainActivity.this, MyProfileDialog.class);
                        startActivity(appIntent);
                        Toast.makeText(MainActivity.this, "My Profile", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case 4:{

                        Intent appIntent = new Intent(MainActivity.this, PdfActivity.class);
                        startActivity(appIntent);
                        Toast.makeText(MainActivity.this, "Soccer Law", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    default:
                        break;

                }
                Toast.makeText(MainActivity.this, "More details to follow", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


   

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    public class TelemetryServiceNotConfiguredException extends RuntimeException {

        public TelemetryServiceNotConfiguredException() {
            super("\nTelemetryService is not configured in your applications AndroidManifest.xml. " +
                    "\nPlease add \"com.mapbox.mapboxsdk.telemetry.TelemetryService\" service in your applications AndroidManifest.xml" +
                    "\nFor an example visit http://goo.gl/cET0Jn. For more information visit https://www.mapbox.com/android-sdk/.");
        }

    }

}



