package com.example.music_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_app.R;
import com.example.music_app.model.OderModel;
import com.example.music_app.model.cartModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class oderAdapter extends RecyclerView.Adapter<oderAdapter.oderViewHolder> {

    FirebaseFirestore fStore;
    List<OderModel> oderModelList;
    Context context;

    String collectionID = null;


    public oderAdapter(List<OderModel> oderModelList) {
        this.oderModelList = oderModelList;
    }

    @NonNull
    @Override
    public oderAdapter.oderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        fStore = FirebaseFirestore.getInstance();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oderhistoryadapter,parent,false);
        oderViewHolder orderviewHolder = new oderViewHolder(view);
        context = parent.getContext();
        return orderviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull oderAdapter.oderViewHolder holder, int position) {

        OderModel model = oderModelList.get(position);

        holder.txt1.setText(model.getItemName());

        holder.view.setVisibility(View.INVISIBLE);

 //       final String pureBase64Encoded = model.getImageValue().substring(model.getImageValue().indexOf(",")  + 1);
//        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
//
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
//
//        holder.imageView.setImageBitmap(decodedByte);

        collectionID = model.getCollectionID();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("orders").document(collectionID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                System.out.println("DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Error deleting document ---- "+e);
                                // Log.w(TAG, "Error deleting document", e);
                            }
                        });
            }
        });


    }

    @Override
    public int getItemCount() {
        return oderModelList.size();
    }

    public class oderViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txt1;
        Button view, remove;

        public oderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            txt1 = itemView.findViewById(R.id.txt1);
            view = itemView.findViewById(R.id.btn1);
            remove = itemView.findViewById(R.id.button12);
        }
    }
}
