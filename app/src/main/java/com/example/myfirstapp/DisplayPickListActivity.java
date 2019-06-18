package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.myfirstapp.picklist.components.CustomRecyclerViewAdapter;
import com.example.myfirstapp.picklist.components.RowItem;
import com.example.myfirstapp.picklist.components.SwipeController;
import com.example.myfirstapp.picklist.components.SwipeControllerActions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import json.JsonConstants;
import json.inbound.FinishItem;
import json.inbound.Pick;
import json.inbound.PickResponse;
import json.inbound.PickWalkFinishResponse;
import json.outbound.JsonRequestSender;
import sms.SMSSender;

public class DisplayPickListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Context context;
//    private ListView listView;
    private CustomRecyclerViewAdapter adapter;
    SwipeController swipeController = null;
    private JsonRequestSender sender ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_display_pick_list);
        this.context = this;
//        listView = (ListView) findViewById(R.id.list);
//        listView.setOnItemClickListener(this);
        sender = new JsonRequestSender();
        String pickWalkId = getIntent().getStringExtra("pickWalkId");
        new RestRequestAsyncTask().execute(pickWalkId);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);


        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                String sku = adapter.rowItems.get(position).getSkuDescription();
                adapter.rowItems.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());

                String message = "The item "+ sku + " is currently unavailable."+
                            "Apologies for the inconvenience";
                SMSSender.sendSMS(getResources().getString(R.string.phone),message);
            }

            @Override
            public void onLeftClicked(int position) {
                String type = JsonCallsAsyncTask.CONFIRM_PICK;
                String key = adapter.rowItems.get(position).getKey();
                int quantity = adapter.rowItems.get(position).getQuantity();
                new JsonCallsAsyncTask().execute(type,key,String.valueOf(quantity), String.valueOf(position));
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
                   list.sort(new Comparator<RowItem>() {
                       @Override
                       public int compare(RowItem item1, RowItem item2) {
                           return item1.getCoordinate().compareTo(item2.getCoordinate());
                       }
                   });
//                   CustomAdapter adapter = new CustomAdapter(context, list);
                   adapter = new CustomRecyclerViewAdapter(context,list);
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
            try {
                return new JsonRequestSender().sendPickRequest(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class JsonCallsAsyncTask extends AsyncTask<String,Void,String>{
        public static final String CONFIRM_PICK = "ConfirmPick";
        public static final String PICK_WALK_FINISH = "PickWalkFinish";
        private int position;
        public JsonCallsAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if(CONFIRM_PICK.equals(s)) {
                postCompleteConfirmPick();
            }else if(PICK_WALK_FINISH.equals(s)){
                postCompletePickWalkFinish();
            }
        }

        private void postCompleteConfirmPick(){
            adapter.rowItems.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, adapter.getItemCount());

            if(adapter.rowItems.size() == 0){
                Intent intent = getIntent();
                String pickWalkId = intent.getStringExtra("pickWalkId");
                String type = PICK_WALK_FINISH;
                new JsonCallsAsyncTask().execute(type,pickWalkId);
            }
        }

        private void postCompletePickWalkFinish(){

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
        protected String doInBackground(String... strings) {

            String returnVal = null;
            String type = strings[0];
            if(CONFIRM_PICK.equals(type)) {
                this.position = Integer.parseInt(strings[3]);
                try {
                    sender.sendConfirmPickRequest(JsonConstants.CONFIRMATION_CODE, strings[0], Integer.valueOf(strings[1]));
                } catch (Exception e) {
                    Log.e(DisplayPickListActivity.class.getName(), e.getMessage(), e);
                }
                returnVal = CONFIRM_PICK;
            }else if(PICK_WALK_FINISH.equals(type)){
                try {
                    String response = sender.sendPickWalkFinishRequest(strings[1]);
                    PickWalkFinishResponse pickWalkFinishResponse = new Gson().
                            fromJson(response,PickWalkFinishResponse.class);
                    List<FinishItem> finishItems = pickWalkFinishResponse.getFinishItems();
                    List<String> containerIds = new ArrayList<>();
                    String destinationLocation = null;
                    for(FinishItem finishItem : finishItems){
                        if(destinationLocation == null){
                            destinationLocation = finishItem.getDestinationLocationId();
                        }
                        containerIds.add(finishItem.getPickContainerId());
                    }
                    //String response2 = sender.sendPickContainerConfirmationRequest(destinationLocation,containerIds);
                    runThread(destinationLocation);

                } catch (Exception e) {
                    Log.e(DisplayPickListActivity.class.getName(), e.getMessage(), e);
                }
                returnVal = PICK_WALK_FINISH;
            }
            return returnVal;
        }
    }

    private void runThread(final String destinationLocation) {

        new Thread() {
            public void run() {
                View view = findViewById(R.id.frameLayout);
                Snackbar.make(view,"Order completed and sent to "+destinationLocation,Snackbar.LENGTH_LONG).show();
            }
        }.start();
    }


}
