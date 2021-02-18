package com.example.uman_android_project.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.uman_android_project.*;
import com.example.uman_android_project.tree.Tree;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.uman_android_project.MainActivity.db;
import static com.example.uman_android_project.MainActivity.storageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "AddTree";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOrderFragment newInstance(String param1, String param2) {
        AddOrderFragment fragment = new AddOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    private EditText areaInput, commentInput ;
    private TextView dateInput,gps_lat,gps_lon;
    private Button chooseDate, addPhoto, submitForm, newOrderButton;
    private String path, imageUri;
    private Uri path_image;
    private ImageView imageView;
    private String orderArea, treePosition, treePlantDate, treePhoto, areaComment;
    public static final int PICK_IMAGE = 1;
    private String imgLat,imgLong;
    private List<String> geoData;
    private LinearLayout orderTable;

    private String userId="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_order, container, false);

        orderTable = view.findViewById(R.id.orderTable);
        orderTable.setVisibility(View.GONE);
        newOrderButton = view.findViewById(R.id.newOrder);
        newOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderTable.setVisibility(View.VISIBLE);
                newOrderButton.setVisibility(View.GONE);
            }
        });

        geoData= new ArrayList<>();

        imageView = view.findViewById(R.id.photo);

        areaInput = view.findViewById(R.id.oderArea);
        commentInput = view.findViewById(R.id.plantComment);
        gps_lon = view.findViewById(R.id.treeGeo_lon);
        gps_lat = view.findViewById(R.id.treeGeo_lat);

        //gps_lon.setText(MainActivity.gps.split(",")[0]);
        //gps_lat.setText(MainActivity.gps.split(",")[1]);


        //treePosition = MainActivity.gps;

        //wait to update after finishing functions
        treePhoto = "";

        dateInput = view.findViewById(R.id.plantDate);
        //dateInput.setText(currentDate);
        //chooseDate = view.findViewById(R.id.chooseDate);
        addPhoto = view.findViewById(R.id.addPhoto);
        submitForm = view.findViewById(R.id.submitForm);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderArea = areaInput.getText().toString();
                areaComment = commentInput.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View viewSubmit = getLayoutInflater().inflate(R.layout.form_confirm, null);
                TextView name_c, date_c;
                name_c = viewSubmit.findViewById(R.id.name);
                date_c = viewSubmit.findViewById(R.id.date);
                name_c.setText("Name: " + orderArea);
                treePosition = geoData.get(0) + " " + geoData.get(1);
                date_c.setText("Date: " + treePlantDate);
                builder.setView(viewSubmit);
                builder.setTitle("Confirm your submit");
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //submit this form
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                        userId = sharedPreferences.getString("id", "not");
                        Tree tree = new Tree(orderArea, treePosition, treePlantDate, areaComment, userId, imageUri);
                        db.collection("tree1").document().set(tree);
                        dialog.cancel();
                        Toast.makeText(getContext(),"Submit successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
                newOrderButton.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            // When an Image is picked
            if (requestCode == 1 && resultCode == -1 && null != data) {
                // Get the Image from data

                path_image= data.getData();
                path = getRealPathFromURI(path_image);
                initialize();

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }
    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor =getActivity().getContentResolver().query(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);}


    public void initialize(){
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        Bitmap bm = BitmapFactory.decodeFile(path);

        imageView.setImageBitmap(bm);

        final StorageReference imageRef = storageReference.child("images/"  + path_image.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(path_image);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Fail to upload to the cloud!",Toast.LENGTH_LONG).show();
            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                Task<Uri> downloadUrl = imageRef.getDownloadUrl();
                downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUri = uri.toString();
                    }
                });
            }
        });

     dateInput.setText(ReadDate(path));
        treePlantDate = dateInput.getText().toString();
    gps_lat.setText(geoData.get(0));
    gps_lon.setText(geoData.get(1));


   // Toast.makeText(getActivity(),ReadExif(path),Toast.LENGTH_LONG).show();
    }
    String ReadDate(String file){
        String exif="";
        try {
            ExifInterface exifInterface = new ExifInterface(file);


            GeoDegree gs= new GeoDegree(exifInterface);
            int Lat=gs.getLatitudeE6();
            imgLat=String.valueOf(Lat);
            int Long=gs.getLongitudeE6();
            imgLong=String.valueOf(Long);

            exif += exifInterface.getAttribute(ExifInterface.TAG_DATETIME);

            geoData = Arrays.asList(gs.toString().split(","));
            Toast.makeText(getActivity(),
                   "Data Successfully loaded from the Image!",
                    Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getActivity(),
                    e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return exif;
    }
    String ReadLat(String file){
        String exif="";
        try {
            ExifInterface exifInterface = new ExifInterface(file);



            exif +=  exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return exif;
    }

    String ReadLong(String file){
        String exif="";
        try {
            ExifInterface exifInterface = new ExifInterface(file);



            exif +=exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getActivity(),
                    e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return exif;
    }


    String ReadExif(String file){
        String exif="Exif: " + file;
        try {
            ExifInterface exifInterface = new ExifInterface(file);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getActivity(),
                    e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return exif;
    }



}