package com.example.gungde.reminder_medicine;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.adapter.CustomAdapter;
import com.example.gungde.reminder_medicine.model.DataObat;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Beranda extends Fragment {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    private Unbinder unbinder;


    @BindView(R.id.prograss)
    ProgressBar prograss;
    @BindView(R.id.listObat)
    RecyclerView listObat;
    @BindView(R.id.addfab)
    FloatingActionButton addfab;


public Beranda(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.activity_beranda, container, false);
       ButterKnife.bind(this,mView);

        listObat = mView.findViewById(R.id.listObat);
        addfab = mView.findViewById(R.id.addfab);

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        return mView;
    }


    @Override
    public void onStart() {
        super.onStart();
        getList();
        progressDoalog.hide();
    }

    private void getList(){
        progressDoalog.show();

        SharedPreferences pref = getActivity().getSharedPreferences("medical", Context.MODE_PRIVATE);
        String id_user = pref.getString("id_user", "0");

        /*Create handle for the RetrofitInstance interface*/
        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<DataObat> call = api.getObat(id_user);
        call.enqueue(new Callback<DataObat>() {

            @Override
            public void onResponse(@NonNull Call<DataObat> call, @NonNull Response<DataObat> response) {
                DataObat resp = response.body();
                if (response.body().getData().size() != 0) {
                    generateDataList(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataObat> call, Throwable t) {
                progressDoalog.dismiss();
                Log.e("Error", t.toString());
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        progressDoalog.dismiss();
    }
    private void generateDataList(List<DataObat.Data> data) {
        recyclerView = getActivity().findViewById(R.id.listObat);
        adapter = new CustomAdapter(getActivity(), data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.addfab)
    public void tambah() {
        startActivity(new Intent(getActivity(), Home.class));
    }

/*    @Override
    public void onBackPressed() {
        getActivity().moveTaskToBack(true);
    }*/
}

