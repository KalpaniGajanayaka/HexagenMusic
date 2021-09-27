package com.example.music_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class item_details_screen extends AppCompatActivity {

    ImageView mainImage;
    TextView itemName, itemQty, itemDetails;
    Button rentBtn, buyBtn;

    private int itemid = 0;
    private int uuserids = 0;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_screen);

        Intent intent = getIntent();
        String str = intent.getStringExtra("itemID");

        mainImage = findViewById(R.id.itemMianImage);
        itemName = findViewById(R.id.itemNameView);
        itemQty = findViewById(R.id.avalableQtyView);
        itemDetails = findViewById(R.id.itemDetailsView);
        rentBtn = findViewById(R.id.rentButton);
        buyBtn = findViewById(R.id.buyButton);

        System.out.println(" --- details "+str);

        init();

        getUserInfo();

        getSelectedItemDetailFromFirebase(str);

        rentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(item_details_screen.this,rent_details.class);
                startActivity(i);

            }
        });

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addtoCartItem(itemid,1,uuserids,"BUY");
                }
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

    public void getSelectedItemDetailFromFirebase(String docunmntID){

        DocumentReference docRef = db.collection("item").document(docunmntID); //

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    System.out.println("------- documet "+document.toString());
                    if (document.exists()) {
                        System.out.println(" +++++ DocumentSnapshot data: " + document.getData());
                        String itmID = document.get("itemId").toString();

                        itemid = Integer.parseInt(document.get("itemId").toString());

                        itemName.setText(document.get("itemName").toString());
                        itemDetails.setText(document.get("description").toString());
                        final String pureBase64Encoded = document.get("itemPic").toString().substring(document.get("itemPic").toString().indexOf(",")  + 1);
                        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);

                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
                        mainImage.setImageBitmap(decodedByte);

                        getStockItemCountFromFirebase(Integer.parseInt(itmID));

                    } else {
                        System.out.println("******** No such document");
                    }
                } else {
                    System.out.println("----*****-----get failed with "+ task.getException());
                }

            }
        });
    }

    public void getStockItemCountFromFirebase(int itemID){

        db.collection("stock")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Log.d(TAG, document.getId() + " => " + document.getData());
                                String itemId = document.get("itemID").toString();
                                int item_id = Integer.parseInt(itemId);
                                if(itemID == item_id) {
                                    itemQty.setText(" Qty : "+document.get("qty").toString());
                                    buyBtn.setText("Buy at \n Rs : "+document.get("sellRate").toString());
                                    rentBtn.setText("Rent at \n Rs : "+document.get("rentRate").toString());
                                }
                            }
                        } else {
                            System.out.println("----*****-----get failed with "+ task.getException());
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addtoCartItem(int itemid, int qty, int userid, String type){
        //cartid , date, itemID, qty, type, userid

        int random = ThreadLocalRandom.current().nextInt(2, 99);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        CollectionReference itemss = db.collection("cart");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("cartid", random);
        data1.put("date", ts);
        data1.put("itemID", itemid);
        data1.put("qty", qty);
        data1.put("type", type);
        data1.put("userid", userid);

        itemss.document("cart0"+random).set(data1);
    }


}