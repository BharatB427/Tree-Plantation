package com.example.uman_android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uman_android_project.tree.Tree;

public class PlantationDetailActivity extends AppCompatActivity {

    private TextView id, area, position, date, comment;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_detail);

        id = findViewById(R.id.treeId);
        area = findViewById(R.id.treeArea);
        position = findViewById(R.id.treePosition);
        date = findViewById(R.id.plantDate);
        comment = findViewById(R.id.plantComment);
        photo = findViewById(R.id.treePhoto);

        Intent intent = getIntent();
        Tree tree = (Tree) intent.getSerializableExtra("tree");

        id.setText(tree.getId());
        area.setText(tree.getArea());
        position.setText(tree.getPosition());
        date.setText(tree.getDate());
        comment.setText(tree.getComment());
        Glide.with(this).load(tree.getPhotoUri()).into(photo);
    }
}