package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new fragment1()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected= null;
            switch (item.getItemId())
            {
                case R.id.profile_bottom:
                    selected=new fragment1();
                    break;

                case R.id.search_bottom:
                    selected=new fragment2();
                    break;

                case R.id.notification_bottom:
                    selected=new fragment3();
                    break;

                case R.id.home_bottom:
                    selected=new fragment4();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selected).commit();

            return true;
        }
    };
    public void logout(View view)
    {
        auth.signOut();
        Intent intent = new Intent(MainActivity.this,Login_Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null)
        {
            Intent intent = new Intent(MainActivity.this,Login_Activity.class);
            startActivity(intent);
            finish();
        }
    }
}