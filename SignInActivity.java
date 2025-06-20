package com.example.app1;

import android.content.Intent;
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
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    // UI elements
    private EditText mobileNumber, email, username, password, confirmPassword;
    private RequestQueue requestQueue;
    private final String SIGNIN_URL = "http://192.168.185.150/pdd/api/sign_in.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this);

        // Bind UI elements
        mobileNumber = findViewById(R.id.mobileNumber);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.setPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        Button signInButton = findViewById(R.id.signInButton);

        // Set click listener for sign in
        signInButton.setOnClickListener(v -> handleSignIn());
    }

    private void handleSignIn() {
        if (!validateInputs()) return;

        StringRequest request = new StringRequest(Request.Method.POST, SIGNIN_URL,
                response -> {
                    Log.d("SIGNIN_SUCCESS", response);
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                    navigateToLogin(); // Optional redirection
                },
                error -> {
                    Log.e("SIGNIN_ERROR", error.toString());
                    Toast.makeText(this, "Registration failed. Check connection.", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mobilenumber", mobileNumber.getText().toString().trim());
                params.put("email", email.getText().toString().trim());
                params.put("username", username.getText().toString().trim());
                params.put("password", password.getText().toString().trim());
                params.put("confirmpassword", confirmPassword.getText().toString().trim());
                return params;
            }
        };

        requestQueue.add(request);
    }

    private boolean validateInputs() {
        if (mobileNumber.getText().toString().trim().isEmpty() ||
                email.getText().toString().trim().isEmpty() ||
                username.getText().toString().trim().isEmpty() ||
                password.getText().toString().trim().isEmpty() ||
                confirmPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
