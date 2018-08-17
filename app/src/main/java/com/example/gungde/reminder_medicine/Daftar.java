package com.example.gungde.reminder_medicine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.model.Base;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daftar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressDialog progressDoalog;
   /* @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.btnMasuk)
    Button btnMasuk;
    @BindView(R.id.txtUsername)
    EditText txtUsername;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtPhone)
    EditText txtPhone;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.btnDaftar)
    Button btnDaftar;*/
   private TextInputLayout displayname;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout no_hp;
    private TextInputLayout alamat;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton_lk,mRadioButton_pr;
    private LinearLayout regiSt;

    private Button register;
    private Toolbar mToolbar;
    private ProgressDialog mpProgressDialog;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //database firebase
    private DatabaseReference mDatabaseReference;
    private DatabaseReference databaseUserCampur;
    private FirebaseUser currentUser;

    private Spinner mSpinner;
    private ArrayAdapter<CharSequence> spinneradapter;
    private String sjenisuser,skategoripengguna,edisplayname,eemail,epassword,eno_hp,ealamat,jenislk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        mpProgressDialog = new ProgressDialog(Daftar.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        displayname = (TextInputLayout) findViewById(R.id.reg_display_name);
        email = (TextInputLayout) findViewById(R.id.reg_email);
        password = (TextInputLayout) findViewById(R.id.reg_password);
        register = (Button) findViewById(R.id.regist_btn);
        no_hp = (TextInputLayout) findViewById(R.id.reg_no_hp);
        alamat = (TextInputLayout) findViewById(R.id.reg_alamat);
        mRadioGroup = (RadioGroup) findViewById(R.id.reg_JenisLK);
        mRadioButton_lk = (RadioButton) findViewById(R.id.perempuan);
        regiSt =  findViewById(R.id.tvRegister);
        mRadioButton_pr = (RadioButton) findViewById(R.id.laki);
        progressDoalog = new ProgressDialog(Daftar.this);
        progressDoalog.setMessage("Loading....");
        mSpinner = findViewById(R.id.reg_jenis_user);
        spinneradapter = ArrayAdapter.createFromResource(this, R.array.jenis_user, android.R.layout.simple_spinner_item);
        spinneradapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinneradapter);
        mSpinner.setOnItemSelectedListener(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 edisplayname = displayname.getEditText().getText().toString();
                 eemail = email.getEditText().getText().toString();
                 Log.e("Email", eemail);
                 Toast.makeText(Daftar.this, ""+eemail, Toast.LENGTH_LONG).show();
                epassword = password.getEditText().getText().toString();
                eno_hp = no_hp.getEditText().getText().toString();
                 ealamat = alamat.getEditText().getText().toString();
                 jenislk = "";
                int jenisid = mRadioGroup.getCheckedRadioButtonId();
                if (jenisid==mRadioButton_lk.getId()){
                    jenislk = mRadioButton_lk.getText().toString();
                }else if (jenisid==mRadioButton_pr.getId()){
                    jenislk = mRadioButton_pr.getText().toString();
                }else {
                    jenislk = "bukan apa2";
                }

                if (sjenisuser.equalsIgnoreCase("Dokter")){
                    skategoripengguna = sjenisuser;
                    edisplayname = "Dr. "+edisplayname;
                }else {
                    skategoripengguna = sjenisuser;
                }





                if(!TextUtils.isEmpty(edisplayname)&& !TextUtils.isEmpty(eemail)&& !TextUtils.isEmpty(epassword)
                        && !TextUtils.isEmpty(eno_hp)&& !TextUtils.isEmpty(ealamat)&& !TextUtils.isEmpty(jenislk) ) {
                    mpProgressDialog.setTitle("Creating new acount..");
                    mpProgressDialog.setMessage("Please wait.. while we create your acount..");
                    mpProgressDialog.setCanceledOnTouchOutside(false);
                    mpProgressDialog.show();
                    register_user_firebase(edisplayname, eemail, epassword,eno_hp,ealamat,skategoripengguna,jenislk);
                }else{
                    mpProgressDialog.hide();
                    Toast.makeText(Daftar.this,"Harap isi seluruh form",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //intent for registrasi
        regiSt =  findViewById(R.id.tvRegister);
        regiSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Daftar.this,Login.class));

            }
        });
    }

   /* private void setSpinnerResources() {
        List<String> kategori = Arrays.asList(getResources().getStringArray(R.array.kategori));
        ArrayAdapter<String> adpKategori = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kategori);
        adpKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adpKategori);
    }*/

   //Register ke Firebase
   private void register_user_firebase(final String edisplayname, final String eemail, final String epassword, final String eno_hp, final String ealamat, String euser, final String jenislk) {
       mAuth.createUserWithEmailAndPassword(eemail,epassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){

                   currentUser = FirebaseAuth.getInstance().getCurrentUser();
                   String uid = currentUser.getUid();

                   //child first is root child,then second child
                   mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);



                   HashMap<String,String> userMap = new HashMap<>();
                   userMap.put("name",edisplayname);
                   userMap.put("status","Harus Semangat. Rajin Minum Obat");
                   userMap.put("image","default");
                   userMap.put("thumb_image","default");
                   userMap.put("email",eemail);
                   userMap.put("no_hp",eno_hp);
                   userMap.put("address",ealamat);
                   userMap.put("Jenis_kelamin",jenislk);
                   userMap.put("uid",mAuth.getUid() );
                   userMap.put("kategori_pengguna", skategoripengguna);

                   mDatabaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               register_user_CustomAPI();
                               mpProgressDialog.dismiss();
                               Intent intent = new Intent(Daftar.this,Login.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                           } else {
                               mpProgressDialog.hide();
                               Toast.makeText(Daftar.this, "Terjadi Kesalahan !!, periksa jaringan anda dan coba lagi", Toast.LENGTH_LONG).show();

                           }

                       }
                   });


                   /* mpProgressDialog.dismiss();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();*/



               }else{
                   mpProgressDialog.hide();
                   Toast.makeText(Daftar.this,"Registrasi dengan FirebaseAuth failed ..",Toast.LENGTH_LONG).show();
               }
           }
       });
   }


   //REGISTER KE Custom API
    public void register_user_CustomAPI() {


        String edisplayname = displayname.getEditText().getText().toString();
        String eemail = email.getEditText().getText().toString();
        String epassword = password.getEditText().getText().toString();
        String eno_hp = no_hp.getEditText().getText().toString();
        String ealamat = alamat.getEditText().getText().toString();
        String jenislk = "";
        int jenisid = mRadioGroup.getCheckedRadioButtonId();
        if (jenisid==mRadioButton_lk.getId()){
            jenislk = mRadioButton_lk.getText().toString();
        }else if (jenisid==mRadioButton_pr.getId()){
            jenislk = mRadioButton_pr.getText().toString();
        }else {
            jenislk = "bukan apa2";
        }

        if (sjenisuser.equalsIgnoreCase("Dokter")){
            skategoripengguna = sjenisuser;
            edisplayname = "Dr. "+edisplayname;
        }else {
            skategoripengguna = sjenisuser;
        }

        if (edisplayname.equals("") || eemail.equals("") || eno_hp.equals("")  || epassword.equals("")) {
            Toast.makeText(this, "Pastikan semua data terisi!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+edisplayname+""+eemail+""+epassword, Toast.LENGTH_LONG).show();
        } else {
        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Base> call = api.postData(edisplayname, eemail, eno_hp, skategoripengguna, epassword);
        progressDoalog.show();
        call.enqueue(new Callback<Base>() {
            @Override
            public void onResponse(Call<Base> call, Response<Base> response) {
                Base resp = response.body();
                if (response.isSuccessful()) {
                    Toast.makeText(Daftar.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    /*startActivity(new Intent(Daftar.this, Login.class));
                    finish();*/
                    mpProgressDialog.dismiss();
                    /*Intent intent = new Intent(Daftar.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();*/
                    SharedPreferences pref = getSharedPreferences("medical", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isRegist", true);
                    editor.putString("id_user", resp.getData().getId_user());
                    editor.putString("username", resp.getData().getUsername());
                    editor.putString("email", resp.getData().getEmail());
                    editor.putString("nohp", resp.getData().getNohp());
                    editor.putString("kategori", resp.getData().getKategori());
                    editor.putString("password", resp.getData().getPassword());
                    editor.apply();
                    Toast.makeText(Daftar.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Daftar.this,Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                } else {
                    Toast.makeText(Daftar.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<Base> call, Throwable t) {
                progressDoalog.dismiss();
                Log.e("ERROR", t.toString());
            }
        });
    }}



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sjenisuser = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
