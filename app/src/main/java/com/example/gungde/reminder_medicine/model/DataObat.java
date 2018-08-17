package com.example.gungde.reminder_medicine.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataObat implements Parcelable {

    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("status")
    private String status;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Data implements Parcelable {
        @Expose
        @SerializedName("waktu_alarm")
        private String waktu_alarm;
        @Expose
        @SerializedName("waktu_akhir")
        private String waktu_akhir;
        @Expose
        @SerializedName("catatan")
        private String catatan;
        @Expose
        @SerializedName("jlh_obat")
        private String jlh_obat;
        @Expose
        @SerializedName("jlh_maks")
        private String jlh_maks;
        @Expose
        @SerializedName("nama_obat")
        private String nama_obat;
        @Expose
        @SerializedName("id_user")
        private String id_user;
        @Expose
        @SerializedName("id_obat")
        private String id_obat;

        public String getWaktu_alarm() {
            return waktu_alarm;
        }

        public void setWaktu_alarm(String waktu_alarm) {
            this.waktu_alarm = waktu_alarm;
        }

        public String getWaktu_akhir() {
            return waktu_akhir;
        }

        public void setWaktu_akhir(String waktu_akhir) {
            this.waktu_akhir = waktu_akhir;
        }

        public String getCatatan() {
            return catatan;
        }

        public void setCatatan(String catatan) {
            this.catatan = catatan;
        }

        public String getJlh_obat() {
            return jlh_obat;
        }

        public void setJlh_obat(String jlh_obat) {
            this.jlh_obat = jlh_obat;
        }

        public String getJlh_maks() {
            return jlh_maks;
        }

        public void setJlh_maks(String jlh_maks) {
            this.jlh_maks = jlh_maks;
        }

        public String getNama_obat() {
            return nama_obat;
        }

        public void setNama_obat(String nama_obat) {
            this.nama_obat = nama_obat;
        }

        public String getId_user() {
            return id_user;
        }

        public void setId_user(String id_user) {
            this.id_user = id_user;
        }

        public String getId_obat() {
            return id_obat;
        }

        public void setId_obat(String id_obat) {
            this.id_obat = id_obat;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.waktu_alarm);
            dest.writeString(this.waktu_akhir);
            dest.writeString(this.catatan);
            dest.writeString(this.jlh_obat);
            dest.writeString(this.jlh_maks);
            dest.writeString(this.nama_obat);
            dest.writeString(this.id_user);
            dest.writeString(this.id_obat);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.waktu_alarm = in.readString();
            this.waktu_akhir = in.readString();
            this.catatan = in.readString();
            this.jlh_obat = in.readString();
            this.jlh_maks = in.readString();
            this.nama_obat = in.readString();
            this.id_user = in.readString();
            this.id_obat = in.readString();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
        dest.writeString(this.status);
    }

    public DataObat() {
    }

    protected DataObat(Parcel in) {
        this.data = new ArrayList<Data>();
        in.readList(this.data, Data.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Parcelable.Creator<DataObat> CREATOR = new Parcelable.Creator<DataObat>() {
        @Override
        public DataObat createFromParcel(Parcel source) {
            return new DataObat(source);
        }

        @Override
        public DataObat[] newArray(int size) {
            return new DataObat[size];
        }
    };
}
