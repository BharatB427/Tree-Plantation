package com.example.uman_android_project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText name, size, category, gps, date, comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameInput);
        size = findViewById(R.id.sizeInput);
        category = findViewById(R.id.categoryInput);
        gps = findViewById(R.id.gpsInput);
        date = findViewById(R.id.dateInput);
        comment = findViewById(R.id.commentInput);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        gps.setText(provider);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        });
        Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationUpdates(location);
    }

    public void locationUpdates(Location location){
        DecimalFormat df = new DecimalFormat("#.00");
        if(location!=null){
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append("longitude: ");
            String longtitude = df.format(location.getLongitude());
            if (longtitude.contains("-")) {
                stringBuffer.append(longtitude.substring(1));
                stringBuffer.append("S");
            }
            else {
                stringBuffer.append(longtitude);
                stringBuffer.append("N");
            }
            stringBuffer.append(" ,latitude: ");
            String latitude = df.format(location.getLatitude());
            Log.i(TAG, "locationUpdates: " + location.getLatitude());
            if (latitude.contains("-")) {
                stringBuffer.append(latitude.substring(1));
                stringBuffer.append("W");
            }
            else {
                stringBuffer.append(latitude);
                stringBuffer.append("E");
            }
            Log.i(TAG, "locationUpdates: " + location.getLongitude());
            gps.setText(stringBuffer.toString());
        }else {
            gps.setText("no.......");
        }

    }

    public void submit(View view) {
        String namestr = name.getText().toString();
        String sizestr = size.getText().toString();
        String categorystr = category.getText().toString();
        String gpsstr = gps.getText().toString();
        String datestr = date.getText().toString();
        String commentstr = comment.getText().toString();

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", "user1");
        editor.commit();
        String userid = sharedPreferences.getString("id", "notExist");


        Tree tree = new Tree(namestr, sizestr, categorystr, gpsstr, datestr, commentstr, userid);

        // Add a new document with a generated ID
        db.collection("users").document().set(tree);
        Intent intent = new Intent(MainActivity.this, FormSubmitActivity.class);
        startActivity(intent);
    }
}