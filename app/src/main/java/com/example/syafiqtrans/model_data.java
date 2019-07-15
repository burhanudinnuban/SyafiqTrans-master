package com.example.syafiqtrans;

public class model_data {
    private   String id_order;
    private   String tanggal_penjemputan;
    private   String tujuan;
    private   String bus;
    private   String harga;



    model_data(String id_order, String tanggal_penjemputan, String tujuan, String bus, String harga){
        this.id_order = id_order;
        this.tanggal_penjemputan = tanggal_penjemputan;
        this.tujuan = tujuan;
        this.bus = bus;
        this.harga = harga;


    }
    String getId_order(){ return id_order;}
    String getTanggal_penjemputan(){ return tanggal_penjemputan;}
    String getTujuan(){ return tujuan;}
    String getBus(){ return bus;}
    String getHarga(){ return harga;}

}
