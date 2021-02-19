package com.example.uman_android_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.uman_android_project.adapter.AdapterOrder;
import com.example.uman_android_project.model.Farmer;
import com.example.uman_android_project.model.Order;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class OrderListOfFarmerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_of_farmer);

        recyclerView = findViewById(R.id.recycleList);
        title = findViewById(R.id.farmer);

        Intent intent = getIntent();
        Farmer farmer = (Farmer) intent.getSerializableExtra("farmer");
        title.setText(farmer.getUserName()+"'s orders");

        Query query = FirebaseFirestore.getInstance()
                .collection("tree")
                .whereEqualTo("owner", farmer.getUserId());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TAG", "Error :" + error.getMessage());
                    return;
                }

                List<Order> listOrder = value.toObjects(Order.class);
                AdapterOrder adapterOrder = new AdapterOrder(listOrder);
                recyclerView.setLayoutManager(new LinearLayoutManager(OrderListOfFarmerActivity.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(OrderListOfFarmerActivity.this, DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(adapterOrder);

            }
        });

    }
}