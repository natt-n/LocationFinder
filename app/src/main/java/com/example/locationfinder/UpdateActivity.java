package com.example.locationfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText addressInput, latitudeInput, longitudeInput;
    Button update, backBtn, deleteBtn;

    String id, address, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize the views
        addressInput = findViewById(R.id.address2);
        latitudeInput = findViewById(R.id.latitude2);
        longitudeInput = findViewById(R.id.longitude2);
        update = findViewById(R.id.update);
        backBtn = findViewById(R.id.backBtn2);
        deleteBtn = findViewById(R.id.delete);

        // Handle back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();  // Finish the activity and return to the previous one
            }
        });

        // Handle update button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper mydb = new MyDBHelper(UpdateActivity.this);
                mydb.updateData(id, addressInput.getText().toString(), latitudeInput.getText().toString(), longitudeInput.getText().toString());
            }
        });

        // Handle delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper mydb = new MyDBHelper(UpdateActivity.this);
                boolean isDeleted = mydb.deleteData(id);  // Pass the ID of the record to delete
                if (isDeleted) {
                    Toast.makeText(UpdateActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                    finish();  // Close the activity after deletion
                } else {
                    Toast.makeText(UpdateActivity.this, "Failed to delete data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Retrieve data from Intent
        getSetIntentData();
    }

    void getSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("address") && getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {
            id = getIntent().getStringExtra("id");
            address = getIntent().getStringExtra("address");
            latitude = getIntent().getStringExtra("latitude");
            longitude = getIntent().getStringExtra("longitude");

            addressInput.setText(address);
            latitudeInput.setText(latitude);
            longitudeInput.setText(longitude);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}
