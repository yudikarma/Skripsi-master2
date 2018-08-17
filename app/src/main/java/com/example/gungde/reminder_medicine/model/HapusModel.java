package com.example.gungde.reminder_medicine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HapusModel {
    @Expose
    @SerializedName("messages")
    private String messages;
    @Expose
    @SerializedName("status")
    private boolean status;

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
