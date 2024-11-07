package com.example.locationfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private Context context;
    Activity activity;
    private ArrayList id, address, lat, lon;

    int position;

    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList address, ArrayList lat, ArrayList lon){
        this.activity = activity;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position){
        holder.idText.setText(String.valueOf(id.get(position)));
        holder.addressText.setText(String.valueOf(address.get(position)));
        holder.latText.setText(String.valueOf(lat.get(position)));
        holder.lonText.setText(String.valueOf(lon.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("address", String.valueOf(address.get(position)));
                intent.putExtra("latitude", String.valueOf(lat.get(position)));
                intent.putExtra("longitude", String.valueOf(lon.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount(){
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView idText, addressText, latText, lonText;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.idText);
            addressText = itemView.findViewById(R.id.addressText);
            latText = itemView.findViewById(R.id.latText);
            lonText = itemView.findViewById(R.id.lonText);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
