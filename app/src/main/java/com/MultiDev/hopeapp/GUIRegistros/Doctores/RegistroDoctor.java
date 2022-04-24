package com.MultiDev.hopeapp.GUIRegistros.Doctores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.MultiDev.hopeapp.Objetos.Usuario;
import com.MultiDev.hopeapp.R;

public class RegistroDoctor extends AppCompatActivity {
    private TextView txtTitulo;
    private EditText txtEspecialidad,txtCedula,txtEstudios,txtEvidercia;
    private Button btnRegistro;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_doctor);
        inicializarComponentes();
        inicializarEventos();
    }
    private void inicializarComponentes(){
        this.txtTitulo = findViewById(R.id.txtTituloRegistroDoctor);
        this.txtEspecialidad = findViewById(R.id.vRegDocEdtEspecialidad);
        this.txtCedula = findViewById(R.id.vRegDocEdtCedula);
        this.txtEstudios = findViewById(R.id.vRegDocEdtEstudios);
        this.btnRegistro = findViewById(R.id.BtnRegDoctRegistrar);
        usuario = new Usuario(getIntent().getExtras().getString("usrTxt").split(","));
    }
    private void inicializarEventos(){
        this.btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}