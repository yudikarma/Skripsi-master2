package com.example.gungde.reminder_medicine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.model.DataObat;
import com.example.gungde.reminder_medicine.model.ReportObat;
import com.example.gungde.reminder_medicine.model.TranskasiModel;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Minumobat extends AppCompatActivity {

    ProgressDialog progressDoalog;
    @BindView(R.id.nama)
    TextView nama;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnAll)
    Button btnAll;
    @BindView(R.id.edtJlh_Minum)
    EditText edtJlhMinum;
    @BindView(R.id.btnSome)
    Button btnSome;

//    private DataObat.Data id_obatt;
//    private DataObat.Data jlh_makss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minumobat);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    public static String generateDateTime() {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        Date cal = Calendar.getInstance().getTime();
        return inputFormat.format(cal);
    }


    @OnClick(R.id.btnAll)
    public void onBtnAllClicked() {
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("id_obatt")) {
            String id_obatt = getIntent().getExtras().getString("id_obatt");
            String jlh_makss = getIntent().getExtras().getString("jlh_makss");
            String jlh_total = getIntent().getExtras().getString("jlh_makss");
            String waktu_minum = generateDateTime();
            GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<TranskasiModel> call = api.postTrans(id_obatt, jlh_makss, jlh_total, waktu_minum);

            call.enqueue(new Callback<TranskasiModel>() {
                @Override
                public void onResponse(Call<TranskasiModel> call, Response<TranskasiModel> response) {
                    if (response.isSuccessful()) {
                        startActivity(new Intent(Minumobat.this, Reward.class));
                        finish();
                    } else {
                        Toast.makeText(Minumobat.this, "tidak ada ID obat", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TranskasiModel> call, Throwable t) {
                    Log.e("ERROR", t.toString());
                }
            });
        }
    }

    @OnClick(R.id.btnSome)
    public void onBtnSomeClicked() {

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("id_obatt")) {
            String jlh_minum = edtJlhMinum.getText().toString().trim();
            String id_obatt = getIntent().getExtras().getString("id_obatt");
            String jlh_total = getIntent().getExtras().getString("jlh_makss");
            String waktu_minum = generateDateTime();
            GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<TranskasiModel> call = api.postTrans(id_obatt, jlh_minum, jlh_total, waktu_minum);

            call.enqueue(new Callback<TranskasiModel>() {
                @Override
                public void onResponse(Call<TranskasiModel> call, Response<TranskasiModel> response) {
                    if (response.isSuccessful()) {
                        startActivity(new Intent(Minumobat.this, Badreward.class));
                        finish();
                    } else {
                        Toast.makeText(Minumobat.this, "tidak ada ID obat", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TranskasiModel> call, Throwable t) {
                    Log.e("ERROR", t.toString());
                }
            });
        }
    }
}
