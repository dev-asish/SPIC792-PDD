package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        // Find views
        Button mainLoginButton = findViewById(R.id.loginButton);
        TextView signInText = findViewById(R.id.signInText);

        // Navigate to LoginActivity on login button click
        if (mainLoginButton != null) {
            mainLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Navigate to SignInActivity when clicking on "Sign in" text
        if (signInText != null) {
            signInText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainScreenActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
