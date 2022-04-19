package com.MultiDev.hopeapp.GUIRegistros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MultiDev.hopeapp.GUIRegistros.Doctores.RegistroDoctor;
import com.MultiDev.hopeapp.GUIRegistros.Pacientes.RegistroPaciente;
import com.MultiDev.hopeapp.R;

public class VentanaRegistroGen extends AppCompatActivity {
    private TextView txtTitulo;
    private LinearLayout layout;
    private EditText txtNombre,txtApellidos,txtEdad,txtEmail,txtPass,txtConfPass;
    private Button btnSiguiente, btnCancelar;
    private boolean esDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_registro_gen);
        inicializarObjetos();
        inicializarEventos();
    }
    private void inicializarObjetos(){
        this.txtTitulo = findViewById(R.id.txtTituloRegistroGen);
        this.layout = findViewById(R.id.vRegisroGenLyInfo);
        this.txtNombre = findViewById(R.id.vRegistroGenedtNombre);
        this.txtApellidos = findViewById(R.id.vRegistroGenEdtApelidos);
        this.txtEdad = findViewById(R.id.vRegistroGenEdtEdad);
        this.txtEmail = findViewById(R.id.vRegistroGenEdtEmail);
        this.txtPass = findViewById(R.id.vRegistroGenEdtPwd);
        this.txtConfPass = findViewById(R.id.vRegistroGenEdtPwdConf);
        this.btnSiguiente = findViewById(R.id.btnRegGenSiguiente);
        this.btnCancelar = findViewById(R.id.btnRegGenCancelar);
        this.esDoctor = getIntent().getExtras().getBoolean("esDoctor");
        System.out.println("Tipo de usuario: "+((this.esDoctor)?"Es un doctor":"Es un paciente"));
    }
    private void inicializarEventos(){
        this.btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(esDoctor){
                    startActivity(new Intent(VentanaRegistroGen.this, RegistroDoctor.class));
                }else{
                    startActivity(new Intent(VentanaRegistroGen.this, RegistroPaciente.class));
                }
            }
        });

    }

}