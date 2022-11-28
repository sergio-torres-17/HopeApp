package com.MultiDev.hopeapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MultiDev.hopeapp.Herramientas.Herramientas;
import com.MultiDev.hopeapp.Herramientas.ServicioNotificacion;
import com.MultiDev.hopeapp.Objetos.Doctor;
import com.MultiDev.hopeapp.Objetos.Paciente;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.TaskStackBuilder;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.MultiDev.hopeapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private String nombre, puesto;
    private boolean quiereSalir, esDoctor;
    private TextView txtNombre, txtPuesto;
    private Doctor doctor;
    private Paciente paciente;
    private Uri xx;
    private Group grupoPacientes;
    private Bitmap imgUsuario;
    private ImageView imgAppUsr;
    private NavigationView navigationView;

    private ServicioNotificacion servicioNotificación;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        this.esDoctor = getIntent().getExtras().getBoolean("esDoctor");
        //xx = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

        if (esDoctor){
            this.doctor = new Doctor(getIntent().getExtras().getString("infoUsuario").split(","));
            Intent intent = new Intent(MainActivity.this, ServicioNotificacion.class);
            intent.putExtra("nombreCompleto",this.doctor.getUsuario().getNombre()+" "+this.doctor.getUsuario().getApellidos());
            startService(intent);
        }
        else
            this.paciente = new Paciente(getIntent().getExtras().getString("infoUsuario").split(","));
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.visorPacientes, R.id.reporteSintomas, R.id.proximasCitas,R.id.miCuenta)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        this.quiereSalir = false;
    }
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        inicializarAppbar();
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        if(!this.quiereSalir){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Confirmar Salida");
            builder.setMessage("¿Seguro que quieres salir?");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    quiereSalir = true;
                    onBackPressed();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }else{
            super.onBackPressed();
        }

    }
    @Override
    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }

    public void inicializarAppbar(){
        this.txtNombre = (TextView) findViewById(R.id.txtNavNombre);
        this.txtPuesto = (TextView)findViewById(R.id.txtNavPuesto);
        this.imgAppUsr = (ImageView) findViewById(R.id.imgNavUsuario);
        this.grupoPacientes = findViewById(R.id.grpPacientes);
        this.imgAppUsr.setImageBitmap(this.imgUsuario);
        if(esDoctor){
            this.txtNombre.setText(this.doctor.getUsuario().getNombre()+" "+this.doctor.getUsuario().getApellidos());
            navigationView.getMenu().setGroupVisible(R.id.grpDoctores,true);
            navigationView.getMenu().setGroupVisible(R.id.grpPacientes,false);
        }
        else{
            this.txtNombre.setText(this.paciente.getUsr().getNombre()+" "+this.paciente.getUsr().getApellidos());
            navigationView.getMenu().setGroupVisible(R.id.grpDoctores,false);
            navigationView.getMenu().setGroupVisible(R.id.grpPacientes,true);
        }
        this.txtPuesto.setText((this.esDoctor)?"Doctor":"Paciente");
    }

}