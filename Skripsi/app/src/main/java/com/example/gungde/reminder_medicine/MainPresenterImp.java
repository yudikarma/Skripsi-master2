package com.example.gungde.reminder_medicine;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.gungde.reminder_medicine.model.MainResponse;

/**
 * Created by macbookpro on 7/3/18.
 */

public class MainPresenterImp implements MainPresenter{

    private Main mView;
    private static final String TAG = MainPresenterImp.class.getSimpleName();

    public MainPresenterImp(Main view){
        this.mView = view;
    }

    @Override
    public void sendNotif(String time, String phone) {
        AndroidNetworking.post("https://rest.nexmo.com/sms/json")
                .addBodyParameter("api_key", "e37925f1")
                .addBodyParameter("api_secret", "W9FjcJooKxKtaVhA")
                .addBodyParameter("to", phone)
                .addBodyParameter("from", "Reminder App")
                .addBodyParameter("text", "Sudah pukul "+ time + ", kabari temanmu untuk meminum obat!!")
                .setContentType("application/json; charset=utf-8")
                .setTag("kongsi_activation")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(MainResponse.class, new ParsedRequestListener<MainResponse>() {
                    @Override
                    public void onResponse(MainResponse response) {
                        mView.result(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if(anError.getErrorCode() != 0){
                            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
                            Log.e(TAG, "onError errorBody : " + anError.getErrorBody());
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }else{
                            Log.e(TAG, "onError errorDetail : " + anError.getErrorDetail());
                        }
                    }
                });
    }
}
