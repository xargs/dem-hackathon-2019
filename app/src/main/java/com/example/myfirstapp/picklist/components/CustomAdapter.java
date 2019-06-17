package com.example.myfirstapp.picklist.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<RowItem> rowItems;

    public CustomAdapter(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView skuPic;
        TextView skuDesc;
        TextView quantiy;
        TextView unit;
        TextView coordinate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_display_pick_list, null);
            holder = new ViewHolder();

            holder.skuDesc = (TextView) convertView.findViewById(R.id.sku_desc);
            holder.skuPic = (ImageView) convertView.findViewById(R.id.sku_pic);
            holder.quantiy = (TextView) convertView.findViewById(R.id.quantity);
            holder.unit = (TextView) convertView.findViewById(R.id.unit);
            holder.coordinate = (TextView)convertView.findViewById(R.id.coordinate) ;

            RowItem row_pos = rowItems.get(position);

            holder.skuDesc.setText(row_pos.getSkuDescription());
            holder.quantiy.setText(String.valueOf(row_pos.getQuantity())+" ");
            holder.unit.setText(row_pos.getUnit());
            holder.coordinate.setText(row_pos.getCoordinate());
            String skuId = row_pos.getSkuId();
            String url = new StringBuilder("http://US7813PC.amcs.tld:8080/mgtp/resources/sku-images/")
                                .append(skuId)+".JPG";
//            String url = "file:///Users/kaimalrk/gettyimages-186843005-612x612.jpg";
            new DownloadImage(holder.skuPic).execute(url);
//            holder.skuPic.setImageResource(R.drawable.apple);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
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
}
