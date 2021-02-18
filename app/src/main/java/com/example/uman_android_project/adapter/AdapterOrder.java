package com.example.uman_android_project.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uman_android_project.OrderDetailActivity;
import com.example.uman_android_project.R;
import com.example.uman_android_project.model.Farmer;
import com.example.uman_android_project.model.Order;

import java.io.Serializable;
import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class order_list extends RecyclerView.ViewHolder{

        public TextView area, date, num;

        public order_list(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            area = itemView.findViewById(R.id.area);
            num = itemView.findViewById(R.id.num);
        }
    }

    private List<Order> data;

    public AdapterOrder(List<Order> data){
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new order_list(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((order_list) holder).date.setText(data.get(position).getDate());
        ((order_list) holder).area.setText(data.get(position).getArea());
        ((order_list) holder).num.setText((position+1) + ": ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
                Order order = data.get(position);
                intent.putExtra("order", (Serializable)order);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
