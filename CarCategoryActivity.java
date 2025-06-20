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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CarCategoryActivity extends Activity {

    private Spinner spinnerBrand, spinnerSeating, spinnerFuel, spinnerMileage, spinnerPrice, spinnerColor;
    private RequestQueue requestQueue;
    private final String CAR_DETAILS_URL = "http://192.168.218.150/pdd/api/car_details.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_category);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();

        //.onnx model
        spinnerBrand = findViewById(R.id.spinnerBrand);
        spinnerSeating = findViewById(R.id.spinnerSeating);
        spinnerFuel = findViewById(R.id.spinnerFuel);
        spinnerMileage = findViewById(R.id.spinnerMileage);
        spinnerPrice = findViewById(R.id.spinnerPrice);
        spinnerColor = findViewById(R.id.spinnerColor);
        Button btnSearch = findViewById(R.id.btnSearch);

        setupSpinner(spinnerBrand, new String[]{"Select Brand", "Toyota", "Honda", "Ford", "BMW"});
        setupSpinner(spinnerSeating, new String[]{"Select Seating Capacity", "2-Seater", "4-Seater", "5-Seater", "7-Seater"});
        setupSpinner(spinnerFuel, new String[]{"Select Fuel Type", "Petrol", "Diesel", "Electric", "Hybrid"});
        setupSpinner(spinnerMileage, new String[]{"Select Mileage", "10-15 km/l", "15-20 km/l", "20-25 km/l"});
        setupSpinner(spinnerPrice, new String[]{"Select Price Range", "₹8L-₹16L", "₹16L-₹25L", "₹25L-₹33L", "₹33L+"});
        setupSpinner(spinnerColor, new String[]{"Select Color", "Red", "Blue", "Black", "White"});

        btnSearch.setOnClickListener(v -> handleSearch());
    }

    private void handleSearch() {
        String brand = spinnerBrand.getSelectedItem().toString();
        String seating = spinnerSeating.getSelectedItem().toString();
        String fuel = spinnerFuel.getSelectedItem().toString();
        String mileage = spinnerMileage.getSelectedItem().toString();
        String price = spinnerPrice.getSelectedItem().toString();
        String color = spinnerColor.getSelectedItem().toString();

        if (brand.equals("Select Brand") || seating.equals("Select Seating Capacity") ||
                fuel.equals("Select Fuel Type") || mileage.equals("Select Mileage") ||
                price.equals("Select Price Range") || color.equals("Select Color")) {
            Toast.makeText(this, "Please select all options", Toast.LENGTH_SHORT).show();
            return;
        }

        sendToServer(brand, seating, fuel, mileage, price, color);
    }

    private void sendToServer(final String brand, final String seating,
                              final String fuel, final String mileage,
                              final String price, final String color) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CAR_DETAILS_URL,
                response -> {
                    Log.d("ServerResponse", "Response: " + response);
                    if (response.trim().equalsIgnoreCase("SUCCESS")) {
                        Toast.makeText(CarCategoryActivity.this, "Car details saved successfully!", Toast.LENGTH_SHORT).show();
                        navigateToResults(brand, seating, fuel, mileage, price, color);
                    } else {
                        Toast.makeText(CarCategoryActivity.this,
                                "Server Error: " + response, Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    String errorMessage = "Network error";
                    // Check if there is a network response error
                    if (error.networkResponse != null) {
                        errorMessage = "Error " + error.networkResponse.statusCode;
                        // Try to extract and log the response body from the error
                        try {
                            String errorDetails = new String(error.networkResponse.data);
                            errorMessage += ": " + errorDetails;
                            Log.e("VolleyError", "Error details: " + errorDetails);  // Logs error details for debugging
                        } catch (Exception e) {
                            Log.e("ErrorParse", "Couldn't parse error response", e);
                        }
                    }

                    // Show a Toast message with the error details
                    Toast.makeText(CarCategoryActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    Log.e("VolleyError", "Error details: ", error);
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("brand", brand);
                params.put("seating", seating);
                params.put("fuel", fuel);
                params.put("mileage", mileage);
                params.put("price", price);
                params.put("colour", color); // 'colour' spelling kept same for PHP
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, // 15 seconds timeout
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

    private void navigateToResults(String brand, String seating, String fuel,
                                   String mileage, String price, String color) {
        Intent intent = new Intent(this, CarResultsActivity.class);
        intent.putExtra("brand", brand);
        intent.putExtra("seating", seating);
        intent.putExtra("fuel", fuel);
        intent.putExtra("mileage", mileage);
        intent.putExtra("price", price);
        intent.putExtra("color", color); // Keeping as 'color' internally for app
        startActivity(intent);
    }

    private void setupSpinner(Spinner spinner, String[] options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, options);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }
}
