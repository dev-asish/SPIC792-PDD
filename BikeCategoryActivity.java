package com.example.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class BikeCategoryActivity extends Activity {
    private Spinner spinnerBrand, spinnerFuel, spinnerMileage, spinnerPrice, spinnerColor;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_category);

        // Initialize UI elements
        spinnerBrand = findViewById(R.id.spinnerBrand);
        spinnerFuel = findViewById(R.id.spinnerFuel);
        spinnerMileage = findViewById(R.id.spinnerMileage);
        spinnerPrice = findViewById(R.id.spinnerPrice);
        spinnerColor = findViewById(R.id.spinnerColor);
        btnSearch = findViewById(R.id.btnSearch);

        // Setup spinners with data
        setupSpinner(spinnerBrand, new String[]{"Select Brand", "Yamaha", "Honda", "Ducati", "Kawasaki"});
        setupSpinner(spinnerFuel, new String[]{"Select Fuel Type", "Petrol", "Electric"});
        setupSpinner(spinnerMileage, new String[]{"Select Mileage", "30-40 km/l", "40-50 km/l", "50+ km/l"});
        setupSpinner(spinnerPrice, new String[]{"Select Price Range", "₹50K-₹1L", "₹1L-₹3L", "₹3L-₹5L", "₹5L+"});
        setupSpinner(spinnerColor, new String[]{"Select Color", "Red", "Blue", "Black", "White"});

        // Search button action to open BikeResultsActivity with selected filters
        btnSearch.setOnClickListener(v -> handleSearch());
    }

    private void handleSearch() {
        String brand = spinnerBrand.getSelectedItem().toString();
        String fuel = spinnerFuel.getSelectedItem().toString();
        String mileage = spinnerMileage.getSelectedItem().toString();
        String price = spinnerPrice.getSelectedItem().toString();
        String color = spinnerColor.getSelectedItem().toString();

        // Validate selections
        if (brand.equals("Select Brand") || fuel.equals("Select Fuel Type") ||
                mileage.equals("Select Mileage") || price.equals("Select Price Range") ||
                color.equals("Select Color")) {
            Toast.makeText(this, "Please select all options", Toast.LENGTH_SHORT).show();
            return;
        }

        // Log selected values for debugging
        Log.d("SelectedValues", "Brand: " + brand + ", Fuel: " + fuel +
                ", Mileage: " + mileage + ", Price: " + price + ", Color: " + color);

        // Open BikeResultsActivity and pass selected filters
        Intent intent = new Intent(this, BikeResultsActivity.class);
        intent.putExtra("brand", brand);
        intent.putExtra("fuel", fuel);
        intent.putExtra("mileage", mileage);
        intent.putExtra("price", price);
        intent.putExtra("color", color);
        startActivity(intent);
    }

    private void setupSpinner(Spinner spinner, String[] options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        spinner.setAdapter(adapter);
    }
}
