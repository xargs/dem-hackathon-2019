package com.example.myfirstapp.picklist.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import sms.SMSSender;

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
//        holder.skuPic.setImageResource(R.drawable.apple);
        String skuId = row_pos.getSkuId();

        String url = new StringBuilder("http://US7813PC.amcs.tld:8080/mgtp/resources/sku-images/")
                .append(skuId)+".JPG";
        new DownloadImage(holder.skuPic).execute(url);
    }

    private class DownloadImage extends AsyncTask<String, Integer, Drawable> {

        private ImageView imageView;
        public DownloadImage(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Drawable doInBackground(String... arg0) {
            // This is done in a background thread
            return downloadImage(arg0[0]);
        }

        /**
         * Called after the image has been downloaded
         * -> this calls a function on the main thread again
         */
        protected void onPostExecute(Drawable image)
        {
            this.imageView.setImageDrawable(image);
        }


        /**
         * Actually download the Image from the _url
         * @param _url
         * @return
         */
        private Drawable downloadImage(String _url)
        {
            URL url;
            BufferedOutputStream out;
            InputStream in;
            BufferedInputStream buf;

            try {
                url = new URL(_url);
                in = url.openStream();


                buf = new BufferedInputStream(in);

                Bitmap bMap = BitmapFactory.decodeStream(buf);
                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                return new BitmapDrawable(bMap);

            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }

            return null;
        }

    }


    @Override
    public int getItemCount() {
        return rowItems.size();
    }
}
