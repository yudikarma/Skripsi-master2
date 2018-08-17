package com.example.gungde.reminder_medicine;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.model.InputObat;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;
import com.example.gungde.reminder_medicine.utils.AlarmReceiver;
import com.example.gungde.reminder_medicine.utils.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;

public class Home extends AppCompatActivity {

    ProgressDialog progressDoalog;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtJudul)
    EditText txtJudul;
    @BindView(R.id.txtCatatan)
    EditText txtCatatan;
    @BindView(R.id.txtSisa)
    EditText txtSisa;
    @BindView(R.id.txtJlh_maks)
    EditText txtJlh_maks;
    @BindView(R.id.edtTime1)
    EditText edtTime1;
    @BindView(R.id.btnTime1)
    Button btnTime1;
    @BindView(R.id.bg1)
    LinearLayout bg1;
    @BindView(R.id.bg2)
    LinearLayout bg2;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnOut)
    Button btnOut;
    @BindView(R.id.txtWaktu)
    EditText txtWaktu;


    private AlarmManager alarmManager;
    private static Home inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        progressDoalog = new ProgressDialog(Home.this);
        progressDoalog.setMessage("Loading....");
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    public static Home instance() {
        return inst;
    }

    /*public void onSuccess(String time) {
        presenter.sendNotif(time, edtNama.getText().toString());
    }*/

    private void setTimePicker(final EditText et) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        et.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void setAlarm(String id_obat) {
        Calendar calendar = Calendar.getInstance();
        String time = edtTime1.getText().toString();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(3)));
        calendar.set(Calendar.SECOND, 0);


        Intent myIntent = new Intent(Home.this, AlarmReceiver.class);
        myIntent.putExtra("id_obatt", id_obat);
        myIntent.putExtra("jlh_makss", txtJlh_maks.getText().toString().trim());
        myIntent.putExtra("judull", txtJudul.getText().toString().trim());
        myIntent.putExtra("jlh_sisa", txtSisa.getText().toString().trim());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Home.this, Integer.parseInt(id_obat), myIntent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    @OnClick(R.id.btnTime1)
    public void onChangeTime1() {
        setTimePicker(edtTime1);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnOut)
    public void onViewClicked1() {
        finish();
    }

    public String getWaktuAkhir(int waktu_akhir) {
        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(inputPattern);
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        Calendar cal = Calendar.getInstance();
        Date date;
        String str = null;
        try {
            date = sdf.parse(currentDate);
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, waktu_akhir);
            str = sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @OnClick(R.id.btnAdd)
    public void onViewClicked() {
        SharedPreferences pref = this.getSharedPreferences("medical", Context.MODE_PRIVATE);
        String id_user = (pref.getString("id_user", "0"));
        String judul = txtJudul.getText().toString().trim();
        String jlh_maks = txtJlh_maks.getText().toString().trim();
        String jlh_obat = txtSisa.getText().toString().trim();
        String catatan = txtCatatan.getText().toString().trim();
        String waktu_akhir = getWaktuAkhir(Integer.parseInt(txtWaktu.getText().toString().trim()));
        String time = edtTime1.getText().toString().trim();


        if (judul.equals("") || jlh_maks.equals("") || jlh_obat.equals("") || catatan.equals("") || waktu_akhir.equals("")) {
            Toast.makeText(Home.this, "Pastikan semua data terisi!", Toast.LENGTH_SHORT).show();
        } else {
            GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<InputObat> call = api.postObat(id_user, judul, jlh_maks, jlh_obat, catatan, waktu_akhir, time);
            progressDoalog.show();
            call.enqueue(new Callback<InputObat>() {
                @Override
                public void onResponse(Call<InputObat> call, Response<InputObat> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Home.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        setAlarm(response.body().getId_obat());
                        finish();
                    } else {
                        Toast.makeText(Home.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                    progressDoalog.dismiss();
                }

                @Override
                public void onFailure(Call<InputObat> call, Throwable t) {
                    progressDoalog.dismiss();
                    Log.e("ERROR", t.toString());
                }
            });
        }
    }
}
