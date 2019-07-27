package com.example.syafiqtrans;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class login_activity extends AppCompatActivity {
    RelativeLayout mRelmain;
    TextView txtdaftar;
    TextView txtLupa_password;
    PreferenceHelper mPrefHelper;

    public void onCreate(Bundle SaveInstanceState) {
        super.onCreate(SaveInstanceState);
        setContentView(R.layout.login);
        mPrefHelper = new PreferenceHelper(getApplicationContext());

        mRelmain = findViewById(R.id.relmain);
        Button btn_login = findViewById(R.id.login_btn1);

        Boolean logIs = mPrefHelper.getIsLogin();

        if (logIs){
            Intent masuk = new Intent(getApplicationContext(),BottomNavigation.class);
            startActivity(masuk);
            mPrefHelper.setIsLogin(true);
            finish();
        }

        final EditText mEmail = findViewById(R.id.email);
        final EditText mPassword = findViewById(R.id.password);
        txtdaftar = findViewById(R.id.txt_daftar);
        txtLupa_password = findViewById(R.id.txt_lupaPassword);

        txtLupa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masuk = new Intent(getApplicationContext(), daftar_activity.class);
                startActivity(masuk);
            }});

        txtdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masuk = new Intent(getApplicationContext(), daftar_activity.class);
                startActivity(masuk);
            }});

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = String.valueOf(mEmail.getText());
                Log.d("debug", "Onclick: "+sEmail);

                String sPassword = String.valueOf(mPassword.getText());
                Log.d("debug", "Onclick: "+sPassword);

                if(mEmail.getText().toString().length()==0){
                    mEmail.setError("Email diperlukan!");
                }else if(mPassword.getText().toString().length()==0){
                    mPassword.setError("Password diperlukan!");
                } else {
                    Toast.makeText(login_activity.this, "LOGIN BERHASIL", Toast.LENGTH_LONG).show();
                    getTokenRest(sEmail, sPassword);

                }}});
    }

    private void getTokenRest(final String edemail,final String edpassword) {
        String url = "http://193.168.195.201/burhan/index.php/Rest/RestAuth/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jObject = null;
                try {
                    jObject = new JSONObject(response);
                    String hasil_pesan = jObject.getString("pesan");
                    String hasil_respon = jObject.getString("respon");


                    Log.d("debug", "Onclick: "+hasil_respon);
                    Log.d("debug", "onResponse: "+hasil_pesan);


                    if (hasil_respon.equals("200")){
                        mPrefHelper.setTokenRest(hasil_pesan);
                        Log.d("debug", "USER BERHASIL MASUK: "+hasil_respon);
                        Intent masuk = new Intent(getApplicationContext(),BottomNavigation.class);
                        startActivity(masuk);

                        mPrefHelper.setIsLogin(true);
                        finish();
                    }else{
                        Snackbar snackbar = Snackbar
                                .make(mRelmain, hasil_pesan, Snackbar.LENGTH_LONG)
                                ;
                        snackbar.setActionTextColor(Color.RED);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(Color.DKGRAY);
                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();
                        Log.d("debug", "onResponse: "+hasil_pesan);
                        mPrefHelper.setIsLogin(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email_user",edemail); //Add the data you'd like to send to the server.
                MyData.put("password",edpassword); //Add the data you'd like to send to the server.
                return MyData;
            }
            public Map<String, String> getHeaders(){
                Map<String, String> MyData1 = new HashMap<>();
                MyData1.put("Authorization","Bearer " + mPrefHelper.getTokenRest());
                return MyData1;
            }
        };
        NetworkRequest.getInstance(this).addToRequestQueue(stringRequest);
    }
    }


