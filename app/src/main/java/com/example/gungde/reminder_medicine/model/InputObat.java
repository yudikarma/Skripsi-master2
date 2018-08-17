package com.example.gungde.reminder_medicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputObat {


    @Expose
    @SerializedName("id_obat")
    private String id_obat;
    @Expose
    @SerializedName("messages")
    private String messages;
    @Expose
    @SerializedName("status")
    private boolean status;

    public String getId_obat() {
        return id_obat;
    }

    public void setId_obat(String id_obat) {
        this.id_obat = id_obat;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
