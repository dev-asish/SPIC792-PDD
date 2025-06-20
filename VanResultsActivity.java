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

public class VanResultsActivity extends AppCompatActivity {

    private TextView vanName, vanPricePrediction;
    private LinearLayout similarVansContainer;
    private Button fullListButton, nextButton;
    private ScrollView scrollView;
    private boolean isExpanded = false;
    private String selectedVan = "Maruti Suzuki Eeco";
    private int selectedPrice = 550000;

    private final Map<String, Integer> vanModels = new HashMap<String, Integer>() {{
        put("Maruti Suzuki Eeco", 550000);
        put("Mahindra Supro", 600000);
        put("Force Traveller", 1500000);
        put("Tata Winger", 1400000);
        put("Ashok Leyland Dost", 700000);
        put("Mahindra Bolero Camper", 900000);
        put("Tata Magic Express", 650000);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_results);

        vanName = findViewById(R.id.vanName);
        vanPricePrediction = findViewById(R.id.vanPricePrediction);
        similarVansContainer = findViewById(R.id.similarVansContainer);
        fullListButton = findViewById(R.id.fullListButton);
        nextButton = findViewById(R.id.nextButton);
        scrollView = findViewById(R.id.scrollView);

        String vanModel = getIntent().getStringExtra("VAN_MODEL");
        int price = getIntent().getIntExtra("PRICE", 550000);

        if (vanModel != null && vanModels.containsKey(vanModel)) {
            selectedVan = vanModel;
            selectedPrice = price;
        }

        updateSelectedVan();
        loadSimilarVans(false);

        fullListButton.setOnClickListener(view -> toggleFullList());
        nextButton.setOnClickListener(view -> openSellActivity());
    }

    private void updateSelectedVan() {
        vanName.setText(selectedVan);
        vanPricePrediction.setText("Prediction Price: ₹" + selectedPrice);
    }

    private void loadSimilarVans(boolean showFullList) {
        similarVansContainer.removeAllViews();

        int count = 0;
        for (Map.Entry<String, Integer> entry : vanModels.entrySet()) {
            if (!showFullList && count >= 4) break;
            count++;

            LinearLayout vanItem = new LinearLayout(this);
            vanItem.setOrientation(LinearLayout.VERTICAL);
            vanItem.setPadding(20, 20, 20, 20);
            vanItem.setBackgroundColor(selectedVan.equals(entry.getKey()) ? Color.parseColor("#FF9800") : Color.parseColor("#666666"));

            TextView vanNameView = new TextView(this);
            vanNameView.setText(entry.getKey());
            vanNameView.setTextSize(18);
            vanNameView.setTextColor(Color.WHITE);
            vanItem.addView(vanNameView);

            TextView vanPriceView = new TextView(this);
            vanPriceView.setText("Prediction Price: ₹" + entry.getValue());
            vanPriceView.setTextSize(16);
            vanPriceView.setTextColor(Color.parseColor("#FFCA28"));
            vanItem.addView(vanPriceView);

            vanItem.setOnClickListener(view -> {
                selectedVan = entry.getKey();
                selectedPrice = entry.getValue();
                updateSelectedVan();
                loadSimilarVans(isExpanded);
                scrollView.fullScroll(View.FOCUS_UP);
            });

            similarVansContainer.addView(vanItem);
        }
    }

    private void toggleFullList() {
        isExpanded = !isExpanded;
        loadSimilarVans(isExpanded);
        fullListButton.setText(isExpanded ? "SHOW LESS" : "SHOW MORE");
    }

    private void openSellActivity() {
        Intent intent = new Intent(VanResultsActivity.this, SellActivity.class);
        intent.putExtra("TYPE", "VAN");
        intent.putExtra("VEHICLE_NAME", selectedVan);
        intent.putExtra("VEHICLE_PRICE", selectedPrice);
        startActivity(intent);
    }
}
