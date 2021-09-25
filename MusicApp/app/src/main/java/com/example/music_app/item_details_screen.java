package com.example.music_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class item_details_screen extends AppCompatActivity {

    ImageView mainImage;
    TextView itemName, itemQty, itemDetails;
    Button rentBtn, buyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_screen);

        mainImage = findViewById(R.id.itemMianImage);
        itemName = findViewById(R.id.itemNameView);
        itemQty = findViewById(R.id.avalableQtyView);
        itemDetails = findViewById(R.id.itemDetailsView);
        rentBtn = findViewById(R.id.rentButton);
        buyBtn = findViewById(R.id.buyButton);


        rentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}