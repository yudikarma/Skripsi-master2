package com.example.gungde.reminder_medicine.utils;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import de.mateware.snacky.Snacky;

/**
 * Created by macbookpro on 5/14/18.
 */

public class BaseActivity extends AppCompatActivity {

    protected void binding(int layout){
        setContentView(layout);
        ButterKnife.bind(this);
    }

    protected void showErrorSnackBar(String message) {
        Snacky.builder()
                .setActivity(this)
                .setText(message)
                .setDuration(Snacky.LENGTH_LONG)
                .error()
                .show();
    }

    protected void showWarningSnackBar(String message) {
        Snacky.builder()
                .setActivity(this)
                .setText(message)
                .setDuration(Snacky.LENGTH_INDEFINITE)
                .setActionText(android.R.string.ok)
                .warning()
                .show();
    }

    protected void showSuccessSnackBar(String message) {
        Snacky.builder()
                .setActivity(this)
                .setText(message)
                .setDuration(Snacky.LENGTH_SHORT)
                .success()
                .show();
    }

    protected void showSnackBar(String message) {
        Snacky.builder()
                .setActivity(this)
                .setText(message)
                .setDuration(Snacky.LENGTH_SHORT)
                .build()
                .show();
    }

}
