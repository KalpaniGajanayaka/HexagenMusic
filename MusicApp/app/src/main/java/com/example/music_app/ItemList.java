package com.example.music_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.music_app.Adapter.itemListAdspter;
import com.example.music_app.Adapter.musicIstrumentAdapter;
import com.example.music_app.model.MusicModel;
import com.example.music_app.model.itemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    RecyclerView lists;
    ArrayList<itemModel> musicList;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        musicList =  new ArrayList<itemModel>();
        lists = findViewById(R.id.musicListView);

        init();
        getMusicDataFromFireStore();

    }

    public void init(){
        db = FirebaseFirestore.getInstance();
    }

    public ArrayList<MusicModel> getMusicDataFromFireStore(){

        System.out.println("----calling method");

        db.collection("item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println(document.getId() +"+++++ DocumentSnapshot data: " + document.getData());
                                itemModel model = new itemModel();

                                String ids = document.get("itemId").toString();

                                model.setCollectionID(document.getId());
                                model.setItemId(Integer.parseInt(ids));
                                model.setImageValue((String) document.get("itemPic"));
                                model.setItemDescirption((String) document.get("description"));
                                model.setItemName((String) document.get("itemName"));

                                musicList.add(model);

                                itemListAdspter adpter = new itemListAdspter(musicList);
                                lists.setLayoutManager(new LinearLayoutManager(ItemList.this));
                                lists.setItemAnimator(new DefaultItemAnimator());
                                lists.setAdapter(adpter);
                            }
                        } else {
                            System.out.println("----*****-----get failed with "+ task.getException());
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        return null;
    }
}