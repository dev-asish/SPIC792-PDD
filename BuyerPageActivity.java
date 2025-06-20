package com.example.app1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BuyerPageActivity extends AppCompatActivity {

    private ImageView s1, s2, s3;
    private Button btnLocation;
    private static final String PREF_NAME = "UserLocation";
    private static final String KEY_LOCATION = "SelectedLocation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_page);

        // Initialize UI elements
        s1 = findViewById(R.id.imgCars);
        s2 = findViewById(R.id.imgBikes);
        s3 = findViewById(R.id.imgVans);
        btnLocation = findViewById(R.id.btnLocation);

        // Load saved location from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedLocation = sharedPreferences.getString(KEY_LOCATION, null);

        if (btnLocation != null) {
            if (savedLocation != null) {
                btnLocation.setText(savedLocation);
            } else {
                btnLocation.setText("No Location Selected");
            }

            btnLocation.setOnClickListener(v -> showLocationDialog());
        }

        // Set Click Listeners
        if (s1 != null) s1.setOnClickListener(v -> startActivity(new Intent(BuyerPageActivity.this, CarsCategoryActivity.class)));
        if (s2 != null) s2.setOnClickListener(v -> startActivity(new Intent(BuyerPageActivity.this, BikesCategoryActivity.class)));
        if (s3 != null) s3.setOnClickListener(v -> startActivity(new Intent(BuyerPageActivity.this, VanCategoryActivity.class)));
    }

    private void showLocationDialog() {
        String[] locations = {
                "Delhi", "Mumbai", "Bangalore", "Chennai", "Kolkata", "Hyderabad", "Pune", "Ahmedabad",
                "Jaipur", "Lucknow", "Kanpur", "Nagpur", "Indore", "Patna", "Bhopal", "Ludhiana",
                "Agra", "Nashik", "Vadodara", "Meerut", "Rajkot", "Varanasi", "Srinagar", "Aurangabad",
                "Dhanbad", "Amritsar", "Navi Mumbai", "Allahabad", "Ranchi", "Gwalior"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(BuyerPageActivity.this);
        builder.setTitle("Select Your Location");
        builder.setItems(locations, (dialog, which) -> {
            String selectedLocation = locations[which];

            // Save selected location to SharedPreferences
            SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
            editor.putString(KEY_LOCATION, selectedLocation);
            editor.apply();

            // Update button text
            if (btnLocation != null) {
                btnLocation.setText(selectedLocation);
            }

            // Show Toast
            Toast.makeText(BuyerPageActivity.this, "Selected Location: " + selectedLocation, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
