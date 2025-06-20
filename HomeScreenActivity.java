package com.example.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomeScreenActivity extends AppCompatActivity {

    private Button btnBuyer, btnSeller, btnLogout;
    private ImageButton startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        btnBuyer = findViewById(R.id.btn_buyer);
        btnSeller = findViewById(R.id.btn_seller);
        startButton = findViewById(R.id.startButton);
        btnLogout = findViewById(R.id.btn_logout);

        btnBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
                editor.putString("userType", "Buyer");
                editor.apply();
                Toast.makeText(HomeScreenActivity.this, "Buyer selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeScreenActivity.this, BuyerDetailsActivity.class));
            }
        });

        btnSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
                editor.putString("userType", "Seller");
                editor.apply();
                Toast.makeText(HomeScreenActivity.this, "Seller selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeScreenActivity.this, SellerDetailsActivity.class));
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreenActivity.this, "Start Button Clicked", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String userType = sharedPreferences.getString("userType", "");

                if (userType.equals("Buyer")) {
                    startActivity(new Intent(HomeScreenActivity.this, BuyerPageActivity.class));
                } else if (userType.equals("Seller")) {
                    startActivity(new Intent(HomeScreenActivity.this, SellerPageActivity.class));
                } else {
                    Toast.makeText(HomeScreenActivity.this, "Please select Buyer or Seller first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                Toast.makeText(HomeScreenActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeScreenActivity.this, MainScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
