
package com.example.gungde.reminder_medicine.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MainResponse {

    @SerializedName("message-count")
    private String mMessageCount;
    @SerializedName("messages")
    private List<Message> mMessages;

    public String getMessageCount() {
        return mMessageCount;
    }

    public void setMessageCount(String messageCount) {
        mMessageCount = messageCount;
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public void setMessages(List<Message> messages) {
        mMessages = messages;
    }

    public static class Message {

        @SerializedName("message-id")
        private String mMessageId;
        @SerializedName("message-price")
        private String mMessagePrice;
        @SerializedName("network")
        private String mNetwork;
        @SerializedName("remaining-balance")
        private String mRemainingBalance;
        @SerializedName("status")
        private String mStatus;
        @SerializedName("to")
        private String mTo;

        public String getMessageId() {
            return mMessageId;
        }

        public void setMessageId(String messageId) {
            mMessageId = messageId;
        }

        public String getMessagePrice() {
            return mMessagePrice;
        }

        public void setMessagePrice(String messagePrice) {
            mMessagePrice = messagePrice;
        }

        public String getNetwork() {
            return mNetwork;
        }

        public void setNetwork(String network) {
            mNetwork = network;
        }

        public String getRemainingBalance() {
            return mRemainingBalance;
        }

        public void setRemainingBalance(String remainingBalance) {
            mRemainingBalance = remainingBalance;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String status) {
            mStatus = status;
        }

        public String getTo() {
            return mTo;
        }

        public void setTo(String to) {
            mTo = to;
        }

    }

}
