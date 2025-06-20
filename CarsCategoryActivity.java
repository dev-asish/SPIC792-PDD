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

public class CarsCategoryActivity extends Activity {
    private Spinner spinnerBrand, spinnerSeating, spinnerFuel, spinnerMileage, spinnerPrice, spinnerColor;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_category);

        // Initialize UI elements
        spinnerBrand = findViewById(R.id.spinnerBrand);
        spinnerSeating = findViewById(R.id.spinnerSeating);
        spinnerFuel = findViewById(R.id.spinnerFuel);
        spinnerMileage = findViewById(R.id.spinnerMileage);
        spinnerPrice = findViewById(R.id.spinnerPrice);
        spinnerColor = findViewById(R.id.spinnerColor);
        btnSearch = findViewById(R.id.btnSearch);

        // Setup spinners with data
        setupSpinner(spinnerBrand, new String[]{"Select Brand", "Toyota", "Honda", "Ford", "BMW"});
        setupSpinner(spinnerSeating, new String[]{"Select Seating Capacity", "2-Seater", "4-Seater", "5-Seater", "7-Seater"});
        setupSpinner(spinnerFuel, new String[]{"Select Fuel Type", "Petrol", "Diesel", "Electric", "Hybrid"});
        setupSpinner(spinnerMileage, new String[]{"Select Mileage", "10-15 km/l", "15-20 km/l", "20-25 km/l"});
        setupSpinner(spinnerPrice, new String[]{"Select Price Range", "₹8L-₹16L", "₹16L-₹25L", "₹25L-₹33L", "₹33L+"});
        setupSpinner(spinnerColor, new String[]{"Select Color", "Red", "Blue", "Black", "White"});

        // Search button action to open CarResultsActivity with selected filters
        btnSearch.setOnClickListener(v -> handleSearch());
    }

    private void handleSearch() {
        String brand = spinnerBrand.getSelectedItem().toString();
        String seating = spinnerSeating.getSelectedItem().toString();
        String fuel = spinnerFuel.getSelectedItem().toString();
        String mileage = spinnerMileage.getSelectedItem().toString();
        String price = spinnerPrice.getSelectedItem().toString();
        String color = spinnerColor.getSelectedItem().toString();

        // Validate selections (ensure the user has selected actual values)
        if (brand.equals("Select Brand") || seating.equals("Select Seating Capacity") ||
                fuel.equals("Select Fuel Type") || mileage.equals("Select Mileage") ||
                price.equals("Select Price Range") || color.equals("Select Color")) {
            Toast.makeText(this, "Please select all options", Toast.LENGTH_SHORT).show();
            return;
        }

        // Log selected values for debugging
        Log.d("SelectedValues", "Brand: " + brand + ", Seating: " + seating +
                ", Fuel: " + fuel + ", Mileage: " + mileage +
                ", Price: " + price + ", Color: " + color);

        // Open CarResultsActivity and pass selected filters
        Intent intent = new Intent(this, CarResultsActivity.class);
        intent.putExtra("brand", brand);
        intent.putExtra("seating", seating);
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
