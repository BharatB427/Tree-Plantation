package com.example.uman_android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uman_android_project.tree.Tree;

public class PlantationDetailActivity extends AppCompatActivity {

    private TextView name, category, size, geo, date, comment;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_detail);

        name = findViewById(R.id.treeName);
        category = findViewById(R.id.treeCategory);
        size = findViewById(R.id.treeSize);
        geo = findViewById(R.id.treeGeo);
        date = findViewById(R.id.plantDate);
        comment = findViewById(R.id.plantComment);
        photo = findViewById(R.id.treePhoto);

        Intent intent = getIntent();
        Tree tree = (Tree) intent.getSerializableExtra("tree");

        name.setText(tree.getName());
        category.setText(tree.getCategory());
        size.setText(tree.getSize());
        geo.setText(tree.getGps());
        date.setText(tree.getDate());
        comment.setText(tree.getComment());
    }
}