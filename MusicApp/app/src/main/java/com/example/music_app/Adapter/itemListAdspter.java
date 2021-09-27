package com.example.music_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_app.R;
import com.example.music_app.item_details_screen;
import com.example.music_app.model.MusicModel;
import com.example.music_app.model.itemModel;

import java.util.List;

public class itemListAdspter extends  RecyclerView.Adapter<itemListAdspter.viewHolders> {

    List<itemModel> musicItemList;
    Context context;

    public itemListAdspter(List<itemModel> musicItemList) {
        this.musicItemList = musicItemList;
    }

    @NonNull
    @Override
    public viewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistadapter,parent,false);
        viewHolders viewHolder = new viewHolders(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolders holder, int position) {
        itemModel model =  musicItemList.get(position);

        final String pureBase64Encoded = model.getImageValue().substring(model.getImageValue().indexOf(",")  + 1);
        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);

        holder.mainImage.setImageBitmap(decodedByte);

        holder.itemName.setText(model.getItemName());
        holder.itemDetails.setText(model.getItemDescirption());
        holder.viewItemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(holder.itemView.getContext(), item_details_screen.class);
//                intent.putExtra("itemID",model.getCollectionID());
//                context.startActivity(intent);

                Toast t = Toast.makeText(holder.itemView.getContext(),"item name "+model.getItemName()+" collection id "+model.getCollectionID(),Toast.LENGTH_LONG);
                t.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicItemList.size();
    }

    public class viewHolders extends RecyclerView.ViewHolder {
        ImageView mainImage;
        TextView itemName, itemDetails;
        Button viewItemInfo;

        public viewHolders(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemDetails = itemView.findViewById(R.id.itemdescription);
            viewItemInfo = itemView.findViewById(R.id.viewInfoDetails);
        }
    }
}
