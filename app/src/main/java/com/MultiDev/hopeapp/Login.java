package com.MultiDev.hopeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class Login extends AppCompatActivity {
    private EditText txtUser, txtPass;
    private ImageView imgLogo;
    private Button btnLogIn;
    private Animation anim1, anim2;
    private CheckBox chkPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.inicializarValores();
        this.animar();

    }
    private void inicializarValores(){
        this.txtUser = findViewById(R.id.edtUsuario);
        this.txtPass = findViewById(R.id.edtPassword);
        this.btnLogIn= findViewById(R.id.btnLogin);
        this.imgLogo = findViewById(R.id.imgLogoLogin);
        this.chkPass = findViewById(R.id.chkShowPass);
    }
    private void animar(){
        anim1 = AnimationUtils.loadAnimation(Login.this, R.anim.down_des);
        this.txtUser.setAnimation(this.anim1);
        this.txtPass.setAnimation(this.anim1);
        this.btnLogIn.setAnimation(this.anim1);
        this.imgLogo.setAnimation(this.anim1);
        this.chkPass.setAnimation(this.anim1);
    }
}