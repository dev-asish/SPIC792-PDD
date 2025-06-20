package com.example.app1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class CarResultsActivity extends AppCompatActivity {

    private TextView carName, carPricePrediction;
    private LinearLayout similarCarsContainer;
    private Button fullListButton, nextButton;
    private ScrollView scrollView;
    private boolean isExpanded = false;
    private String selectedCar = "Tayota Innova";
    private int selectedPrice = 900000;

    private final Map<String, Integer> carList = new HashMap<String, Integer>() {{
        put("Tayota Innova ", 900000);
        put("Carmy Hybrid", 850000);
        put("Tayota crysta", 1000000);
        put("Pirus ", 1400000);
        put("Coralla ", 1500000);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_results);

        carName = findViewById(R.id.carName);
        carPricePrediction = findViewById(R.id.carPricePrediction);
        similarCarsContainer = findViewById(R.id.similarCarsContainer);
        fullListButton = findViewById(R.id.fullListButton);
        nextButton = findViewById(R.id.nextButton);
        scrollView = findViewById(R.id.scrollView);

        String carModel = getIntent().getStringExtra("CAR_MODEL");
        int price = getIntent().getIntExtra("PRICE", 700000);

        if (carModel != null && carList.containsKey(carModel)) {
            selectedCar = carModel;
            selectedPrice = price;
        }

        updateSelectedCar();
        loadSimilarCars(false);

        fullListButton.setOnClickListener(view -> toggleFullList());
        nextButton.setOnClickListener(view -> openSellActivity());
    }

    private void updateSelectedCar() {
        carName.setText(selectedCar);
        carPricePrediction.setText("Prediction Price: ₹" + selectedPrice);
    }

    private void loadSimilarCars(boolean showFullList) {
        similarCarsContainer.removeAllViews();

        int count = 0;
        for (Map.Entry<String, Integer> entry : carList.entrySet()) {
            if (!showFullList && count >= 4) break;
            count++;

            LinearLayout carItem = new LinearLayout(this);
            carItem.setOrientation(LinearLayout.VERTICAL);
            carItem.setPadding(20, 20, 20, 20);
            carItem.setBackgroundColor(selectedCar.equals(entry.getKey()) ? Color.parseColor("#FF9800") : Color.parseColor("#666666"));

            TextView carNameView = new TextView(this);
            carNameView.setText(entry.getKey());
            carNameView.setTextSize(18);
            carNameView.setTextColor(Color.WHITE);
            carItem.addView(carNameView);

            TextView carPriceView = new TextView(this);
            carPriceView.setText("Prediction Price: ₹" + entry.getValue());
            carPriceView.setTextSize(16);
            carPriceView.setTextColor(Color.parseColor("#FFCA28"));
            carItem.addView(carPriceView);

            carItem.setOnClickListener(view -> {
                selectedCar = entry.getKey();
                selectedPrice = entry.getValue();
                updateSelectedCar();
                loadSimilarCars(isExpanded);
                scrollView.fullScroll(View.FOCUS_UP);
            });

            similarCarsContainer.addView(carItem);
        }
    }

    private void toggleFullList() {
        isExpanded = !isExpanded;
        loadSimilarCars(isExpanded);
        fullListButton.setText(isExpanded ? "SHOW LESS" : "SHOW MORE");
    }

    private void openSellActivity() {
        Intent intent = new Intent(CarResultsActivity.this, SellActivity.class);
        intent.putExtra("TYPE", "CAR");
        intent.putExtra("VEHICLE_NAME", selectedCar);
        intent.putExtra("VEHICLE_PRICE", selectedPrice); // Send as int
        startActivity(intent);
    }
}
