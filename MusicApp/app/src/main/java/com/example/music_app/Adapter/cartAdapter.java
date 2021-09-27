package com.example.music_app.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_app.R;
import com.example.music_app.model.cartModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cartAdapter  extends  RecyclerView.Adapter<cartAdapter.cartViewHolder>{


    FirebaseFirestore fStore;
    List<cartModel> cartModelList;
    Context context;

    public cartAdapter(List<cartModel> cartModelList) {
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        fStore = FirebaseFirestore.getInstance();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartadapter,parent,false);
        cartViewHolder cartViewHolder = new cartViewHolder(view);
        context = parent.getContext();
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull cartViewHolder holder, int position) {
            cartModel model = cartModelList.get(position);

//        final String pureBase64Encoded = model.getImageData().substring(model.getImageData().indexOf(",")  + 1);
//        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
//
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
//
//        holder.itemImage.setImageBitmap(decodedByte);


            holder.itemName.setText(model.getItemName());
            holder.pricese.setText( "Rs : "+model.getItemPrice());
            holder.cartQtys.setText(model.getQty()+"");

            holder.addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = holder.cartQtys.getText().toString();
                    int qtys = Integer.parseInt(s);
                    qtys = qtys+1;

                    holder.cartQtys.setText(qtys+"");

                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("qty", qtys);

                    fStore.collection("cart").document(model.getCartId()).update(data1);


                }
            });

            holder.removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = holder.cartQtys.getText().toString();
                    int qty = Integer.parseInt(s);
                    if(qty <= 0){
                        qty = 0;
                    }else {
                        qty = qty-1;
                    }

                    holder.cartQtys.setText(qty+"");

                    Map<String, Object> data1 = new HashMap<>();
                    data1.put("qty", qty);

                    fStore.collection("cart").document(model.getCartId()).update(data1);
                }
            });

            holder.deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fStore.collection("cart").document(model.getCartId())
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
        return cartModelList.size();
    }

    public class cartViewHolder  extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView itemName, pricese, cartQtys;
        Button addItem, removeItem, deleteItem;

        public cartViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.cartItemImage);
            itemName = itemView.findViewById(R.id.itemName);
            pricese = itemView.findViewById(R.id.txt5);
            cartQtys = itemView.findViewById(R.id.cartQty);
            addItem  = itemView.findViewById(R.id.addQty);
            removeItem = itemView.findViewById(R.id.removeQty);
            deleteItem = itemView.findViewById(R.id.removeCart);
        }
    }
}
