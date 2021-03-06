package com.example.music_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.music_app.Adapter.oderAdapter;
import com.example.music_app.model.OderModel;
import com.example.music_app.model.cartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class oder_history extends AppCompatActivity {

    RecyclerView lists;
    ArrayList<OderModel> orderlists;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_history);

        orderlists = new ArrayList<OderModel>();
        lists = findViewById(R.id.oderHistoryList);
        init();

        getOderInfoMainFromFribase();
    }

    public void init(){
        db = FirebaseFirestore.getInstance();
    }

    private void getOderInfoMainFromFribase() {
        db.collection("orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println(document.getId() +"+++++ DocumentSnapshot data: " + document.getData());
                                OderModel model = new OderModel();

                                //String ids = document.get("itemId").toString();

                                model.setCollectionID(document.getId());
                                model.setDates(document.get("date").toString());
                                model.setItemName(document.get("itemName").toString());
                                model.setImageValue(document.get("itemImage").toString());



                                orderlists.add(model);

                                oderAdapter adpter = new oderAdapter(orderlists);
                                lists.setLayoutManager(new LinearLayoutManager(oder_history.this));
                                lists.setItemAnimator(new DefaultItemAnimator());
                                lists.setAdapter(adpter);
                            }
                        } else {
                            System.out.println("----*****-----get failed with "+ task.getException());
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}