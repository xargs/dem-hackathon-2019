package com.example.myfirstapp.picklist.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.R;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.RowItemViewHolder> {
    public List<RowItem> rowItems;

    public class RowItemViewHolder extends RecyclerView.ViewHolder {
        ImageView skuPic;
        TextView skuDesc;
        TextView quantiy;
        TextView unit;
        TextView coordinate;

        public RowItemViewHolder(View view) {
            super(view);
            skuPic = (ImageView)view.findViewById(R.id.sku_pic);
            skuDesc = (TextView) view.findViewById(R.id.sku_desc);
            quantiy = (TextView)view.findViewById(R.id.quantity);
            unit = (TextView)view.findViewById(R.id.unit);
            coordinate = (TextView)view.findViewById(R.id.coordinate);

        }
    }

    public CustomRecyclerViewAdapter(List<RowItem> rowItems) {
        this.rowItems = rowItems;
    }

    @Override
    public RowItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_display_pick_list_2, parent, false);

        return new RowItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerViewAdapter.RowItemViewHolder holder, int position) {
        RowItem row_pos = rowItems.get(position);
//        RowItemViewHolder holder = (RowItemViewHolder)rHolder;
        holder.skuDesc.setText(row_pos.getSkuDescription());
        holder.quantiy.setText(String.valueOf(row_pos.getQuantity())+" ");
        holder.unit.setText(row_pos.getUnit());
        holder.coordinate.setText(row_pos.getCoordinate());
        holder.skuPic.setImageResource(R.drawable.apple);
        String skuId = row_pos.getSkuId();
    }


    @Override
    public int getItemCount() {
        return rowItems.size();
    }
}
