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
        public TextView name, category, size, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.treeName);
            date = itemView.findViewById(R.id.date);
            category = itemView.findViewById(R.id.treeCategory);
            size = itemView.findViewById(R.id.treeSize);
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
        ((MyViewHolder) holder).name.setText("Name: " + data.get(position).getName());
        Log.d("TAG", "onBindViewHolder: name" + data.get(position).getName());
        ((MyViewHolder) holder).date.setText("Date: " + data.get(position).getDate());
        Log.d("TAG", "onBindViewHolder: date" + data.get(position).getDate());
        ((MyViewHolder) holder).category.setText("Category: " + data.get(position).getCategory());
        Log.d("TAG", "onBindViewHolder: cate" + data.get(position).getCategory());
        ((MyViewHolder) holder).size.setText("Size: " + data.get(position).getSize());
        Log.d("TAG", "onBindViewHolder: size" + data.get(position).getSize());

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
