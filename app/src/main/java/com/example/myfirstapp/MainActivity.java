package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button _loginButton = (Button) findViewById(R.id.button);

        _loginButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent inent = new Intent(this, LoginActivity.class);

        startActivity(inent);
    }
}
