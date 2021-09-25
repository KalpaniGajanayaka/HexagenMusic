package com.example.music_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Deliver_Details extends AppCompatActivity {

    EditText nameInput, phoneInput,addressInput, deliverstatusInput , dateInput;
    Button detailUpdate;
    FirebaseFirestore dbroot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_details);

        nameInput = findViewById(R.id.name);
        phoneInput = findViewById(R.id.phone);
        addressInput = findViewById(R.id.address);
        deliverstatusInput = findViewById(R.id.deliverStatus);
        dateInput = findViewById(R.id.date);

        detailUpdate = findViewById(R.id.deliverDetailUpdate);

        dbroot=FirebaseFirestore.getInstance();


        detailUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = " name "+nameInput.getText().toString()+" phone "+phoneInput.getText().toString()+"address"+ addressInput.getText().toString()+ "deliverStatus"+ deliverstatusInput.getText().toString()+ " date "+dateInput.getText().toString();

                Toast t = Toast.makeText(Deliver_Details.this,s,Toast.LENGTH_LONG);
                t.show();
                updateDetailsonFirebase(nameInput.getText().toString(),phoneInput.getText().toString(),addressInput.getText().toString(),deliverstatusInput.getText().toString(),dateInput.getText().toString());

                //insertdata();
            }
        });
    }

    public void updateDetailsonFirebase(String name, String phone, String address, String de_state, String dates){

        Map<String,String> items=new HashMap<>();
        items.put("name",nameInput.getText().toString());
        items.put("phone",phoneInput.getText().toString());
        items.put("address",addressInput.getText().toString());
        items.put("deliverStatus", deliverstatusInput.getText().toString());
        items.put("date",dateInput.getText().toString());

        dbroot.collection("deliver").add(items)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        nameInput.setText("");
                        phoneInput.setText("");
                        addressInput.setText("");
                        deliverstatusInput.setText("");
                        dateInput.setText("");
                        Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();

                    }
                });

    }

//    public void insertdata(){
//
//        Map<String,String> items=new HashMap<>();
//        items.put("name",nameInput.getText().toString());
//        items.put("phone",phoneInput.getText().toString());
//        items.put("address",addressInput.getText().toString());
//        items.put("deliverStatus", deliverstatusInput.getText().toString());
//        items.put("date",dateInput.getText().toString());
//
//        dbroot.collection("deliver").add(items)
//                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                        nameInput.setText("");
//                        phoneInput.setText("");
//                        addressInput.setText("");
//                        deliverstatusInput.setText("");
//                        dateInput.setText("");
//                        Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
//
//                    }
//                });
//    }
}

