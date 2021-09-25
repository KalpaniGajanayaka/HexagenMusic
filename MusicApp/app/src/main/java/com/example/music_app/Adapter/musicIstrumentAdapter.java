package com.example.music_app.Adapter;

import android.content.Context;
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
import com.example.music_app.model.MusicModel;

import java.util.List;

public class musicIstrumentAdapter extends RecyclerView.Adapter<musicIstrumentAdapter.musicViewHolder> {

    List<MusicModel> musicItemList;
    Context context;

    public musicIstrumentAdapter(List<MusicModel> lists){
        this.musicItemList = lists;
    }

    @NonNull
    @Override
    public musicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instrument_adapter,parent,false);
        musicViewHolder musicViewHolder = new musicViewHolder(view);
        context = parent.getContext();
        return musicViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull musicViewHolder holder, int position) {
        MusicModel model =  musicItemList.get(position);

        holder.itemName.setText(model.getItemName());
        holder.itemDetails.setText(model.getItemDescirption());
        holder.viewItemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(holder.itemView.getContext(),"item name "+model.getItemName(),Toast.LENGTH_LONG);
                t.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return musicItemList.size();
    }

    class musicViewHolder extends RecyclerView.ViewHolder{

        ImageView mainImage;
        TextView itemName, itemDetails;
        Button viewItemInfo;

        public musicViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemDetails = itemView.findViewById(R.id.itemdescription);
            viewItemInfo = itemView.findViewById(R.id.viewInfoDetails);
        }
    }

}


