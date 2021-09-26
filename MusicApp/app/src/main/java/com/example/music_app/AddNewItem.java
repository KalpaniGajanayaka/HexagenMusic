package com.example.music_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class AddNewItem extends AppCompatActivity {


    EditText itemName, itemBrandName, itemModel, description;
    ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        itemName = findViewById(R.id.itemNameInput);
        itemBrandName = findViewById(R.id.itemBrandInput);

    }
}