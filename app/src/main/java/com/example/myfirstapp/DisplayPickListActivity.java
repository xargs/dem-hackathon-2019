package com.example.myfirstapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.URLEncoder;

import http.HttpConnection;
import http.HttpConnectionFactory;
import json.JsonConstants;

public class DisplayPickListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pick_list);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
            super.onPostExecute(s);
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
                HttpConnection connection = HttpConnectionFactory.getPickRequestHttpConnection();
                connection.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("uuid", "{{$guid}}");
                jsonParam.put("userName", JsonConstants.HACKER_10);
                jsonParam.put("terminalId", "5036");
                jsonParam.put("pickWalkId", "000000000000131");


                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(connection.getResponseCode()));
                Log.i("MSG" , connection.getResponseMessage());

                message = connection.getResponseMessage();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return message;
        }
    }

}
