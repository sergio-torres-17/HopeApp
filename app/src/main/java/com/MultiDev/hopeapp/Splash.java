package com.MultiDev.hopeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    private TextView txt1,txt2;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        inicializarObjetos();
        fadeIn();
    }
    public void inicializarObjetos(){
        this.txt1 = findViewById(R.id.txtSplash1);
        this.txt2 = findViewById(R.id.txtSplash2);
        this.logo = findViewById(R.id.logoSplash);
    }
    public void fadeIn(){
        Animation am1,am2,am3;
        am1 = AnimationUtils.loadAnimation(this,R.anim.up_des);
        am2 = AnimationUtils.loadAnimation(this,R.anim.down_des);
        am3 = AnimationUtils.loadAnimation(this,R.anim.down_des);
        this.txt1.setAnimation(am3);
        this.txt2.setAnimation(am2);
        this.logo.setAnimation(am1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this,Login.class));
                finish();
            }
        },4000);
    }
}