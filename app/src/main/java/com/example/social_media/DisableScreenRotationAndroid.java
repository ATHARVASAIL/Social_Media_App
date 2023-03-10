package com.example.social_media;

// Disable Screen Orientation Change in Android Programmatically

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DisableScreenRotationAndroid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Make to run your application only in LANDSCAPE mode
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_register);
        setContentView(R.layout.activity_splashscreen);
        setContentView(R.layout.fragment1);
        setContentView(R.layout.fragment2);
        setContentView(R.layout.fragment3);
        setContentView(R.layout.fragment4);
    }
}
