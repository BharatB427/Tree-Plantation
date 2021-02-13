package com.example.uman_android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private ImageView photo;
    private TextView name, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        photo = findViewById(R.id.photo);
        name = findViewById(R.id.name);
        city = findViewById(R.id.city);
    }
}