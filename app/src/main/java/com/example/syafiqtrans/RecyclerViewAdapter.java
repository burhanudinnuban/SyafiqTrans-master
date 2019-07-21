package com.example.syafiqtrans;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.syafiqtrans.MenuFragment.ListFragment;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.syafiqtrans.R.layout.activity_transaksi;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<model_data> rvData;

    Context ctx;
    Context context;
    PreferenceHelper mPrefHelper;

    public RecyclerViewAdapter(Context ctx, ArrayList<model_data> inputData) {
        rvData = inputData;
        this.ctx = ctx; }
    class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        TextView tvTujuan;
        TextView tv_tanggal_penjemputan;
        TextView tv_bus;
        TextView tv_harga;
        ImageView delete;
        Button edit;
        ImageView order;
        ImageView view;



        ViewHolder(View v) {
            super(v);
            tv_tanggal_penjemputan = (TextView) v.findViewById(R.id.tv2);
            tvTujuan = (TextView) v.findViewById(R.id.tv1);
            tv_bus= (TextView) v.findViewById(R.id.tv3);
            tv_harga= (TextView) v.findViewById(R.id.tv4);
            delete =  v.findViewById(R.id.hapus_btn);
//            edit=  v.findViewById(R.id.edit_btn);
            order=  v.findViewById(R.id.order_btn);
            view= v.findViewById(R.id.view_btn);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        final model_data name = rvData.get(position);
        holder.tv_tanggal_penjemputan.setText(rvData.get(position).getTanggal_penjemputan());
        holder.tvTujuan.setText(rvData.get(position).getTujuan());
        holder.tv_harga.setText(rvData.get(position).getHarga());
        holder.tv_bus.setText(rvData.get(position).getBus());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrefHelper = new PreferenceHelper(ctx);
                Log.d("debug", "onClick: "+rvData.get(position).getId_order());
                String url = "http://193.168.195.201/burhan/index.php/Rest/RestUser/delete_order";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jObject = null;
                        try {
                            jObject = new JSONObject(response);
                            String hasil_pesan = jObject.getString("pesan");
                            Log.d("debug", "onResponse: "+hasil_pesan);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent("broadcast");
                        intent.putExtra("message","getdata");
                        LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
                        Log.e("recycleradapter", "mchange = " + "asd");
                        Toast.makeText(ctx,"Data berhasil terhapus", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //This code is executed if there is an error.
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();
                        MyData.put("id_order",rvData.get(position).getId_order()); //Add the data you'd like to send to the server.
                        return MyData;
                    }
                    public Map<String, String> getHeaders(){
                        Map<String, String> MyData1 = new HashMap<>();
                        MyData1.put("Authorization","Bearer " + mPrefHelper.getTokenRest());
                        return MyData1;
                    }
                };

                NetworkRequest.getInstance(ctx).addToRequestQueue(stringRequest);
            }
        });
        holder.order.setOnClickListener(new View.OnClickListener() {
            private Object TransaksiActivity;

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
                TransaksiActivity = com.example.syafiqtrans.TransaksiActivity.class;
                // set title dialog
                alertDialogBuilder.setTitle("Brosur PO.SyafiqTrans");

                // set pesan dari dialog
                alertDialogBuilder
                        .setIcon(R.mipmap.ic_launcher)
                        .setView((View) TransaksiActivity)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                // jika tombol diklik, maka akan menutup activity ini
                                dialog.dismiss();
                            }
                        });

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrefHelper = new PreferenceHelper(ctx);
                Intent intent = new Intent(ctx, DetailViewOrder.class);
                intent.putExtra("id_order", rvData.get(position).getId_order());
                intent.putExtra("tanggal_penjemputan", rvData.get(position).getTanggal_penjemputan());
                intent.putExtra("tujuan", rvData.get(position).getTujuan());
                intent.putExtra("bus", rvData.get(position).getBus());
                intent.putExtra("harga", rvData.get(position).getHarga());
                ctx.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return rvData.size();
    }
}