package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfirstapp.picklist.components.CustomAdapter;
import com.example.myfirstapp.picklist.components.CustomRecyclerViewAdapter;
import com.example.myfirstapp.picklist.components.RowItem;
import com.example.myfirstapp.picklist.components.SwipeController;
import com.example.myfirstapp.picklist.components.SwipeControllerActions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import http.HttpConnection;
import http.HttpConnectionFactory;
import json.JsonConstants;
import json.inbound.Pick;
import json.inbound.PickResponse;
import json.outbound.JsonRequestSender;

public class DisplayPickListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Context context;
//    private ListView listView;
    private CustomRecyclerViewAdapter adapter;
    SwipeController swipeController = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_display_pick_list);
        this.context = this;
//        listView = (ListView) findViewById(R.id.list);
//        listView.setOnItemClickListener(this);

        new RestRequestAsyncTask().execute();


    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                adapter.rowItems.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
                String key = adapter.rowItems.get(position).getKey();
                int quantity = adapter.rowItems.get(position).getQuantity();
                try {
                    new JsonRequestSender().sendConfirmPickRequest(JsonConstants.CONFIRMATION_CODE,key,quantity);
                } catch (Exception e) {
                    Log.e(DisplayPickListActivity.class.getName(),e.getMessage(),e);
                }
            }

        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("Hello","Item Clicked");
    }

    private class RestRequestAsyncTask extends AsyncTask<String,Void,String>{
        public RestRequestAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            PickResponse response= new Gson().fromJson(s, PickResponse.class);

            if(response != null && response.getStateCode() != null
                    && "OK".equals(response.getStateCode().getValue()) ){
                //Log.i("PickRequest","Success");
               if(response.getPicks() != null && response.getPicks().size() > 0){
                   List<RowItem> list = new ArrayList<RowItem>();
                   for(Pick pick : response.getPicks()){
                       RowItem item = new RowItem();
                       item.setCoordinate(pick.getCoordinate());
                       item.setDestinationId(pick.getDestinationId());
                       item.setKey(pick.getPrimaryKey());
                       item.setOrderId(pick.getOrderId());
                       item.setOrderPosition(pick.getOrderPos());
                       item.setQuantity(pick.getQuantityTarget());
                       item.setSequence(pick.getSequence());
                       item.setSkuDescription(pick.getSkuDescription());
                       item.setSkuId(pick.getSkuId());
                       item.setUnit(pick.getQuantityUnit());
                       list.add(item);
                   }
//                   CustomAdapter adapter = new CustomAdapter(context, list);
                   adapter = new CustomRecyclerViewAdapter(list);
//                   listView.setAdapter(adapter);
                   setupRecyclerView();
               }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... params) {
            String message = null;
            try {
//                HttpConnection connection = HttpConnectionFactory.getPickRequestHttpConnection();
//                connection.connect();
//
//                JSONObject jsonParam = new JSONObject();
//                jsonParam.put("uuid", "{{$guid}}");
//                jsonParam.put("userName", JsonConstants.HACKER_10);
//                jsonParam.put("terminalId", "5036");
//                jsonParam.put("pickWalkId", "000000000000131");
//
//
//                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
//                os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
//
//                os.flush();
//                os.close();
//
//                //Log.i("STATUS", String.valueOf(connection.getResponseCode()));
//                //Log.i("MSG" , connection.getResponseMessage());
//
//                message = connection.getResponseMessage();
//                connection.disconnect();
//                JsonRequestSender sender = new JsonRequestSender();
//                message = sender.sendPickRequest("5036","000000000000131");

                message = "{\n" +
                        "    \"originalResponseGeneratedOn\": 1560622005043,\n" +
                        "    \"isOriginalResponse\": true,\n" +
                        "    \"processTimeInMS\": 15,\n" +
                        "    \"totalTimeInMS\": 15,\n" +
                        "    \"messageKey\": \"APP-II-0000\",\n" +
                        "    \"messageText\": \"OK\",\n" +
                        "    \"token\": null,\n" +
                        "    \"stateCode\": {\n" +
                        "        \"value\": \"OK\",\n" +
                        "        \"type\": \"com.dematic.wms.pic.person2goods.entity.domainvalue.PersonToGoodsResponseStateCode\",\n" +
                        "        \"features\": null\n" +
                        "    },\n" +
                        "    \"message\": null,\n" +
                        "    \"picks\": [\n" +
                        "        {\n" +
                        "            \"primaryKey\": \"000000000009562\",\n" +
                        "            \"sequence\": 0,\n" +
                        "            \"coordinate\": \"2-1-254-1-1\",\n" +
                        "            \"checkDigit\": null,\n" +
                        "            \"quantityTarget\": 1,\n" +
                        "            \"quantityUnit\": \"EACH\",\n" +
                        "            \"skuId\": \"2100001865\",\n" +
                        "            \"skuDescription\": \"KRAFT NAT CHS SLICE PROVOLONE 8 OZ\",\n" +
                        "            \"orderId\": \"15149326\",\n" +
                        "            \"orderPos\": \"0001\",\n" +
                        "            \"destinationId\": \"PC000003\",\n" +
                        "            \"destinationCheckDigit\": null,\n" +
                        "            \"reverseRecommended\": true\n" +
                        "        },\n" +
                        "{\n" +
                "            \"primaryKey\": \"000000000009562\",\n" +
                        "            \"sequence\": 0,\n" +
                        "            \"coordinate\": \"2-1-300-1-1\",\n" +
                        "            \"checkDigit\": null,\n" +
                        "            \"quantityTarget\": 5,\n" +
                        "            \"quantityUnit\": \"EACH\",\n" +
                        "            \"skuId\": \"2100001865\",\n" +
                        "            \"skuDescription\": \"PRODUCE,GRAPES AVOCADOS SOFT FRUIT,Refridgerated\",\n" +
                        "            \"orderId\": \"15149326\",\n" +
                        "            \"orderPos\": \"0001\",\n" +
                        "            \"destinationId\": \"PC000003\",\n" +
                        "            \"destinationCheckDigit\": null,\n" +
                        "            \"reverseRecommended\": true\n" +
                        "        }\n"+",\n" +
                "{\n" +
                        "            \"primaryKey\": \"000000000009562\",\n" +
                        "            \"sequence\": 0,\n" +
                        "            \"coordinate\": \"2-1-104-1-1\",\n" +
                        "            \"checkDigit\": null,\n" +
                        "            \"quantityTarget\": 1,\n" +
                        "            \"quantityUnit\": \"EACH\",\n" +
                        "            \"skuId\": \"2100001865\",\n" +
                        "            \"skuDescription\": \"DAIRY,JUICE AND JUICE DRINK RFRG,Refridgerated\",\n" +
                        "            \"orderId\": \"15149326\",\n" +
                        "            \"orderPos\": \"0001\",\n" +
                        "            \"destinationId\": \"PC000003\",\n" +
                        "            \"destinationCheckDigit\": null,\n" +
                        "            \"reverseRecommended\": true\n" +
                        "        }\n" +                        "    ]\n" +
                        "}";
//                HttpConnection connection = HttpConnectionFactory.getTestPickResponse();
//                connection.setRequestMethod(HttpConnection.REQUEST_METHOD_GET);
//                connection.connect();
//
//                message = connection.getResponseMessage();
//                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return message;
        }
    }

}
