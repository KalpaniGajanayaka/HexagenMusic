package com.example.music_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class rent_details extends AppCompatActivity {

    EditText name, address, contact_num, email, rent_date, duration;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_details);

        name = findViewById(R.id.editTextTextPersonName8);
        address = findViewById(R.id.editTextTextPersonName11);
        contact_num = findViewById(R.id.editTextTextPersonName14);
        email = findViewById(R.id.editTextTextEmailAddress);
        rent_date = findViewById(R.id.editTextDate);
        duration = findViewById(R.id.editTextDate2);

        submit = findViewById(R.id.button13);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_input = name.getText().toString();
                String add_input = address.getText().toString();
                String contact_input = contact_num.getText().toString();
                String email_input = email.getText().toString();
                String rent_input = rent_date.getText().toString();
                String duration_input = duration.getText().toString();

                updateRentDetailOnFirebase(name_input,add_input,contact_input,email_input,rent_input,duration_input);
            }
        });

    }


    public  void updateRentDetailOnFirebase(String name, String address, String numb, String email, String rent, String duration){

    }
}