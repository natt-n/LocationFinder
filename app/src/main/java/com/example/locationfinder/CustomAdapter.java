package com.example.locationfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    Context context;
    private ArrayList id, address, lat, lon;

    CustomAdapter(Context context, ArrayList id, ArrayList address, ArrayList lat, ArrayList lon){
        this.context = context;
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.location_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        holder.idText.setText(String.valueOf(id.get(position)));
        holder.addressText.setText(String.valueOf(address.get(position)));
        holder.latText.setText(String.valueOf(lat.get(position)));
        holder.lonText.setText(String.valueOf(lon.get(position)));
    }

    @Override
    public int getItemCount(){
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView idText, addressText, latText, lonText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.idText);
            addressText = itemView.findViewById(R.id.addressText);
            latText = itemView.findViewById(R.id.latText);
            lonText = itemView.findViewById(R.id.lonText);
        }
    }
}
