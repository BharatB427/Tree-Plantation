package com.example.uman_android_project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.uman_android_project.fragment.CustomViewPager;
import com.example.uman_android_project.fragment.MainFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final int[] TAB_TITLES = new int[]{R.string.menu_main, R.string.menu_newTree, R.string.menu_profile};
    private final int[] TAB_IMGS = new int[]{};

    private CustomViewPager viewPager;
    private TabLayout tabLayout;

    private PagerAdapter adapter;

    public static String currentDate;
    public static String gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        initPager();
        setTabs(tabLayout, getLayoutInflater(), TAB_TITLES, TAB_IMGS);

        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        gps = provider;
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

    private void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] tab_titles, int[] tab_imgs) {
        for (int i = 0; i < tab_titles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.item_menu, null);

            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.txt_tab);
            tvTitle.setText(tab_titles[i]);
            //ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            //imgTab.setImageResource(tab_imgs[i]);
            tabLayout.addTab(tab);
        }
    }

    private void initPager() {
        adapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
            gps = stringBuffer.toString();
        }else {
            gps = "no.......";
        }

    }
}