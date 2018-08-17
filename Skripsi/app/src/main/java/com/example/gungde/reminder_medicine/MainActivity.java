package com.example.gungde.reminder_medicine;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.gungde.reminder_medicine.model.MainResponse;
import com.example.gungde.reminder_medicine.utils.AlarmReceiver;
import com.example.gungde.reminder_medicine.utils.BaseActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements Main {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rbTime1)
    RadioButton rbTime1;
    @BindView(R.id.rbTime2)
    RadioButton rbTime2;
    @BindView(R.id.rbTime3)
    RadioButton rbTime3;
    @BindView(R.id.rgTime)
    RadioGroup rgTime;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtTime1)
    EditText edtTime1;
    @BindView(R.id.btnTime1)
    Button btnTime1;
    @BindView(R.id.bg1)
    LinearLayout bg1;
    @BindView(R.id.edtTime2)
    EditText edtTime2;
    @BindView(R.id.btnTime2)
    Button btnTime2;
    @BindView(R.id.bg2)
    LinearLayout bg2;
    @BindView(R.id.edtTime3)
    EditText edtTime3;
    @BindView(R.id.btnTime3)
    Button btnTime3;
    @BindView(R.id.bg3)
    LinearLayout bg3;
    @BindView(R.id.btnToggle)
    ToggleButton btnToggle;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static MainActivity inst;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding(R.layout.activity_main);
        setSupportActionBar(toolbar);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        presenter = new MainPresenterImp(this);
        setRadioGroup();
    }

    private void setRadioGroup() {
        rgTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbTime1) {
                    bg1.setVisibility(View.VISIBLE);
                    bg2.setVisibility(View.GONE);
                    bg3.setVisibility(View.GONE);
                } else if (checkedId == R.id.rbTime2) {
                    bg1.setVisibility(View.VISIBLE);
                    bg2.setVisibility(View.VISIBLE);
                    bg3.setVisibility(View.GONE);
                } else {
                    bg1.setVisibility(View.VISIBLE);
                    bg2.setVisibility(View.VISIBLE);
                    bg3.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    public static MainActivity instance() {
        return inst;
    }

    public void onSuccess() {
        showSuccessSnackBar("YEY");
//        presenter.sendNotif(edtTime1.getText().toString(), edtPhone.getText().toString());
    }

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

    private void setAlarm(EditText et){
        Calendar calendar = Calendar.getInstance();
        String time = et.getText().toString();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,2)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(3)));
        calendar.set(Calendar.SECOND, 0);
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void result(MainResponse response) {
        if (response.getMessages().get(0).getStatus().equals("0"))
            showSuccessSnackBar("Sukses mengirim pesan!!");
        else
            showErrorSnackBar("Gagal kirim pesan");
    }

    @OnClick(R.id.btnTime1)
    public void onChangeTime1() {
        setTimePicker(edtTime1);
    }

    @OnClick(R.id.btnTime2)
    public void onChangeTime2() {
        setTimePicker(edtTime2);
    }

    @OnClick(R.id.btnTime3)
    public void onChangeTime3() {
        setTimePicker(edtTime3);
    }

    @OnClick(R.id.btnToggle)
    public void onToggle(ToggleButton toggleButton) {
        if (toggleButton.isChecked()) {
            if(edtPhone.getText().toString().trim().equals("")){
                showSnackBar("Mohon input nomor telepon terlebih dahulu");
                toggleButton.setChecked(false);
            }else{
                if(rbTime1.isChecked())
                    setAlarm(edtTime1);
                else if(rbTime2.isChecked()){
                    setAlarm(edtTime1);
                    setAlarm(edtTime2);
                }else if(rbTime3.isChecked()){
                    setAlarm(edtTime1);
                    setAlarm(edtTime2);
                    setAlarm(edtTime3);
                }
            }
        } else {
            alarmManager.cancel(pendingIntent);
            Log.d("MyActivity", "Alarm Off");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
