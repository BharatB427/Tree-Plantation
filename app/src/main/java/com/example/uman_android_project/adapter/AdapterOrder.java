package com.example.uman_android_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uman_android_project.R;
import com.example.uman_android_project.model.Farmer;
import com.example.uman_android_project.model.Order;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class order_list extends RecyclerView.ViewHolder{

        TextView name;

        public order_list(@NonNull View itemView) {
            super(itemView);
            //name = itemView.findViewById(R.id.treeName);
        }
    }

    private List<Order> data;

    public AdapterOrder(List<Order> data){
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterFarmer.farmer_list(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AdapterOrder.order_list) holder).name.setText("Name: " );

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), );
                Farmer farmer = data.get(position);
                intent.putExtra("farmer", (Serializable)farmer);
                v.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
