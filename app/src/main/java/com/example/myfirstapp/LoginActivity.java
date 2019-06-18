package com.example.myfirstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import json.inbound.ConfigurationResponse;
import json.inbound.PickWalkResponse;
import json.inbound.RegisterResponse;
import json.outbound.JsonRequestSender;

import static json.JsonConstants.CONFIRMATION_CODE;
import static json.JsonConstants.RF_PK_ZONE;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.btn_login) Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");

        new JsonRegisterRequestAsyncTask().execute();

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    private void onSuccessFullLoginAndGettingPickWalkRequest(String r) {
        PickWalkResponse pickWalkResponse = new Gson().fromJson(r, PickWalkResponse.class);
        if(pickWalkResponse != null && pickWalkResponse.getMessageText().equals(CONFIRMATION_CODE)
                && pickWalkResponse.getStateCode().getValue().equals(CONFIRMATION_CODE)) {
            Intent intent = new Intent(this, ScanContainerActivity.class);
            intent.putExtra("pickWalkId", pickWalkResponse.getPickWalkId());
            intent.putExtra("unitType", pickWalkResponse.getDefaultPickContainerType());
            startActivity(intent);
        }
    }

    public class JsonRegisterRequestAsyncTask extends AsyncTask<String,Void,String> {

        public JsonRegisterRequestAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String r) {
            onSuccessFullLoginAndGettingPickWalkRequest(r);
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
                String response = new JsonRequestSender().sendRegisterRequest();
                if(response != null) {
                    RegisterResponse registerResponse = new Gson().fromJson(response, RegisterResponse.class);
                    if(registerResponse != null && registerResponse.getMessageText().equals(CONFIRMATION_CODE)
                            && registerResponse.getStateCode().getValue().equals(CONFIRMATION_CODE)) {
                        String response1 = new JsonRequestSender().sendConfigurationRequest();
                        if(response1 != null) {
                            ConfigurationResponse configurationResponse = new Gson().fromJson(response1, ConfigurationResponse.class);
                            if(configurationResponse != null && configurationResponse.getMessageText().equals(CONFIRMATION_CODE)
                                    && configurationResponse.getStateCode().getValue().equals(CONFIRMATION_CODE)) {
                                if(configurationResponse.getAllowedPickZones() != null
                                        && configurationResponse.getAllowedPickZones().size() > 0
                                        && configurationResponse.getAllowedPickZones().get(0).getName().equals(RF_PK_ZONE)) {
                                    String response2 = new JsonRequestSender().sendPickWalkRequest();
                                    if(response2 != null) {
                                        return response2;
                                    }
                                }
                            }
                        }
                    } else {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
