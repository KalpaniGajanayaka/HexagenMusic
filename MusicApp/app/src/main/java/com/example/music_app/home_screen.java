package com.example.music_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_app.Adapter.musicIstrumentAdapter;
import com.example.music_app.model.MusicModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class home_screen extends AppCompatActivity {

    EditText serchText;
    RecyclerView lists;
    ArrayList<MusicModel> musicList;

    private FirebaseFirestore db;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        musicList =  new ArrayList<MusicModel>();

        init();
        getMusicDataFromFireStore();

        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.nav_home:
                        Intent ix = new Intent(home_screen.this,rent_details.class); // item reg screen
                        startActivity(ix);
                       // Toast.makeText(home_screen.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_gallery:
                        Intent i = new Intent(home_screen.this,view_profile_screen.class);
                        startActivity(i);
                    case R.id.nav_slideshow:
                        Intent ii = new Intent(home_screen.this,feedback_screen.class);
                        startActivity(ii);
                    case R.id.nav_cart:
                        Intent iii = new Intent(home_screen.this,cart_screen.class);
                        startActivity(iii);
                    case R.id.nav_itemreg:
                        Intent iv = new Intent(home_screen.this,item_register.class); // item reg screen
                        startActivity(iv);
                    case R.id.nav_oderhistory:
                        Intent v = new Intent(home_screen.this,oder_history.class);
                        startActivity(v); //nav_item_rent
                    case R.id.nav_item_rent:
                        Intent vi = new Intent(home_screen.this,rent_details.class);
                        startActivity(vi);
                    default:
                        return true;
                }


                //return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        db = FirebaseFirestore.getInstance();
    }

    public ArrayList<MusicModel> getMusicDataFromFireStore(){

        System.out.println("----calling method");

//        DocumentReference docRef = db.collection("item").document("item001");
//
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    System.out.println("------- documet "+document.toString());
//                    if (document.exists()) {
//                        System.out.println(" +++++ DocumentSnapshot data: " + document.getData());
//                    } else {
//                        System.out.println("******** No such document");
//                    }
//                } else {
//                    System.out.println("----*****-----get failed with "+ task.getException());
//                }
//
//            }
//        });

        db.collection("item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println(document.getId() +"+++++ DocumentSnapshot data: " + document.getData());
                                MusicModel model = new MusicModel();

                                String ids = document.get("itemId").toString();

                                model.setCollectionID(document.getId());
                                model.setItemId(Integer.parseInt(ids));
                                model.setImageValue((String) document.get("itemPic"));
                                model.setItemDescirption((String) document.get("description"));
                                model.setItemName((String) document.get("itemName"));

                                musicList.add(model);

                                musicIstrumentAdapter adpter = new musicIstrumentAdapter(musicList);

                                lists = findViewById(R.id.musicListView);
                                lists.setLayoutManager(new LinearLayoutManager(home_screen.this));
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