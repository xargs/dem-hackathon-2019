package com.example.myfirstapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.myfirstapp.databinding.ActivityPickDetailsBinding;

public class PickActivity extends AppCompatActivity {

    private Context context;
    private TextView textView;
    private ActivityPickDetailsBinding activityPickDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPickDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_pick_details);
        activityPickDetailsBinding.skuValue.setText("12345678");
        activityPickDetailsBinding.skuDescriptionValue.setText("this is where the reallllly long description of sku goes.....");
//        activityPickDetailsBinding.skuDesc.setText("Description");
    }
}
