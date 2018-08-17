package com.example.gungde.reminder_medicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class  TranskasiModel {

     @Expose
    @SerializedName("total_harus")
    private int total_harus;
    @Expose
    @SerializedName("total_minum")
    private int total_minum;
    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("status")
    private boolean status;

    public int getTotal_harus() {
        return total_harus;
    }

    public void setTotal_harus(int total_harus) {
        this.total_harus = total_harus;
    }

    public int getTotal_minum() {
        return total_minum;
    }

    public void setTotal_minum(int total_minum) {
        this.total_minum = total_minum;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("waktu_minum")
        private String waktu_minum;
        @Expose
        @SerializedName("jlh_total")
        private String jlh_total;
        @Expose
        @SerializedName("jlh_minum")
        private String jlh_minum;
        @Expose
        @SerializedName("id_obat")
        private String id_obat;
        @Expose
        @SerializedName("id_trans")
        private String id_trans;

        public String getWaktu_minum() {
            return waktu_minum;
        }

        public void setWaktu_minum(String waktu_minum) {
            this.waktu_minum = waktu_minum;
        }

        public String getJlh_total() {
            return jlh_total;
        }

        public void setJlh_total(String jlh_total) {
            this.jlh_total = jlh_total;
        }

        public String getJlh_minum() {
            return jlh_minum;
        }

        public void setJlh_minum(String jlh_minum) {
            this.jlh_minum = jlh_minum;
        }

        public String getId_obat() {
            return id_obat;
        }

        public void setId_obat(String id_obat) {
            this.id_obat = id_obat;
        }

        public String getId_trans() {
            return id_trans;
        }

        public void setId_trans(String id_trans) {
            this.id_trans = id_trans;
        }
    }
}
