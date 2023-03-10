package com.example.social_media;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Splashscreen extends AppCompatActivity {

    ImageView imageView;
    TextView nameTv,nameTv2;
    long Time = 2000;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        imageView=findViewById(R.id.iv_Logo_splash);
        nameTv=findViewById(R.id.tv_name);
        nameTv2=findViewById(R.id.tv_splash_name);

        ObjectAnimator animatorY=ObjectAnimator.ofFloat(imageView,"y",400f);
        ObjectAnimator animator_name=ObjectAnimator.ofFloat(nameTv,"x",350f);
        ObjectAnimator animator_name2=ObjectAnimator.ofFloat(nameTv2,"x",350f);
        animatorY.setDuration(Time);
        animator_name.setDuration(Time);
        animator_name2.setDuration(Time);
        AnimatorSet animatorset=new AnimatorSet();
        animatorset.playTogether(animatorY,animator_name,animator_name2);
        animatorset.start();

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splashscreen.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}