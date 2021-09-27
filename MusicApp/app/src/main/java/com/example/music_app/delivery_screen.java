package com.example.music_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class delivery_screen extends AppCompatActivity {

    ImageView itemImage;
    TextView itemName;
    EditText name, phone, address, deleery_status, dates;
    Button submits;

    private FirebaseFirestore db;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_screen);

        init();

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

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String tmps =sdf.format(timestamp);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    updateDelivaryInfoToDate(item_input,name_input,phone_input,address_input,develey,tmps);
                }

            }
        });
    }

    public void init(){
        db = FirebaseFirestore.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateDelivaryInfoToDate(String itemName, String nameIn, String phonInp, String address, String delivary,String dt){

        int random = ThreadLocalRandom.current().nextInt(2, 99);

        CollectionReference itemss = db.collection("delivary");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("date", dt);
        data1.put("address",address);
        data1.put("delivary_st",delivary);
        data1.put("phone",phonInp);
        data1.put("name",nameIn);
        data1.put("oderid",random);

        itemss.document("delivary0"+random).set(data1);

    }
}