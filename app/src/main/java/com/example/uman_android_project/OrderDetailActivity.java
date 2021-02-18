package com.example.uman_android_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uman_android_project.adapter.AdapterOrder;
import com.example.uman_android_project.model.Farmer;
import com.example.uman_android_project.model.Order;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView owner, area, position, date, comment;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        owner = findViewById(R.id.owner);
        area = findViewById(R.id.treeArea);
        position = findViewById(R.id.treePosition);
        date = findViewById(R.id.plantDate);
        comment = findViewById(R.id.plantComment);
        photo = findViewById(R.id.treePhoto);

        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra("order");


        area.setText(order.getArea());
        position.setText(order.getPosition());
        date.setText(order.getDate());
        comment.setText(order.getComment());
        Glide.with(this).load(order.getPhotoUri()).into(photo);

        Query query = FirebaseFirestore.getInstance()
                .collection("farmer")
                .whereEqualTo("userId", order.getOwner());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TAG", "Error :" + error.getMessage());
                    return;
                }

                List<Farmer> farmerList = value.toObjects(Farmer.class);
                for(Farmer farmer: farmerList){
                    if(farmer.getUserId().equals(order.getOwner())){
                        owner.setText(farmer.getUserName()+" "+farmer.getLastName());
                    }
                }

            }
        });
    }
}