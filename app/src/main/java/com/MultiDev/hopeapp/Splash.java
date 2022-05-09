package com.MultiDev.hopeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.MultiDev.hopeapp.Herramientas.HerramientasRed;
import com.MultiDev.hopeapp.Objetos.Sesion;
import com.MultiDev.hopeapp.WebService.Herramientas.ControlSesiones;

public class Splash extends AppCompatActivity {
    private TextView txt1,txt2;
    private ImageView logo;
    private ControlSesiones controlSesiones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        controlSesiones = new ControlSesiones(Splash.this);
        System.out.println("Valor del usuario guardado "+controlSesiones.leerSesion().getUsuario());
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
        HerramientasRed.validarConexionServer();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(HerramientasRed.validarConexionAInternet(Splash.this)){
                    if(HerramientasRed.validarConexionServer()){
                        if(!controlSesiones.haySesion()){
                            startActivity(new Intent(Splash.this,Login.class));
                            finish();
                        }else{
                            Sesion sesion = controlSesiones.leerSesion();
                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            intent.putExtra("esDoctor", sesion.isEsDoctor());
                            intent.putExtra("infoUsuario",sesion.getInfoUsuario());
                            startActivity(intent);
                            finish();
                        }

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
                        builder.setTitle("No hay conexion con el servidor");
                        builder.setMessage("Contactar a soporte tecnico");
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                System.exit(0);
                            }
                        });
                        builder.show();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
                    builder.setTitle("No hay conexion a internet");
                    builder.setMessage("Necesitas conexión a internet para abrir la aplicación.\nLa apliación se cerrará");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    });
                    builder.show();
                }
            }
        },3000);
    }
}