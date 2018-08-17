package com.example.gungde.reminder_medicine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.model.DataObat;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {
    ProgressDialog progressDoalog;
    //fIREBASE
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_splash);

        //Getting Uid user
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {


            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        }


           /* new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("medical", Context.MODE_PRIVATE);
                    String id_user = pref.getString("id_user", "0");

                    if(pref.getBoolean("isLogin", false)){
                        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                        Call<DataObat> call = api.getObat(id_user);
                        call.enqueue(new Callback<DataObat>(){
                            @Override
                            public void onResponse(@NonNull Call<DataObat> call, @NonNull Response<DataObat> response) {
                                DataObat resp = response.body();
                                if(resp.getStatus().equals("success")){
                                   *//* startActivity(new Intent(Splash.this,Beranda.class));
                                    finish();*//*
                                   Intent intent = new Intent(Splash.this,MainActivity.class);
                                   startActivity(intent);
                                   finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<DataObat> call, Throwable t) {
                                Log.e("Error", t.toString());
                            }
                        });
                    }
                    else{
                        startActivity(new Intent(Splash.this,Login.class));
                        finish();
                    }
                }
            },2000);*/

        }

    private void sendTostart() {
        //Check i user is Sign-out
        Intent startIntent = new Intent(Splash.this, Login.class);
        startActivity(startIntent);
        finish();

    }

    @Override
    public void onStart() {
        super.onStart();

        //ketika aplikasi pertama kali dibuka,
        //check apakah user dalam keadaan login.
        //jika iya setonline
        //jika tidak kirim ke activity login
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (!(currentUser != null)) {
            // !User is signed in
            sendTostart();


        } else {
            mUserRef.child("online").setValue("true");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("medical", Context.MODE_PRIVATE);
                    String id_user = pref.getString("id_user", "0");

                    if(pref.getBoolean("isLogin", false)){
                        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                        Call<DataObat> call = api.getObat(id_user);
                        call.enqueue(new Callback<DataObat>(){
                            @Override
                            public void onResponse(@NonNull Call<DataObat> call, @NonNull Response<DataObat> response) {
                                DataObat resp = response.body();
                                if(resp.getStatus().equals("success")){
//                                   *//* startActivity(new Intent(Splash.this,Beranda.class));
//                                    finish();*//*
                                    Intent intent = new Intent(Splash.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<DataObat> call, Throwable t) {
                                Log.e("Error", t.toString());
                            }
                        });
                    }
                    else{
                        startActivity(new Intent(Splash.this,Login.class));
                        finish();
                    }
                }
            },2000);

        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        //Getting waktu terakhir online
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

        }
    }


}

