package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myfirstapp.databinding.ActivityPickDetailsBinding;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import json.inbound.Pick;
import json.inbound.PickResponse;
import json.outbound.JsonRequestSender;
import sms.SMSSender;

import static json.JsonConstants.CONFIRMATION_CODE;
import static json.JsonConstants.PHONE_NUMBER;

public class PickActivity extends AppCompatActivity {
    private static final String TAG = "PickActivity";
    private Context context;
    private TextView textView;
    private ActivityPickDetailsBinding activityPickDetailsBinding;
    private Pick currentPick;

    @Bind(R.id.buttonPick) Button _pickButton;
    @Bind(R.id.buttonDelete) Button _deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPickDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_pick_details);
        ButterKnife.bind(this);
        _pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick();
            }
        });
        _deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    public void pick() {
        Log.d(TAG, "Pick");
        _pickButton.setEnabled(false);
        new JsonPickCompleteRequestAsyncTask().execute();
    }

    public void delete() {
        Log.d(TAG, "Delete");
        _deleteButton.setEnabled(false);
        new JsonPickDeleteRequestAsyncTask().execute();
    }

    public void onPickComplete(String r) {
        PickResponse response = new Gson().fromJson(r, PickResponse.class);
        if (response != null
                && response.getMessageText().equals(CONFIRMATION_CODE)
                && response.getStateCode().getValue().equals(CONFIRMATION_CODE)) {
            Intent intent = new Intent(this, DisplayPickListActivity.class);
            intent.putExtra("pickWalkId", currentPick.getPickWalkId());
            startActivity(intent);
        }
    }

    public void onPickDelete() {
        Intent intent = new Intent(this, DisplayPickListActivity.class);
        intent.putExtra("pickWalkId", currentPick.getPickWalkId());
        startActivity(intent);
    }

    public class JsonPickCompleteRequestAsyncTask extends AsyncTask<String,Void,String> {

        public JsonPickCompleteRequestAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String r) {
            onPickComplete(r);
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
                return new JsonRequestSender().sendConfirmPickRequest(CONFIRMATION_CODE, currentPick.getPrimaryKey(), currentPick.getQuantityTarget());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class JsonPickDeleteRequestAsyncTask extends AsyncTask<String,Void,String> {

        public JsonPickDeleteRequestAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String r) {
            onPickDelete();
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
                SMSSender.sendSMS(PHONE_NUMBER, "Hello, Would you like to substitute item 1 with item 2 ?");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
