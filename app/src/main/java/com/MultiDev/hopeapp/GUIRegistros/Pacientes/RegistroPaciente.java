package com.MultiDev.hopeapp.GUIRegistros.Pacientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.MultiDev.hopeapp.Objetos.Usuario;
import com.MultiDev.hopeapp.R;

public class RegistroPaciente extends AppCompatActivity {
    private Usuario usr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);
        usr = new Usuario(getIntent().getExtras().getString("usrTxt").split(","));
    }
}