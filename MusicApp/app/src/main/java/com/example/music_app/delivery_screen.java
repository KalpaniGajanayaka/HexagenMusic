package com.example.music_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class delivery_screen extends AppCompatActivity {

    ImageView itemImage;
    TextView itemName;
    EditText name, phone, address, deleery_status, dates;
    Button submits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_screen);

        itemImage = findViewById(R.id.img7);
        itemName = findViewById(R.id.textView11);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        deleery_status = findViewById(R.id.deliverStatus);
        dates = findViewById(R.id.date);

        submits = findViewById(R.id.deliverDetailUpdate);

        submits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String item_input = itemName.getText().toString();
                String name_input = name.getText().toString();
                String phone_input = phone.getText().toString();
                String address_input = address.getText().toString();
                String develey = deleery_status.getText().toString();

                updateDelivaryInfoToDate(item_input,name_input,phone_input,address_input,develey);

            }
        });
    }


    public void updateDelivaryInfoToDate(String itemName,String nameIn, String phonInp,String address, String delivary){

    }
}