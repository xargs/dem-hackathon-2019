package com.example.myfirstapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myfirstapp.databinding.ActivityPickDetailsBinding;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;

import java.io.IOException;

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
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    @Bind(R.id.buttonPick) Button _pickButton;
    @Bind(R.id.buttonDelete) Button _deleteButton;
    @Bind(R.id.surfaceView) SurfaceView _surfaceView;
    @Bind(R.id.txtBarcodeValue) TextView _txtBarcodeValue;
    @Bind(R.id.buttonReplace) Button _replaceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPickDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_pick_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Pick pick = (Pick) intent.getSerializableExtra("pick");
        currentPick = pick;
        activityPickDetailsBinding.skuValue.setText(pick.getSkuId());
        activityPickDetailsBinding.skuDescriptionValue.setText(pick.getSkuDescription());
        activityPickDetailsBinding.qtyValue.setText(String.valueOf(pick.getQuantityTarget()));
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
        _replaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace();
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

    public void replace(){
        Intent intent = new Intent(this,ReplaceActivity.class);
        intent.putExtra("currentSku",currentPick.getSkuId());
        intent.putExtra("skuDesc",currentPick.getSkuDescription());
        startActivity(intent);
    }

    public void onPickComplete(String r) {
        PickResponse response = new Gson().fromJson(r, PickResponse.class);
        if (response != null
                && response.getMessageText().equals(CONFIRMATION_CODE)) {
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

    private void initialiseDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        _surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(PickActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(_surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(PickActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    _txtBarcodeValue.setText(barcodes.valueAt(0).displayValue);
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }
}
