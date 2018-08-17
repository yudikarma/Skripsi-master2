package com.example.gungde.reminder_medicine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.gungde.reminder_medicine.ActivityChats.AcountSettings;
import com.example.gungde.reminder_medicine.ActivityChats.AllUserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;

    //FRAGMENT NAVIGATION
    private Beranda beranda;
    private Artikel artikel;
    private Chats chats;
    private Settings settings;

    private Toolbar mToolbar;

    //Method set freame onselectef Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(beranda);
                    return true;

                case R.id.navigation_artikel:
                    setFragment(artikel);
                    return true;
                case R.id.navigation_chats:
                    setFragment(chats);
                    return true;
                case R.id.navigation_settings:
                    setFragment(settings);
                    return true;




            }
            return false;
        }
    };
    //fIREBASE
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrameLayout = findViewById(R.id.main_frame);

        mToolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Patuh OAT");

        //Getting Uid user
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {


            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        }

        //Inisialisasi Frame Bottom navigation
        beranda = new Beranda();
        artikel = new Artikel();
        chats = new Chats();
        settings = new Settings();
        setFragment(beranda);


        //set bottom navigation method
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
    //Method set Fragment
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
    //MEthod backpressed to exit
    long back_pressed;

    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
    //Method panggil menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;

    }

    //Method Menu selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout_menu_main) {
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

            FirebaseAuth.getInstance().signOut();
            sendTostart();
        } else if (item.getItemId() == R.id.acount_setting_main) {
            Intent i = new Intent(MainActivity.this, AcountSettings.class);
            /* i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
            startActivity(i);
        } else if (item.getItemId() == R.id.All_user) {
            Intent i = new Intent(MainActivity.this, AllUserActivity.class);
            // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }
        return true;
    }
    private void sendTostart() {
        //Check i user is Sign-out
        Intent startIntent = new Intent(MainActivity.this, Login.class);
        startActivity(startIntent);
        finish();

    }
}
