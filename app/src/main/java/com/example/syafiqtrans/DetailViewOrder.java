package com.example.syafiqtrans;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailViewOrder extends AppCompatActivity {
TextView id_order1;
TextView tanggal_penjemputan1;
TextView tujuan1;
TextView bus1;
TextView harga1;
Button edit;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<model_data> dataSet;
    PreferenceHelper mPrefHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view_order);

//        Deklarasi dari layout ke aktivity
        id_order1 = findViewById(R.id.tv_id_order);
        tanggal_penjemputan1 = findViewById(R.id.tv_tanggal_penjemputan_order);
        tujuan1 = findViewById(R.id.tv_tujuan_order);
        bus1 = findViewById(R.id.tv_bus_order);
        harga1 = findViewById(R.id.tv_harga_order);
        edit = findViewById(R.id.btn_edit);
        mPrefHelper = new PreferenceHelper(this);

//        Deklarasi String ke Rest
        String id_order = getIntent().getStringExtra("id_order");
        String tanggal_penjemputan = getIntent().getStringExtra("tanggal_penjemputan");
        String tujuan = getIntent().getStringExtra("tujuan");
        String bus = getIntent().getStringExtra("bus");
        String harga = getIntent().getStringExtra("harga");

//        Set Text dari getStringExtra
        id_order1.setText(" "+id_order);
        tanggal_penjemputan1.setText(" "+tanggal_penjemputan);
        tujuan1.setText(" "+tujuan);
        bus1.setText(" "+bus);
        harga1.setText(""+harga);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(getApplicationContext(),UpdateOrder.class);
                edit.putExtra("id_order",id_order1.getText().toString());
                startActivity(edit);
            }
        });
    }

}
