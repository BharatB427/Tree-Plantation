package com.example.uman_android_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FormSubmitActivity extends AppCompatActivity {

    private static final String TAG = "FormActivity";
    private TextView first, last, born;
    private RecyclerView recyclerView;
    private List<Tree> listTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_submit);

        recyclerView = findViewById(R.id.recycleview);

        Query query = FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("owner", "me");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TAG", "Error :" + error.getMessage());
                    return;
                }
                List<Tree> listTree = value.toObjects(Tree.class);
                AdapterTree adapterTree = new AdapterTree(listTree);
                recyclerView.setLayoutManager(new LinearLayoutManager(FormSubmitActivity.this));
                recyclerView.setAdapter(adapterTree);
            }
        });
    }

}