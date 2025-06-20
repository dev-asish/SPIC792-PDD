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

public class VanCategoryActivity extends Activity {
    private Spinner spinnerBrand, spinnerSeating, spinnerFuel, spinnerMileage, spinnerPrice, spinnerColor;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vans_category);

        // Initialize UI elements
        spinnerBrand = findViewById(R.id.spinnerBrand);
        spinnerSeating = findViewById(R.id.spinnerSeating);
        spinnerFuel = findViewById(R.id.spinnerFuel);
        spinnerMileage = findViewById(R.id.spinnerMileage);
        spinnerPrice = findViewById(R.id.spinnerPrice);
        spinnerColor = findViewById(R.id.spinnerColor);
        btnSearch = findViewById(R.id.btnSearch);

        // Setup spinners with data
        setupSpinner(spinnerBrand, new String[]{"Select Brand", "Toyota", "Nissan", "Ford", "Mercedes"});
        setupSpinner(spinnerSeating, new String[]{"Select Seating Capacity", "5-Seater", "7-Seater", "9-Seater", "12-Seater"});
        setupSpinner(spinnerFuel, new String[]{"Select Fuel Type", "Petrol", "Diesel", "Electric", "Hybrid"});
        setupSpinner(spinnerMileage, new String[]{"Select Mileage", "8-12 km/l", "12-16 km/l", "16-20 km/l"});
        setupSpinner(spinnerPrice, new String[]{"Select Price Range", "₹10L-₹20L", "₹20L-₹30L", "₹30L-₹40L", "₹40L+"});
        setupSpinner(spinnerColor, new String[]{"Select Color", "White", "Black", "Silver", "Blue"});

        // Search button action to open VanResultActivity with selected filters
        btnSearch.setOnClickListener(v -> handleSearch());
    }

    private void handleSearch() {
        String brand = spinnerBrand.getSelectedItem().toString();
        String seating = spinnerSeating.getSelectedItem().toString();
        String fuel = spinnerFuel.getSelectedItem().toString();
        String mileage = spinnerMileage.getSelectedItem().toString();
        String price = spinnerPrice.getSelectedItem().toString();
        String color = spinnerColor.getSelectedItem().toString();

        // Validate selections
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

        // Open VanResultActivity and pass selected filters
        Intent intent = new Intent(this, VanResultsActivity.class);
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
