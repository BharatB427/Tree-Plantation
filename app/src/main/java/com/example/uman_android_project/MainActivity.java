package com.example.uman_android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText first, last, born;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        first = findViewById(R.id.firstInput);
        last = findViewById(R.id.lastInput);
        born = findViewById(R.id.bornInput);

    }

    public void submit(View view) {
        String firststr = first.getText().toString();
        String laststr = last.getText().toString();
        String bornstr = born.getText().toString();

        Tree tree = new Tree(firststr, laststr, bornstr, "me");

        // Add a new document with a generated ID
        db.collection("users").document().set(tree);
        Intent intent = new Intent(MainActivity.this, FormSubmitActivity.class);
        startActivity(intent);
    }
}