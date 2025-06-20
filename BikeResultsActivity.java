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

public class BikeResultsActivity extends AppCompatActivity {

    private TextView bikeName, bikePricePrediction;
    private LinearLayout similarBikesContainer;
    private Button fullListButton, nextButton;
    private ScrollView scrollView;
    private boolean isExpanded = false;
    private String selectedBike = "Yamaha R15";
    private int selectedPrice = 180000;

    private final Map<String, Integer> yamahaBikes = new HashMap<String, Integer>() {{
        put("Yamaha R15", 180000);
        put("Yamaha MT-15", 190000);
        put("Yamaha FZ V3", 120000);
        put("Yamaha FZ-X", 135000);
        put("Yamaha FZ 25", 160000);
        put("Yamaha Aerox 155", 145000);
        put("Yamaha R3", 380000);
        put("Yamaha XSR 155", 150000);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_results);

        bikeName = findViewById(R.id.bikeName);
        bikePricePrediction = findViewById(R.id.bikePricePrediction);
        similarBikesContainer = findViewById(R.id.similarBikesContainer);
        fullListButton = findViewById(R.id.fullListButton);
        nextButton = findViewById(R.id.nextButton);
        scrollView = findViewById(R.id.scrollView);

        String bikeModel = getIntent().getStringExtra("BIKE_MODEL");
        int price = getIntent().getIntExtra("PRICE", 180000);

        if (bikeModel != null && yamahaBikes.containsKey(bikeModel)) {
            selectedBike = bikeModel;
            selectedPrice = price;
        }

        updateSelectedBike();
        loadSimilarBikes(false);

        fullListButton.setOnClickListener(view -> toggleFullList());
        nextButton.setOnClickListener(view -> openSellActivity());
    }

    private void updateSelectedBike() {
        bikeName.setText(selectedBike);
        bikePricePrediction.setText("Prediction Price: ₹" + selectedPrice);
    }

    private void loadSimilarBikes(boolean showFullList) {
        similarBikesContainer.removeAllViews();

        int count = 0;
        for (Map.Entry<String, Integer> entry : yamahaBikes.entrySet()) {
            if (!showFullList && count >= 4) break;
            count++;

            LinearLayout bikeItem = new LinearLayout(this);
            bikeItem.setOrientation(LinearLayout.VERTICAL);
            bikeItem.setPadding(20, 20, 20, 20);
            bikeItem.setBackgroundColor(selectedBike.equals(entry.getKey()) ? Color.parseColor("#FF9800") : Color.parseColor("#666666"));

            TextView bikeNameView = new TextView(this);
            bikeNameView.setText(entry.getKey());
            bikeNameView.setTextSize(18);
            bikeNameView.setTextColor(Color.WHITE);
            bikeItem.addView(bikeNameView);

            TextView bikePriceView = new TextView(this);
            bikePriceView.setText("Prediction Price: ₹" + entry.getValue());
            bikePriceView.setTextSize(16);
            bikePriceView.setTextColor(Color.parseColor("#FFCA28"));
            bikeItem.addView(bikePriceView);

            bikeItem.setOnClickListener(view -> {
                selectedBike = entry.getKey();
                selectedPrice = entry.getValue();
                updateSelectedBike();
                loadSimilarBikes(isExpanded);
                scrollView.fullScroll(View.FOCUS_UP);
            });

            similarBikesContainer.addView(bikeItem);
        }
    }

    private void toggleFullList() {
        isExpanded = !isExpanded;
        loadSimilarBikes(isExpanded);
        fullListButton.setText(isExpanded ? "SHOW LESS" : "SHOW MORE");
    }

    private void openSellActivity() {
        Intent intent = new Intent(BikeResultsActivity.this, SellActivity.class);
        intent.putExtra("TYPE", "BIKE");
        intent.putExtra("VEHICLE_NAME", selectedBike);
        intent.putExtra("VEHICLE_PRICE", selectedPrice); // Send as int
        startActivity(intent);
    }
}
