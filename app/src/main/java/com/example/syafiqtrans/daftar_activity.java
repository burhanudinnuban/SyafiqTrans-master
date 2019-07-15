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

public class daftar_activity extends AppCompatActivity {
    EditText mInputPassword;
    EditText mInputUsername;
    EditText mInputEmail;
    RelativeLayout mRelmain1;

    public void onCreate(Bundle SaveInstanceState) {
        super.onCreate(SaveInstanceState);
        setContentView(R.layout.daftar);

        mInputEmail = findViewById(R.id.input_email);
        mInputPassword = findViewById(R.id.input_password);
        mInputUsername = findViewById(R.id.input_username);
        Button btn_login = findViewById(R.id.create_btn);
        mRelmain1 = findViewById(R.id.Relmain1);
        TextView mText = findViewById(R.id.loginbutton);

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        Intent daftar = new Intent(daftar_activity.this,login_activity.class);
                startActivity(daftar);
            }});
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = String.valueOf(mInputEmail.getText());
                Log.d("debug", "Onclick: "+sEmail);

                String sPassword = String.valueOf(mInputPassword.getText());
                Log.d("debug", "Onclick: "+sPassword);

                String sUsername = String.valueOf(mInputUsername.getText());
                Log.d("debug", "Onclick: "+sUsername);

                if(mInputEmail.getText().toString().length()==0){
                    mInputEmail.setError("Email diperlukan!");
                }else if(mInputPassword.getText().toString().length()==0){
                    mInputPassword.setError("Username diperlukan!");
                }else if(mInputUsername.getText().toString().length()==0){
                    mInputUsername.setError("Password diperlukan!");
                }
                else {
                    Toast.makeText(daftar_activity.this, "AKUN BERHASIL DIBUAT SILAHKAN CEK EMAIL UNTUK KONFIRMASI AKUN!!!", Toast.LENGTH_LONG).show();
                    getTokenRest(sEmail, sPassword, sUsername);
                }
            }});
    }

    private void getTokenRest(final String edemail,final String edpassword,final String edusername) {
        String url = "http://193.168.195.201/burhan/index.php/Rest/RestAuth/register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jObject = null;
                try {
                    jObject = new JSONObject(response);
                    String hasil_pesan = jObject.getString("pesan");
                    String hasil_respon = jObject.getString("respon");
                    Log.d("debug", "Onclick: "+hasil_respon);


                    if (hasil_respon.equals("200")){
                        Log.d("debug", "USER BERHASIL MENDAFTAR: "+hasil_respon);
                        Intent daftar = new Intent(getApplicationContext(),login_activity.class);
                        startActivity(daftar);
                        finish();
                    }else{
                        Snackbar snackbar = Snackbar
                                .make(mRelmain1, hasil_pesan, Snackbar.LENGTH_LONG)
                                ;
                        snackbar.setActionTextColor(Color.RED);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(Color.DKGRAY);
                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();
                        Log.d("debug", "onResponse: "+hasil_pesan);
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
                MyData.put("name_user",edusername);
                return MyData;
            }
        };

        NetworkRequest.getInstance(this).addToRequestQueue(stringRequest);
    }
}

