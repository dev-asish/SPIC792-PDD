package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SellsActivity extends AppCompatActivity {

    private TextView tvName, tvPrice;
    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell); // Keep layout as activity_sell if XML file name is unchanged

        // Initialize views
        tvName = findViewById(R.id.value1);
        tvPrice = findViewById(R.id.value2);
        btnHome = findViewById(R.id.home_button);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("VEHICLE_NAME");
            String price = intent.getStringExtra("VEHICLE_PRICE");

            // Display the values with null safety
            tvName.setText(name != null ? name : "Not Available");
            tvPrice.setText(price != null ? price : "Not Available");
        }

        // Set click listener for Home button
        btnHome.setOnClickListener(view -> {
            Intent homeIntent = new Intent(SellsActivity.this, BuyerPageActivity.class);
            startActivity(homeIntent);
            finish(); // Close current activity
        });
    }
}
