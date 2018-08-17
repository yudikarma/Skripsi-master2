package com.example.gungde.reminder_medicine.ActivityChats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChangeStatus extends AppCompatActivity {
    private EditText status;
    private Button simpan,back;
    private DatabaseReference Userdatabase;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        //Toolbar action
        mToolbar = (Toolbar) findViewById(R.id.toolbarstatus);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Change Bio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        status = findViewById(R.id.edittextstatus);
        simpan = findViewById(R.id.simpan);

        //Getting user id user
        final String userid = getIntent().getStringExtra("iduser");
        String statuss = getIntent().getStringExtra("status");

        //change with news status
        Userdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newsstatus = status.getText().toString();
                if (!newsstatus.isEmpty()){
                    Map update = new HashMap();
                    update.put("status",newsstatus );
                    Userdatabase.updateChildren(update, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null){

                                finish();

                            }else {
                                String messageerror = databaseError.getMessage();
                                Toast.makeText(ChangeStatus.this,"Database Error", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else {
                    Toast.makeText(ChangeStatus.this, "tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
