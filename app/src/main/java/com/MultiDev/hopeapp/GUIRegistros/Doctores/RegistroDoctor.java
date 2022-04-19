package com.MultiDev.hopeapp.GUIRegistros.Doctores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.MultiDev.hopeapp.R;

public class RegistroDoctor extends AppCompatActivity {
    private TextView txtTitulo;
    private EditText txtEspecialidad,txtCedula,txtEstudios,txtEvidercia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_doctor);
    }
    private void inicializarComponentes(){
        this.txtTitulo = findViewById(R.id.txtTituloRegistroDoctor);
        this.txtEspecialidad = findViewById(R.id.vRegDocEdtEspecialidad);
        this.txtCedula = findViewById(R.id.vRegDocEdtCedula);
        this.txtEstudios = findViewById(R.id.vRegDocEdtEstudios);
    }
}