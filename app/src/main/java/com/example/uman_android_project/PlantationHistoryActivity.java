package com.example.uman_android_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.uman_android_project.tree.AdapterTree;
import com.example.uman_android_project.tree.Tree;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PlantationHistoryActivity extends AppCompatActivity {

    private List<Tree> listTree = new ArrayList<Tree>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_history);

        recyclerView = findViewById(R.id.recyclerView);

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "notExist");

        Query query = FirebaseFirestore.getInstance()
                .collection("tree1")
                .whereEqualTo("owner", userId);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TAG", "Error :" + error.getMessage());
                    return;
                }

                List<Tree> listTree = value.toObjects(Tree.class);
                AdapterTree adapterTree = new AdapterTree(listTree);
                recyclerView.setLayoutManager(new LinearLayoutManager(PlantationHistoryActivity.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(PlantationHistoryActivity.this, DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(adapterTree);

            }
        });
    }
}