package com.example.music_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class edit_profile_screen extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText profileName,profileEmail,profilePhone,passwords;
    ImageView profileImageView;
    Button doneBtn;
    FirebaseFirestore fStore;
    Uri uri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_screen);

        Intent data = getIntent();

        final String collectionID = data.getStringExtra("collection");
        final String fullName = data.getStringExtra("fullName");

        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");
        String userid = data.getStringExtra("userId");

        fStore = FirebaseFirestore.getInstance();

        profileName = findViewById(R.id.epUsername);
        profileEmail = findViewById(R.id.epEmail);
        profilePhone = findViewById(R.id.epPhone);
        passwords =  findViewById(R.id.epPassword);
        profileImageView = findViewById(R.id.profileImageView);
        doneBtn = findViewById(R.id.epDonebtn);



        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pck = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pck.setType("image/*");
                startActivityForResult(pck,1000);
                //Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(openGalleryIntent,1000);
            }
        });



        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty()){
                    Toast.makeText(edit_profile_screen.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }



                final String email = profileEmail.getText().toString();
                String username = profileName.getText().toString();
                String phoneNum =  profilePhone.getText().toString();
                String pass = passwords.getText().toString();


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();

                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                updateProfileInfotoFireBase(collectionID,userid,username,phoneNum,email,pass,encoded);

            }
        });

        profileEmail.setText(email);
        profileName.setText(fullName);
        profilePhone.setText(phone);

        Log.d(TAG, "onCreate: " + fullName + " " + email + " " + phone);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == RESULT_OK && data != null && data.getData() != null){

            uri = data.getData();
            try {
                Bitmap bitmaps = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                bitmap = bitmaps;
                profileImageView.setImageBitmap(bitmaps);
            }catch (Error w){
                System.out.println("error on image pick "+w);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   public void updateProfileInfotoFireBase( String collectionID,String userid, String name, String phone, String email,String password, String base64s){

       Map<String, Object> data1 = new HashMap<>();
       data1.put("userid", userid);
       data1.put("fristName", name);
       data1.put("password", password);
       data1.put("profilePic", base64s);
       data1.put("phoneNumber", phone);
       data1.put("userName", email);
       data1.put("address", "");
       data1.put("lastName", "");

       System.out.println("  ######## collection coded   "+collectionID);
       //System.out.println(" base64 coded   "+base64s);

       fStore.collection("user").document(collectionID).update(data1).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               System.out.println(" update complet ---------");
           }
       });

    }
}