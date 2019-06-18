package com.example.myfirstapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import sms.SMSSender;

public class ReplaceActivity extends AppCompatActivity implements View.OnClickListener {
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private TextView newSkuId = null;
    private SurfaceView _surfaceView;
    private Button smsButton;
    private TextView currentSkuTxt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace);
        Intent intent = getIntent();
        String currentSku = intent.getStringExtra("currentSku");
        String skuDesc = intent.getStringExtra("skuDesc");
        currentSkuTxt = (TextView)findViewById(R.id.currentSkuId);
        currentSkuTxt.setText(currentSku + " - "+skuDesc);
        newSkuId = (TextView)findViewById(R.id.skuId);
        _surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        smsButton = (Button)findViewById(R.id.smsButton);
        smsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String currentItem = currentSkuTxt.getText().toString().split("-")[1];
        String newItem = currentItem;
        String message = " The item "+currentItem +" is unavailable. Do you want to "+
                " substitue "+currentItem +" with "+currentItem +"? Please reply YES to proceed.";
        SMSSender.sendSMS(getResources().getString(R.string.phone),message);
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
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
                    if (ActivityCompat.checkSelfPermission(ReplaceActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(_surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ReplaceActivity.this, new
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
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    newSkuId.setText(barcodes.valueAt(0).displayValue);
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
