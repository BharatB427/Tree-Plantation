package com.example.uman_android_project.tree;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uman_android_project.PlantationDetailActivity;
import com.example.uman_android_project.R;

import java.io.Serializable;
import java.util.List;

public class AdapterTree extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, area, treePosition, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.treeId);
            date = itemView.findViewById(R.id.date);
            area = itemView.findViewById(R.id.treeArea);
            treePosition = itemView.findViewById(R.id.treePosition);
        }
    }

    private List<Tree> data;

    public AdapterTree(List<Tree> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_info_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).id.setText("Name: " + data.get(position).getArea());
        Log.d("TAG", "onBindViewHolder: id" + data.get(position).getArea());
        ((MyViewHolder) holder).date.setText("Date: " + data.get(position).getDate());
        Log.d("TAG", "onBindViewHolder: date" + data.get(position).getDate());
        ((MyViewHolder) holder).area.setText("Area: " + data.get(position).getArea());
        Log.d("TAG", "onBindViewHolder: area" + data.get(position).getArea());
        ((MyViewHolder) holder).treePosition.setText("Position: " + data.get(position).getPosition());
        Log.d("TAG", "onBindViewHolder: position" + data.get(position).getPosition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlantationDetailActivity.class);
                Tree tree = data.get(position);
                intent.putExtra("tree", (Serializable)tree);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
