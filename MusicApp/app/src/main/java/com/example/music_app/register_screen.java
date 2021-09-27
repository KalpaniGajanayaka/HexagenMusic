package com.example.music_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class register_screen extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText Rusername, Rphonenumber, Remail, Rpassword;
    Button Rdonebtn, RLoginBtn;
    //    TextView RLoginBtn;
    //FirebaseAuth fAuth;
    ProgressBar progressBar4;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

       // fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Rusername = findViewById(R.id.Rusername);
        Rphonenumber = findViewById(R.id.Rphonenumber);
        Remail = findViewById(R.id.Remail);
        Rpassword = findViewById(R.id.Rpassword);
        Rdonebtn = findViewById(R.id.Rdonebtn);
        RLoginBtn = findViewById(R.id.RLoginBtn);
        progressBar4 = findViewById(R.id.progressBar);

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),home_screen.class));
//            finish();
//        }

        Rdonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Remail.getText().toString().trim();
                String password = Rpassword.getText().toString().trim();
                final String fullName = Rusername.getText().toString();
                final String phone = Rphonenumber.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Remail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Rpassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    Rpassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                //  progressBar4.setVisibility(View.VISIBLE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    registerNewUser(email,password,phone,fullName);
                }

                // register the user in firebase
//
//                fAuth.createUserWithEmailAndPassword(Remail.getText().toString(),Rpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task){
//                        if(task.isSuccessful()){
//                            Toast.makeText(register_screen.this, "User created", Toast.LENGTH_SHORT).show();
//
//                            userID = fAuth.getCurrentUser().getUid();
//                            DocumentReference documentReference = fStore.collection("users").document(userID);
//                            Map<String,Object> user =new HashMap<>();
//                            user.put("name", Rusername);
//                            user.put("email",Remail);
//                            user.put("phoneNumber",Rphonenumber);
//
//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "onSuccess:user profile is created for "+ userID);
//                                }
//                            });
//                            startActivity(new Intent(getApplicationContext(),login_screen.class));
//                        }else {
//                            Toast.makeText(register_screen.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            // progressBar4.setVisibility(View.GONE);
//                        }
//
//                    };
//                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void registerNewUser(String email, String password, String number, String name){

        int random = ThreadLocalRandom.current().nextInt(2, 99);

        CollectionReference itemss = fStore.collection("user");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("address", "");
        data1.put("fristName", name);
        data1.put("lastName", "");
        data1.put("profilePic", "" );
        data1.put("password",password );
        data1.put("phoneNumber",number);
        data1.put("userName",email);
        data1.put("userid",random);

        itemss.document("user0"+random).set(data1);


        // if success redirect to login screen


    }
}