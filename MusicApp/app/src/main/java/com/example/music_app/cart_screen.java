package com.example.music_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.music_app.Adapter.cartAdapter;
import com.example.music_app.Adapter.musicIstrumentAdapter;
import com.example.music_app.model.MusicModel;
import com.example.music_app.model.cartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class cart_screen extends AppCompatActivity {

    RecyclerView lists;
    ArrayList<cartModel> cartlists;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        cartlists = new ArrayList<cartModel>();
        lists = findViewById(R.id.cartRcycleList);
        init();

        getCartInfoMainFromFribase();

    }

    public void init(){
        db = FirebaseFirestore.getInstance();
    }

    public void getCartInfoMainFromFribase(){
        System.out.println("--------calling cart-----");
        db.collection("cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println(document.getId() +"+++++ DocumentSnapshot data: " + document.getData());
                                cartModel model = new cartModel();

                                //String ids = document.get("itemId").toString();

                                model.setCartId(document.getId());
                                model.setDate(document.get("date").toString());
                                model.setItemId(Integer.parseInt(document.get("itemID").toString()));
                                model.setQty(Integer.parseInt(document.get("qty").toString()));
                                model.setType(document.get("type").toString());
                                model.setUserid(Integer.parseInt(document.get("userid").toString()));


                                cartlists.add(model);
                                getItemData();


//                                musicIstrumentAdapter adpter = new musicIstrumentAdapter(musicList);
//
//                                lists = findViewById(R.id.musicListView);
//                                lists.setLayoutManager(new LinearLayoutManager(home_screen.this));
//                                lists.setItemAnimator(new DefaultItemAnimator());
//                                lists.setAdapter(adpter);
                            }
                        } else {
                            System.out.println("----*****-----get failed with "+ task.getException());
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getItemData(){
        db.collection("item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for (cartModel model : cartlists) {
                                    if(model.getItemId() == Integer.parseInt(document.get("itemId").toString())){
                                        model.setImageData(document.get("itemPic").toString());
                                        model.setItemPrice("00.00");
                                        model.setItemName(document.get("itemName").toString());
                                    }
                                }
                            }
                        }else {
                            System.out.println("----*****-----get failed with "+ task.getException());
                        }
                    }
                });

        System.out.println("cart data "+cartlists);
        cartAdapter adpter = new cartAdapter(cartlists);
        lists.setLayoutManager(new LinearLayoutManager(cart_screen.this));
        lists.setItemAnimator(new DefaultItemAnimator());
        lists.setAdapter(adpter);
    }
}