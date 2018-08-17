package com.example.gungde.reminder_medicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Base {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("status")
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
        @SerializedName("password")
        private String password;
        @Expose
        @SerializedName("kategori")
        private String kategori;
        @Expose
        @SerializedName("nohp")
        private String nohp;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("username")
        private String username;
        @Expose
        @SerializedName("id_user")
        private String id_user;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getKategori() {
            return kategori;
        }

        public void setKategori(String kategori) {
            this.kategori = kategori;
        }

        public String getNohp() {
            return nohp;
        }

        public void setNohp(String nohp) {
            this.nohp = nohp;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getId_user() {
            return id_user;
        }

        public void setId_user(String id_user) {
            this.id_user = id_user;
        }
    }
}
