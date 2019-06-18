package com.example.myfirstapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
import json.inbound.AssignPickContainerResponse;
import json.inbound.Pick;
import json.outbound.JsonRequestSender;

import static json.JsonConstants.CONFIRMATION_CODE;

public class ScanContainerActivity extends AppCompatActivity {
    private static final String TAG = "PickActivity";
    private Context context;
    private TextView textView;
    private ActivityPickDetailsBinding activityPickDetailsBinding;
    private Pick currentPick;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private String pickWalkId;
    private String unitType;

    @Bind(R.id.surfaceViewScanContainer) SurfaceView _surfaceViewScanContainer;
    @Bind(R.id.txtBarcodeValueScanContainer) TextView _txtBarcodeValueScanContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_container);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        pickWalkId = intent.getStringExtra("pickWalkId");
        unitType = intent.getStringExtra("unitType");
    }

    private void onAssignComplete(String r) {
        AssignPickContainerResponse assignPickContainerResponse = new Gson().fromJson(r, AssignPickContainerResponse.class);
        if(assignPickContainerResponse != null
        && assignPickContainerResponse.getMessageText().equals(CONFIRMATION_CODE)
        && assignPickContainerResponse.getStateCode().getValue().equals(CONFIRMATION_CODE)) {
            Intent intent = new Intent(this, DisplayPickListActivity.class);
            intent.putExtra("pickWalkId", pickWalkId);
            startActivity(intent);
        }
    }

    public class JsonOnScanCompleteAsyncTask extends AsyncTask<String,Void,String> {

        public JsonOnScanCompleteAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String r) {
            onAssignComplete(r);
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
                String barcode = params[0];
                return new JsonRequestSender().sendAssignPickContainerRequest(pickWalkId, barcode, unitType,"1");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void onScan(String barcode) {

    }

    private void initialiseDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        _surfaceViewScanContainer.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanContainerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(_surfaceViewScanContainer.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScanContainerActivity.this, new
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
                    new JsonOnScanCompleteAsyncTask().execute(barcodes.valueAt(0).displayValue);
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
