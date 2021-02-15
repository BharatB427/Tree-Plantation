package com.example.uman_android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView photo;
    private TextView name, Lastname;
    private Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        photo = findViewById(R.id.photo);
        name = findViewById(R.id.name);
        Lastname = findViewById(R.id.LastName);

         profile = Profile.getCurrentProfile();
        String Username= profile.getName();
        name.setText(Username);
        String image= profile.getProfilePictureUri(400,400).toString();
        Picasso.get().load(image).into(photo);
        String lastName= profile.getLastName();
        Lastname.setText(lastName);


    }



}