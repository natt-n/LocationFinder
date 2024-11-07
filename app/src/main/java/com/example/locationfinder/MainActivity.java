package com.example.locationfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_btn;
    TextInputEditText searchBar;

    MyDBHelper mydb;
    ArrayList<String> id, address, lat, lon;
    ArrayList<String> filteredId, filteredAddress, filteredLat, filteredLon;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        recyclerView = findViewById(R.id.recyclerView);
        add_btn = findViewById(R.id.addBtn);
        searchBar = findViewById(R.id.searchBar);

        // Set up the "Add" button action
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent); // Open AddActivity
            }
        });

        // Initialize the database helper and data lists
        mydb = new MyDBHelper(MainActivity.this);
        id = new ArrayList<>();
        address = new ArrayList<>();
        lat = new ArrayList<>();
        lon = new ArrayList<>();

        filteredId = new ArrayList<>();
        filteredAddress = new ArrayList<>();
        filteredLat = new ArrayList<>();
        filteredLon = new ArrayList<>();

        // Store data from the database
        storeData();

        // Set up RecyclerView with custom adapter
        customAdapter = new CustomAdapter(MainActivity.this, this, filteredId, filteredAddress, filteredLat, filteredLon);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Set up search functionality
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        storeData(); // Refresh the data from the database
        customAdapter.notifyDataSetChanged(); // Update the RecyclerView with the new data
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            recreate();
        }
    }

    void storeData() {
        // Clear existing data to avoid duplication
        id.clear();
        address.clear();
        lat.clear();
        lon.clear();

        // Query the database and add the data to the lists
        Cursor cursor = mydb.readAllData();
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0)); // ID
                address.add(cursor.getString(1)); // Address
                lat.add(cursor.getString(2)); // Latitude
                lon.add(cursor.getString(3)); // Longitude
            }
        }

        // Close cursor to release resources
        if (cursor != null) {
            cursor.close();
        }

        // Initialize filtered lists with all data
        filteredId.clear();
        filteredAddress.clear();
        filteredLat.clear();
        filteredLon.clear();

        filteredId.addAll(id);
        filteredAddress.addAll(address);
        filteredLat.addAll(lat);
        filteredLon.addAll(lon);
    }

    void filterData(String query) {
        filteredId.clear();
        filteredAddress.clear();
        filteredLat.clear();
        filteredLon.clear();

        // Filter data based on the query and add to filtered lists
        for (int i = 0; i < address.size(); i++) {
            if (address.get(i).toLowerCase().contains(query.toLowerCase())) {
                filteredId.add(id.get(i));
                filteredAddress.add(address.get(i));
                filteredLat.add(lat.get(i));
                filteredLon.add(lon.get(i));
            }
        }
        customAdapter.notifyDataSetChanged(); // Notify the adapter that data has changed
    }
}
