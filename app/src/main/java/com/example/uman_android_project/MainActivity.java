package com.example.uman_android_project;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.example.uman_android_project.fragment.CustomViewPager;
import com.example.uman_android_project.fragment.MainFragmentAdapter;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private final int[] TAB_TITLES = new int[]{R.string.menu_main, R.string.menu_newTree, R.string.menu_newOrder, R.string.menu_profile};
    private final int[] TAB_IMGS = new int[]{R.drawable.home_menu, R.drawable.tree_menu, R.drawable.tree_menu, R.drawable.profile_menu};

    private CustomViewPager viewPager;
    private TabLayout tabLayout;

    private PagerAdapter adapter;

    public static String currentDate;
    public static String gps;

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        initPager();
        setTabs(tabLayout, getLayoutInflater(), TAB_TITLES, TAB_IMGS);
        verifyStoragePermissions(MainActivity.this);
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR);

        //get Facebook profile
        profile = Profile.getCurrentProfile();
        String Username= profile.getName();
        String lastName= profile.getLastName();
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", Username);
        editor.putString("lastName", lastName);
        //check if the farmer is the first time to log in
        Query query = FirebaseFirestore.getInstance()
                .collection("user")
                .whereEqualTo("userName", Username)
                .whereEqualTo("lastName", lastName);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean isExisting = false;
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String uName = ds.getString("userName");
                    String lName = ds.getString("lastName");
                    String userId = ds.getString("userId");
                    if (uName.equals(Username) && lName.equals(lastName)) {
                        isExisting = true;
                        editor.putString("id", userId);
                    }
                }
                if (!isExisting) {
                    String userId = getAccountIdByUUId();
                    editor.putString("id", userId);
                    Map<String, Object> user = new HashMap<>();
                    user.put("userName", Username);
                    user.put("lastName", lastName);
                    user.put("userId", userId);
                    db.collection("tree1")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }
            }
        });
        editor.commit();

        /*LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        });*/
        //Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //locationUpdates(location);

    }

    private void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] tab_titles, int[] tab_imgs) {
        for (int i = 0; i < tab_titles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.item_menu, null);

            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.txt_tab);
            tvTitle.setText(tab_titles[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tab_imgs[i]);
            tabLayout.addTab(tab);
        }
    }

    @Override
    public void onBackPressed() {
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
            Log.i(TAG, "location"+ gps);
        }else {
            gps = "no.......";
        }

    }
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
    public static String getAccountIdByUUId() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {
            hashCodeV = - hashCodeV;
        }
        return String.format("%016d", hashCodeV);
    }

}