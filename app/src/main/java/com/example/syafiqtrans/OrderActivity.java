package com.example.syafiqtrans;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    Button btn_add;
    RelativeLayout mRelmain1;
    PreferenceHelper mPrefHelper;
    EditText etTujuan;
    EditText etBus;
    TextView harga;
    Spinner tujuan;
    private int firtsopen=0;
    String pilihan_bus = "";
    RadioGroup rg;


    public void onCreate(Bundle SaveInstanceState) {

        super.onCreate(SaveInstanceState);
        setContentView(R.layout.input_order);
        harga = findViewById(R.id.txtHarga);
        mRelmain1 = findViewById(R.id.main12);
        mPrefHelper = new PreferenceHelper(getApplication());
        btn_add = findViewById(R.id.buttonAdd);
        Button selectDate = findViewById(R.id.btnDate);
        final TextView date = findViewById(R.id.tvSelectedDate);
        tujuan = findViewById(R.id.stujuan);
        harga = findViewById(R.id.txtHarga);
        getKotaTujuan();
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(OrderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(year+ "-" + (month+1) +"-" +day  );
                            }
                        }, 0, 0, 0);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sDate = String.valueOf(date.getText());
                Log.d("debug", "Onclick: " + sDate);

                String bus = String.valueOf(pilihan_bus.getBytes());
                Log.d("debug", "Onclick: " + pilihan_bus);

//                String bus = pilihan_bus.getBytes().toString();
//                Log.d("debug", "Onclick: " + pilihan_bus);

                String stujuan = tujuan.getSelectedItem().toString();
                Log.d("debug", "Onclick: " + tujuan);

                String sHarga = harga.getText().toString();
                Log.d("debug", "Onclick: " + sHarga);

//
//                String sHarga = String.valueOf(harga.getText());
//                Log.d("debug", "Onclick: " + sHarga);


                if(date.getText().toString().length()==0){
                    date.setError("Tanggal Diperlukan!");
                } else
                    {
                    getTokenRest(sDate, bus, stujuan, sHarga);
                }

            }
        });
}

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.bigbus:
                if (checked)
                    Toast.makeText(this, "Big Buss", Toast.LENGTH_SHORT).show();
                    pilihan_bus = "bigbus";
                    break;
            case R.id.medium:
                if (checked)
                    Toast.makeText(this, "Medium Buss", Toast.LENGTH_SHORT).show();
                pilihan_bus = "mediumbus";
                    break;
            case R.id.hiace:
                if (checked)
                    Toast.makeText(this, "hiace", Toast.LENGTH_SHORT).show();
                pilihan_bus = "hiace";
                    break;
        }
    }
    private void getKotaTujuan(){

        String url = "http://193.168.195.201/burhan/index.php/Rest/RestUser/kotaTujuan";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    JSONArray cast1 = jObject.getJSONArray("pesan");
                    ArrayList<String> aName;
                    aName = new ArrayList<>();
                    aName.clear();
                    aName.add(("Pilih Tujuan Anda:"));
                    for (int i = 0; i < cast1.length(); i++) {
                        JSONObject model_tujuan = cast1.getJSONObject(i);
                        String tujuan = model_tujuan.getString("tujuan");
                        String id_price = model_tujuan.getString("id_price");
                        Log.d("debug", "onResponse: "+tujuan+" "+id_price);
                        aName.add(tujuan);
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, aName);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    tujuan.setAdapter(spinnerArrayAdapter);

                    tujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (firtsopen!=0) {

                                String swt = (String) parent.getItemAtPosition(position);
                                String message;
                                if (swt == "Pilih Tujuan Anda"){
                                    message = "Anda belum memilih tujuan ";

                                } else {
                                    message = "Anda memilih Tujuan "+swt;

                                }
                                getHarga(swt);
                            }
                            firtsopen = 1;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

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
            public Map<String, String> getHeaders() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("Authorization", "Bearer "+ mPrefHelper.getTokenRest()); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        NetworkRequest.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void getHarga(final String fss){

        String url = "http://193.168.195.201/burhan/index.php/Rest/RestUser/harga";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jObject = null;
                try {
                    String hargabigbus = null;
                    String  hargamediumbus = null;
                    String hargahiace = null;

                    jObject = new JSONObject(response);
                    String hasil_pesan = jObject.getString("pesan");
                    String hasil_respon = jObject.getString("respon");
                    JSONArray cast = jObject.getJSONArray("pesan");
                    for (int i = 0; i < cast.length(); i++){
                        JSONObject data = cast.getJSONObject(i);
                        hargabigbus = data.getString("big_bus");
                        hargamediumbus = data.getString("medium");
                        hargahiace = data.getString("hiace");
                    }

                    Log.d("debug", "Onclick: "+hargabigbus +" "+hargamediumbus+" "+hargahiace);
                    Log.d("debug", "onResponse: "+hasil_pesan);


                    if (pilihan_bus != null || !pilihan_bus.equals("")){
                        if (pilihan_bus.equals("bigbus")){
                            harga.setText(hargabigbus);
                        } else if (pilihan_bus.equals("mediumbus")) {
                            harga.setText(hargamediumbus);
                        } else if (pilihan_bus.equals("hiace")){
                            harga.setText(hargahiace);
                        }
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
                MyData.put("tujuan",fss); //Add the data you'd like to send to the server.
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
    private void getTokenRest (final String date,final String bus, final String tujuan, final String sHarga){
        String url = "http://193.168.195.201/burhan/index.php/Rest/RestUser/order";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jObject = null;
                try {
                    jObject = new JSONObject(response);
                    String hasil_pesan = jObject.getString("pesan");
                    String hasil_respon = jObject.getString("respon");
                    Log.d("debug", "Onclick: " + hasil_respon);

                    if (hasil_respon.equals("200")){
                        Log.d("debug", "USER BERHASIL DIMASUKKAN: "+hasil_pesan);
                        Snackbar snackbar = Snackbar
                                .make(mRelmain1, hasil_respon, Snackbar.LENGTH_LONG)
                                ;
                        snackbar.setActionTextColor(Color.RED);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(Color.DKGRAY);
                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();
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
                MyData.put("tanggal_penjemputan", date);
                MyData.put("bus", bus);
                MyData.put("tujuan", tujuan);
                MyData.put("harga", sHarga);
                return MyData;
            }
            public Map<String, String> getHeaders(){
                Map<String, String> MyData1 = new HashMap<>();
                MyData1.put("Authorization","Bearer " + mPrefHelper.getTokenRest());
                return MyData1;
            }
        };
        NetworkRequest.getInstance(getApplication()).addToRequestQueue(stringRequest);
    }
}
