package com.example.locationfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText address_input, latitude_input, longitude_input;
    Button addNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        address_input = findViewById(R.id.address);
        latitude_input = findViewById(R.id.latitude);
        longitude_input = findViewById(R.id.longitude);
        addNew = findViewById(R.id.addNew);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = address_input.getText().toString().trim();
                String latitudeStr = latitude_input.getText().toString().trim();
                String longitudeStr = longitude_input.getText().toString().trim();

                // Check if any fields are empty
                if (address.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
                    Toast.makeText(AddActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double latitude = Double.parseDouble(latitudeStr);
                    double longitude = Double.parseDouble(longitudeStr);

                    // Validate latitude and longitude ranges
                    if (latitude < -90 || latitude > 90) {
                        Toast.makeText(AddActivity.this, "Latitude must be between -90 and 90", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (longitude < -180 || longitude > 180) {
                        Toast.makeText(AddActivity.this, "Longitude must be between -180 and 180", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Insert the data into the database
                    MyDBHelper db = new MyDBHelper(AddActivity.this);
                    db.addAddress(address, longitude, latitude);
                    Toast.makeText(AddActivity.this, "Successfully added new address", Toast.LENGTH_SHORT).show();

                } catch (NumberFormatException e) {
                    Toast.makeText(AddActivity.this, "Invalid latitude or longitude format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
