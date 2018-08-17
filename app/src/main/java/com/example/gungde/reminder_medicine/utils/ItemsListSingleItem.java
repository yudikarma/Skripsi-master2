package com.example.gungde.reminder_medicine.utils;

public class ItemsListSingleItem {
    private String judul, catatan;
    private int jumlah;

    public ItemsListSingleItem(String judul, String catatan, int jumlah) {
        this.judul = judul;
        this.catatan = catatan;
        this.jumlah = jumlah;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}