package com.example.syafiqtrans;

public class model_bus {
    private   String id_price;
    private   String tujuan;


    model_bus(String id_price, String tujuan){
        this.id_price = id_price;
        this.tujuan = tujuan;

    }
    String getId_price(){ return id_price;}
    String getTujuan(){ return tujuan;}
}
