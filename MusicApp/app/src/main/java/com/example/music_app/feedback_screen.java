package com.example.music_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

public class feedback_screen extends AppCompatActivity {

    int userid;
    EditText message;
    Button send;

    private FirebaseFirestore db;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_screen);

        message = findViewById(R.id.editTextTextPersonName2);
        send = findViewById(R.id.button3);

        init();

        getUserInfo();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messages = message.getText().toString();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String tmps =sdf.format(timestamp);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addFeedbackToFirebase(messages,tmps);
                }

            }
        });

    }
    public void init(){
        db = FirebaseFirestore.getInstance();
    }

    private void getUserInfo() {
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        String pass = sp1.getString("user_id", null);

        if(pass != null){
            //alredy log
            userid = Integer.parseInt(pass);
        }else {
            //not log
            userid = 0;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addFeedbackToFirebase(String message, String stpm){
        int random = ThreadLocalRandom.current().nextInt(2, 99);

        CollectionReference itemss = db.collection("feedback");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("date", stpm);
        data1.put("feedbackId", random);
        data1.put("itemId", random);
        data1.put("message", message);
        data1.put("userid", userid);

        itemss.document("feedback0"+random).set(data1);
    }
}