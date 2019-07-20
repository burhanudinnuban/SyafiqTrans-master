package com.example.syafiqtrans.MenuFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.syafiqtrans.NetworkRequest;
import com.example.syafiqtrans.PreferenceHelper;
import com.example.syafiqtrans.R;
import com.example.syafiqtrans.RecyclerViewAdapter;
import com.example.syafiqtrans.model_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListFragment extends Fragment {
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<model_data> dataSet;
    PreferenceHelper mPrefHelper;
    Context ctx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvView = view.findViewById(R.id.recycler_view);
        ctx = getActivity();

        mPrefHelper = new PreferenceHelper(getContext());
        rvView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);

       getdatalistcustomer();
    }

    private void getdatalistcustomer ( ){
        String url = "http://193.168.195.201/burhan/index.php/Rest/RestUser/vieworder";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jObject;
                try {
                    jObject = new JSONObject(response);
                    Log.d("DEBUG", "onResponse: "+response);
                    JSONArray cast = jObject.getJSONArray("pesan");
                    dataSet = new ArrayList<>();
                    dataSet.clear();
                    for (int i = 0; i < cast.length(); i++){
                        JSONObject data = cast.getJSONObject(i);
                        String id_order = data.getString("id_order");
                        String tanggal_penjemputan = data.getString("tanggal_penjemputan");
                        String tujuan = data.getString("tujuan");
                        String bus = data.getString("bus");
                        String harga = data.getString("harga");

                        dataSet.add(new model_data(id_order, tanggal_penjemputan, tujuan, bus, harga));
                    }

                    adapter = new RecyclerViewAdapter(ctx, dataSet);
                    rvView.setAdapter(adapter);

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
            public Map<String, String> getHeaders(){
                Map<String, String> MyData1 = new HashMap<>();
                Log.d("DEBUG", "getHeaders: "+mPrefHelper.getTokenRest());
                MyData1.put("Authorization","Bearer " + mPrefHelper.getTokenRest());
                return MyData1;
            }
        };

        NetworkRequest.getInstance(getContext()).addToRequestQueue(stringRequest);
    }



}