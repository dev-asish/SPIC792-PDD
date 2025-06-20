package com.example.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BuyerDetailsActivity extends AppCompatActivity {

    private EditText etName, etAadhar, etAge, etGender, etAddress;
    private RequestQueue requestQueue;
    private final String BUYER_DETAILS_URL = "http://192.168.185.150/pdd/api/buyer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_details);

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this);

        // Bind UI elements
        etName = findViewById(R.id.et_name);
        etAadhar = findViewById(R.id.et_aadhar);
        etAge = findViewById(R.id.et_age);
        etGender = findViewById(R.id.et_gender);
        etAddress = findViewById(R.id.et_address);
        Button btnConfirm = findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(v -> submitBuyerDetails());
    }

    private void submitBuyerDetails() {
        String name = etName.getText().toString().trim();
        String aadhar = etAadhar.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String gender = etGender.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // Basic validation
        if (name.isEmpty() || aadhar.isEmpty() || age.isEmpty() || gender.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, BUYER_DETAILS_URL,
                response -> {
                    // Handle successful response
                    Log.d("BuyerDetails", "Response: " + response);
                    saveUserTypeAndNavigate(name);
                },
                error -> {
                    // Handle error
                    Log.e("BuyerDetails", "Error: " + error.toString());
                    Toast.makeText(this, "Submission failed. Please try again.", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("aadhar", aadhar);
                params.put("age", age);
                params.put("gender", gender);
                params.put("address", address);
                return params;
            }

            @Override
            public byte[] getBody() {
                return ("name=" + name +
                        "&aadhar=" + aadhar +
                        "&age=" + age +
                        "&gender=" + gender +
                        "&address=" + address).getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        requestQueue.add(request);
    }

    private void saveUserTypeAndNavigate(String name) {
        // Save user type as Buyer
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userType", "Buyer");
        editor.apply();

        Toast.makeText(this, "Buyer: " + name + " confirmed", Toast.LENGTH_SHORT).show();

        // Redirect to HomeScreenActivity
        startActivity(new Intent(this, HomeScreenActivity.class));
        finish();
    }
}