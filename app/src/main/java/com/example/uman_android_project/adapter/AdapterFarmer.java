package com.example.uman_android_project.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uman_android_project.OrderListOfFarmerActivity;
import com.example.uman_android_project.R;
import com.example.uman_android_project.model.Farmer;

import java.io.Serializable;
import java.util.List;

public class AdapterFarmer extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class farmer_list extends RecyclerView.ViewHolder{

        TextView name;
        //ImageView picture;

        public farmer_list(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            //picture = itemView.findViewById(R.id.picture);
        }
    }

    private List<Farmer> data;

    public AdapterFarmer(List<Farmer> data){
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new farmer_list(LayoutInflater.from(parent.getContext()).inflate(R.layout.farmer_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((farmer_list) holder).name.setText(data.get(position).getUserName());
        //Glide.with(holder.itemView.getContext()).load(data.get(position).getPictureURL()).into(((farmer_list)holder).picture);
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrderListOfFarmerActivity.class);
                Farmer farmer = data.get(position);
                intent.putExtra("farmer", (Serializable)farmer);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
