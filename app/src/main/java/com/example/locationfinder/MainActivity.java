package com.example.locationfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

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

        recyclerView = findViewById(R.id.recyclerView);
        add_btn = findViewById(R.id.addBtn);
        searchBar = findViewById(R.id.searchBar);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent); // Open AddActivity
            }
        });

        mydb = new MyDBHelper(MainActivity.this);
        id = new ArrayList<>();
        address = new ArrayList<>();
        lat = new ArrayList<>();
        lon = new ArrayList<>();

        filteredId = new ArrayList<>();
        filteredAddress = new ArrayList<>();
        filteredLat = new ArrayList<>();
        filteredLon = new ArrayList<>();

        storeData();

        customAdapter = new CustomAdapter(MainActivity.this, filteredId, filteredAddress, filteredLat, filteredLon);
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

    void storeData() {
        // Clear existing data to avoid duplication
        id.clear();
        address.clear();
        lat.clear();
        lon.clear();

        Cursor cursor = mydb.readAllData();
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                address.add(cursor.getString(1));
                lat.add(cursor.getString(2));
                lon.add(cursor.getString(3));
            }
        }

        // Close cursor to release resources
        if (cursor != null) {
            cursor.close();
        }

        // Initialize filtered lists with all data
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

        for (int i = 0; i < address.size(); i++) {
            if (address.get(i).toLowerCase().contains(query.toLowerCase())) {
                filteredId.add(id.get(i));
                filteredAddress.add(address.get(i));
                filteredLat.add(lat.get(i));
                filteredLon.add(lon.get(i));
            }
        }
        customAdapter.notifyDataSetChanged();
    }
}
