package com.example.music_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.music_app.Adapter.musicIstrumentAdapter;
import com.example.music_app.model.MusicModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class login_screen extends AppCompatActivity {

    EditText Email,Password;
    Button LoginBtn,CreateBtn,forgotBtn;
    //    TextView mCreateBtn,forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        init();

        int status = retriveLogingStatus();

        if(status == 1) {
            //redirect into home
            Intent i =  new Intent(login_screen.this, home_screen.class);
            startActivity(i);
        }else {
            // continue login
        }

        fAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.Lemail);
        Password = findViewById(R.id.Lpassword);
        progressBar = findViewById(R.id.progressBar);
        LoginBtn = findViewById(R.id.Lloginbtn);
        CreateBtn = findViewById(R.id.Lregbtn);
        forgotBtn = findViewById(R.id.Lforgotbtn);

        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),register_screen.class));
            }
        });

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),View_Item.class));
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Password.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    Password.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user

                checkAuthInFirebase(email,password);

            }
        });
    }

    public void init(){
        db = FirebaseFirestore.getInstance();
    }

    public void StroreDataLocal(String userid){
        SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("login_st","1" );
        Ed.putString("user_id",userid);
        Ed.commit();
    }

    public int retriveLogingStatus(){
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);

        String unm=sp1.getString("login_st", null);
        String pass = sp1.getString("user_id", null);

        if(unm != null && Integer.parseInt(unm) == 1){
            //alredy log
            return 1;
        }else {
            //not log
            return 0;
        }
    }

    public void checkAuthInFirebase(String username, String password){
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println(document.getId() +"+++++ DocumentSnapshot data: " + document.getData());
                                MusicModel model = new MusicModel();

                                String uname = document.get("userName").toString();

                                String pass = document.get("password").toString();

                                String userids = document.get("userid").toString();
                                if(username.equals(uname) && password.equals(pass)){
                                    StroreDataLocal(userids);
                                    Toast.makeText(login_screen.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),home_screen.class));
                                }else {
                                    Toast.makeText(login_screen.this, "Error ! no user found" , Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }


                            }
                        } else {
                            System.out.println("----*****-----get failed with "+ task.getException());
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(login_screen.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

}