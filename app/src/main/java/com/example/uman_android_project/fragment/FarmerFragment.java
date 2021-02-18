package com.example.uman_android_project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uman_android_project.R;
import com.example.uman_android_project.adapter.AdapterFarmer;
import com.example.uman_android_project.model.Farmer;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FarmerFragment extends Fragment {

    public FarmerFragment() {
        // Required empty public constructor
    }

    public static FarmerFragment newInstance(String param1, String param2) {
        FarmerFragment fragment = new FarmerFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private RecyclerView recyclerView;
    private List<Farmer> farmerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_farmer, container, false);

        recyclerView = view.findViewById(R.id.recycleList);

        Query query = FirebaseFirestore.getInstance()
                .collection("farmer");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TAG", "Error :" + error.getMessage());
                    return;
                }

                List<Farmer> listFarmer = value.toObjects(Farmer.class);
                AdapterFarmer adapterFarmer = new AdapterFarmer(listFarmer);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(adapterFarmer);

            }
        });



        return view;
    }

}