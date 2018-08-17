package com.example.gungde.reminder_medicine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.model.LoginModel;
import com.example.gungde.reminder_medicine.network.GetDataService;
import com.example.gungde.reminder_medicine.network.RetrofitClientInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


    /*@BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtUsername)
    EditText txtUsername;
    @BindView(R.id.txtPassword)
    EditText txtPassword;*/
    private LinearLayout regiSt,mforgotpassword;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;
    private ProgressDialog mpProgressDialog;


    //Firebase
    private FirebaseAuth mAuth;
    //firebase user database
    private DatabaseReference muserDatabaseReference;

    private  String email;
    private String password;
    private  String Username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        regiSt =  findViewById(R.id.tvRegister);
        mpProgressDialog = new ProgressDialog(this);

        //forgotpassword
//        mforgotpassword = findViewById(R.id.tvforgotpassword);
//        mforgotpassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               /* startActivity(new Intent(Login.this,ResetPasswordActivity.class));*/
//
//            }
//        });


        //firebase user database
        muserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        //Auth user
        mAuth = FirebaseAuth.getInstance();
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmailView.getText().toString();
                password = mPasswordView.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    mpProgressDialog.setTitle("login..");
                    mpProgressDialog.setMessage("we are try connect your acount..");
                    mpProgressDialog.setCanceledOnTouchOutside(false);
                    mpProgressDialog.show();
                    /* ======== Call method Login with email && password ========*/


                    loginUserfirebase(email,password);

                }else {
                    mpProgressDialog.hide();
                    Toast.makeText(Login.this,"please insert your email and password login",Toast.LENGTH_SHORT).show();
                }

            }
        });


        //intent for registrasi
        regiSt =  findViewById(R.id.tvRegister);
        regiSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this,Daftar.class));

            }
        });

    }

    //METHOD FOR LOGIN WiTH EMAIL && PASSWORD FIREBASE
    private void loginUserfirebase(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //Token User for send Notification to device nantik
                    final String device_token = FirebaseInstanceId.getInstance().getToken();
                    final String current_user_id = mAuth.getCurrentUser().getUid();

                    muserDatabaseReference.child(current_user_id).child("device_token").setValue(device_token).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mpProgressDialog.dismiss();


                            //Onsuccess call login Custom API
                            login(email,password);

                        }
                    });


                }else{
                    mpProgressDialog.hide();
                    Toast.makeText(Login.this,"Maaf Proses LoginActivity gagal, silahkan periksa koneksi jaringan anda.. Terima Kasih",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //LOGIN METHOD CUSTOM API
    public void login(final String username, final String password) {

            GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<LoginModel> call = api.LoginUser(username, password);

            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    mpProgressDialog.dismiss();
                    LoginModel resp = response.body();
                    if (response.isSuccessful()) {
                        mpProgressDialog.dismiss();
                        SharedPreferences pref = getSharedPreferences("medical", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("isLogin", true);
                        editor.putString("id_user", resp.getData().getId_user());
                        editor.putString("username", resp.getData().getUsername());
                        editor.putString("email", resp.getData().getEmail());
                        editor.putString("nohp", resp.getData().getNohp());
                        editor.putString("kategori", resp.getData().getKategori());
                        editor.putString("password", resp.getData().getPassword());
                        editor.apply();
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        finish();
                        /*startActivity(new Intent(Login.this, Beranda.class));
                        finish();*/
                    } else {
                        Toast.makeText(Login.this, "username atau password salah!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Login.this, ""+username+","+password, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    mpProgressDialog.dismiss();
                    Log.e("ERROR", t.toString());
                }
            });
        }



    public void register() {
        startActivity(new Intent(Login.this, Daftar.class));
    }
}
