package com.example.music_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class rent_details extends AppCompatActivity {

    EditText name, address, contact_num, email, rent_date, duration;
    Button submit;

    private FirebaseFirestore db;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_details);

        init();

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

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String tmps =sdf.format(timestamp);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    updateRentDetailOnFirebase(name_input,add_input,contact_input,email_input,tmps,duration_input);
                }
            }
        });

    }


    public void init(){
        db = FirebaseFirestore.getInstance();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void updateRentDetailOnFirebase(String name, String address, String numb, String email, String rent, String duration){

        int random = ThreadLocalRandom.current().nextInt(2, 99);

        CollectionReference itemss = db.collection("rent");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", name);
        data1.put("address", address);
        data1.put("number", numb);
        data1.put("rent_date", rent);
        data1.put("mail", email);
        data1.put("duration", duration);

        itemss.document("rent0"+random).set(data1);

    }
}