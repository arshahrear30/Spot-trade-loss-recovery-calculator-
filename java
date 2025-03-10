package com.example.spotcalculation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize EditText fields
        EditText buy1 = findViewById(R.id.Buy1);
        EditText coinbuy1 = findViewById(R.id.Coinbuy1);
        EditText buy2 = findViewById(R.id.Buy2);
        EditText avg = findViewById(R.id.Avg);
        EditText sell = findViewById(R.id.Sell);

        // Initialize TextView fields
        TextView needbuy = findViewById(R.id.needbuy);
        TextView buy2toavgPercentage = findViewById(R.id.buy2toavgpersentage);
        TextView buySpend = findViewById(R.id.buyspend);
        TextView totalSell = findViewById(R.id.totalsell);
        TextView fee = findViewById(R.id.fee);
        TextView profit = findViewById(R.id.profit);
        TextView profitPercentage = findViewById(R.id.profitpersentage);

        // Get the main LinearLayout
        LinearLayout mainLayout = findViewById(R.id.main);

        // Create a button to trigger calculations
        Button calculateButton = new Button(this);
        calculateButton.setText("Calculate Profit & Loss");
        mainLayout.addView(calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Convert EditText input to double
                    double buy1Value = Double.parseDouble(buy1.getText().toString());
                    double coinBuy1Value = Double.parseDouble(coinbuy1.getText().toString());
                    double buy2Value = Double.parseDouble(buy2.getText().toString());
                    double avgValue = Double.parseDouble(avg.getText().toString());
                    double sellValue = Double.parseDouble(sell.getText().toString());

                    // Perform calculations
                    double needBuyValue = ((buy1Value * coinBuy1Value) - (avgValue * coinBuy1Value)) / (avgValue - buy2Value);
                    double buy2toAvgPercentageValue = ((avgValue - buy2Value) * 100) / buy2Value;
                    double buySpendValue = (buy2Value * needBuyValue) + (buy1Value * coinBuy1Value);
                    double totalSellValue = sellValue * (coinBuy1Value + needBuyValue);
                    double feeValue = (totalSellValue * 0.00125) - (buySpendValue * 0.00125);
                    double profitValue = totalSellValue - buySpendValue - feeValue;
                    double profitPercentageValue = (profitValue * 100) / buySpendValue;

                    // Decimal format settings
                    DecimalFormat twoDecimal = new DecimalFormat("0.00");
                    DecimalFormat fiveDecimal = new DecimalFormat("0.00000");
                    DecimalFormat threeDecimal = new DecimalFormat("0.000");

                    // Display results with proper decimal formatting
                    needbuy.setText("Need to Buy: " + twoDecimal.format(needBuyValue)+" Coin");
                    buy2toavgPercentage.setText("Buy2 to Avg: " + twoDecimal.format(buy2toAvgPercentageValue) + "%");
                    buySpend.setText("Total Buy Spend: " + twoDecimal.format(buySpendValue)+"$");
                    totalSell.setText("Total Sell: " + twoDecimal.format(totalSellValue)+"$");
                    fee.setText("Fee: " + fiveDecimal.format(feeValue)+"$");
                    profit.setText("Profit amount: " + twoDecimal.format(profitValue)+"$");
                    profitPercentage.setText("Profit persentage: " + threeDecimal.format(profitPercentageValue) + "%");
                } catch (NumberFormatException e) {
                    // Handle empty input or invalid values
                    needbuy.setText("Invalid input. Please enter valid numbers.");
                }
            }
        });

        // Adjust padding for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
