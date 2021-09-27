package com.example.music_app;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class AddNewItem extends AppCompatActivity {


    EditText itemName, itemBrandName, itemModel, description;
    ImageView itemImage;
    Uri uri;
    Bitmap bitmap;
    Button submitButton;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        init();

        itemName = findViewById(R.id.itemNameInput);
        itemBrandName = findViewById(R.id.itemBrandInput);
        itemModel = findViewById(R.id.itemModelInput);
        description = findViewById(R.id.itemDescriptionInput);
        submitButton =  findViewById(R.id.button11);
        itemImage = findViewById(R.id.imageView9);


        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pck = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pck.setType("image/*");
                startActivityForResult(pck,1000);
                //Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(openGalleryIntent,1000);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                String item_name = itemName.getText().toString();
                String brandName = itemBrandName.getText().toString();
                String modelName = itemModel.getText().toString();
                String descrip = description.getText().toString();

//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                byte[] byteArray = byteArrayOutputStream .toByteArray();
//
//                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    insertItemInfoIntoFireBase(item_name,brandName,modelName,descrip);
                }

            }
        });
    }

    public void init(){
        db = FirebaseFirestore.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void insertItemInfoIntoFireBase(String name, String brand, String model, String desc ){

        int random = ThreadLocalRandom.current().nextInt(2, 99);

        CollectionReference itemss = db.collection("item");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("brandName", brand);
        data1.put("description", desc);
        data1.put("itemId", random);
        data1.put("itemModel", model);
        data1.put("itemName", name);
        data1.put("itemPic", "");

        itemss.document("item0"+random).set(data1);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == RESULT_OK && data != null && data.getData() != null){

            uri = data.getData();
            try {
                Bitmap bitmaps = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                bitmap = bitmaps;
                itemImage.setImageBitmap(bitmaps);
            }catch (Error w){
                System.out.println("error on image pick "+w);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}