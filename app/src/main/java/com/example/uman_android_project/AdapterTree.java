package com.example.uman_android_project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterTree extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView first, last, born;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.first_info);
            last = itemView.findViewById(R.id.last_info);
            born = itemView.findViewById(R.id.born_info);
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
        ((MyViewHolder) holder).first.setText(data.get(position).getFirst());
        Log.d("TAG", "onBindViewHolder: first" + data.get(position).getFirst());
        ((MyViewHolder) holder).last.setText(data.get(position).getLast());
        Log.d("TAG", "onBindViewHolder: last" + data.get(position).getLast());
        ((MyViewHolder) holder).born.setText(data.get(position).getBorn());
        Log.d("TAG", "onBindViewHolder: born" + data.get(position).getBorn());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
