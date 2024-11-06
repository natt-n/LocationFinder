package com.example.locationfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_btn;

    MyDBHelper mydb;
    ArrayList<String> id, address, lat, lon;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_btn = findViewById(R.id.addBtn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
            }
        });

        mydb = new MyDBHelper(MainActivity.this);
        id = new ArrayList<>();
        address = new ArrayList<>();
        lat = new ArrayList<>();
        lon = new ArrayList<>();

        storeData();

        customAdapter = new CustomAdapter(MainActivity.this, id, address, lat, lon);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void storeData(){
        Cursor cursor = mydb.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                address.add(cursor.getString(1));
                lat.add(cursor.getString(2));
                lon.add(cursor.getString(3));
            }
        }
    }
}