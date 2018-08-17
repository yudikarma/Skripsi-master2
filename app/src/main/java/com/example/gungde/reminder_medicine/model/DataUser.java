package com.example.gungde.reminder_medicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataUser {

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
        private Integer id_user;

    public DataUser(Integer id_user, String username, String email, String nohp, String kategori, String password) {
        this.id_user = id_user;
        this.username = username;
        this.email = email;
        this.nohp = nohp;
        this.kategori = kategori;
        this.password = password;
    }

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

        public Integer getId_user() {
            return id_user;
        }

        public void setId_user(Integer id_user) {
            this.id_user = id_user;
        }
    }

