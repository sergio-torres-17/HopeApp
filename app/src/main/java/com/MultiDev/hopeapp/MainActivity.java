package com.MultiDev.hopeapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.TextView;

import com.MultiDev.hopeapp.Objetos.Doctor;
import com.MultiDev.hopeapp.Objetos.Usuario;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.TaskStackBuilder;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.MultiDev.hopeapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private String nombre, puesto;
    private boolean quiereSalir;
    private TextView txtNombre, txtPuesto;
    private Doctor doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        System.out.println("Info Usuario"+getIntent().getExtras().getString("infoUsuario"));
        this.doctor = new Doctor(getIntent().getExtras().getString("infoUsuario").split(","));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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
        this.txtNombre.setText(this.doctor.getUsuario().getNombre()+" "+this.doctor.getUsuario().getApellidos());
        this.txtPuesto.setText("Doctor");

    }

}