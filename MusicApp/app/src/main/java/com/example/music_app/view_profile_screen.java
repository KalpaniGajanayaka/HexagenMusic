package com.example.music_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.music_app.model.cartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class view_profile_screen extends AppCompatActivity {

    Button deleteProfile, editProfile;
    ImageView profilePic;
    TextView profileName, profileEmail, profilePhone, address;
    String collectionId;
    int uuserids = 0;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_screen);

        profilePic = findViewById(R.id.profileImage);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profilePhone = findViewById(R.id.profilePhone);
        address = findViewById(R.id.textView8);
        deleteProfile = findViewById(R.id.button9);
        editProfile = findViewById(R.id.button10);

        getUserInfo();

        init();

        getUserPrifileInfoFromFireBase();

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view_profile_screen.this, edit_profile_screen.class);
                i.putExtra("collection",collectionId);
                i.putExtra("fullName",profileName.getText().toString());
                i.putExtra("email",profileEmail.getText().toString());
                i.putExtra("phone",profilePhone.getText().toString());
                i.putExtra("userId",uuserids+"");
                startActivity(i);
            }
        });
    }

    private void getUserInfo() {
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        String pass = sp1.getString("user_id", null);

        if(pass != null){
            //alredy log
            uuserids = Integer.parseInt(pass);
        }else {
            //not log
            uuserids = 0;
        }
    }

    public  void init(){
        db = FirebaseFirestore.getInstance();
    }

    public void getUserPrifileInfoFromFireBase(){
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(uuserids == Integer.parseInt(document.get("userid").toString())) {
                                    collectionId = document.getId();

                                    profileName.setText(document.get("fristName").toString() + " " + document.get("lastName").toString());
                                    profileEmail.setText(document.get("userName").toString());
                                    profilePhone.setText(document.get("phoneNumber").toString());
                                    address.setText(document.get("address").toString());


                                    if (document.get("profilePic").toString() != null) {
                                        final String pureBase64Encoded = document.get("profilePic").toString().substring(document.get("profilePic").toString().indexOf(",") + 1);
                                        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);

                                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                        profilePic.setImageBitmap(decodedByte);
                                    }
                                }
                            }
                        }else {
                            System.out.println("----*****-----get failed with "+ task.getException());
                        }
                    }
                });
    }

}