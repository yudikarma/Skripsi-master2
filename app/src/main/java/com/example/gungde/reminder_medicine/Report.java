package com.example.gungde.reminder_medicine;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.adapter.CustomAdapter;
import com.example.gungde.reminder_medicine.model.DataObat;
import com.example.gungde.reminder_medicine.model.HapusModel;
import com.example.gungde.reminder_medicine.model.LoginModel;
import com.example.gungde.reminder_medicine.model.ReportObat;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Report extends AppCompatActivity {

    ProgressDialog progressDoalog;
    @BindView(R.id.chart1)
    PieChart chart1;

    private static String TAG = "Report.java";
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.logoDelete)
    ImageView logoDelete;
    @BindView(R.id.logoReport)
    ImageView logoReport;
    @BindView(R.id.logoEdit)
    ImageView logoEdit;

    private float[] yData;
    private String[] xData = {"Patuh", "Tidak Patuh"};
    private DataObat.Data obat;
    public ReportObat reportobat;
    float total_minum = 0;
    float total_harus = 0;
    float patuh = 0;
    float tidak_patuh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("obat")) {
            obat = getIntent().getExtras().getParcelable("obat");
            GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ReportObat> call = api.getReportObat(obat.getId_obat());

            call.enqueue(new Callback<ReportObat>() {
                @Override
                public void onResponse(Call<ReportObat> call, Response<ReportObat> response) {
                    if (response.body().getStatus()) {
                        total_minum = response.body().getTotal_minum();
                        total_harus = response.body().getTotal_harus();

                        patuh = (total_minum / total_harus) * 100;
                        tidak_patuh = 100 - patuh;
                        yData = new float[]{patuh, tidak_patuh};

                        Log.d(TAG, "onCreate: starting to creat a char");

                        chart1.setDescription("Persentase Kepatuhan");
                        chart1.setRotationEnabled(true);
                        chart1.setHoleRadius(53f);
                        chart1.setTransparentCircleAlpha(0);
                        chart1.setUsePercentValues(true);
//        chart1.setExtraOffsets(5, 10, 5, 5);

//        chart1.setCenterText("Kepatuhan Minum Obat");
//        chart1.setCenterTextSize(5);
//        chart1.setDrawEntryLabels(true);

                        addDataSet();
                    } else {
                        Toast.makeText(Report.this, "tidak ada ID obat", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReportObat> call, Throwable t) {
                    Log.e("ERROR", t.toString());
                }
            });
        }

    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], xData[i]));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to data set
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.CYAN);
        colors.add(Color.LTGRAY);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = chart1.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        chart1.setData(pieData);
        chart1.invalidate();

    }

    @OnClick(R.id.btnBack)
    public void onViewClicked() {
        startActivity(new Intent(Report.this, MainActivity.class));
        finish();
    }

//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(Report.this, Beranda.class));
//        moveTaskToBack(false);
//    }

    @OnClick(R.id.logoDelete)
    public void onLogoDeleteClicked() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(Report.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(Report.this);
        }
        builder.setTitle("Hapus Obat")
                .setMessage("Apakah Anda ingin menghapus data obat?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                        Call<HapusModel> call = api.delObat(obat.getId_obat());
                        call.enqueue(new Callback<HapusModel>() {
                            @Override
                            public void onResponse(Call<HapusModel> call, Response<HapusModel> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(Report.this, "Hapus Obat Berhasil!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Report.this, Beranda.class));
                                    finish();
                                } else {
                                    Toast.makeText(Report.this, "tidak ada ID obat", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<HapusModel> call, Throwable t) {
                                Log.e("ERROR", t.toString());
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    @OnClick(R.id.logoEdit)
    public void onLogoEditClikced() {
        Intent i = new Intent(this, Aturobat.class);
        i.putExtra("obat", obat);
        startActivity(i);
    }
}
