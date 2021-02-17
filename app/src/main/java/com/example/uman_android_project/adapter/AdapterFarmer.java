package com.example.uman_android_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uman_android_project.R;
import com.example.uman_android_project.model.Farmer;

import java.util.List;

public class AdapterFarmer extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class farmer_list extends RecyclerView.ViewHolder{

        TextView name;

        public farmer_list(@NonNull View itemView) {
            super(itemView);
            //name = itemView.findViewById(R.id.treeName);
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
        ((AdapterFarmer.farmer_list) holder).name.setText("Name: " );

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
