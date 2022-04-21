package com.MultiDev.hopeapp.GUIRegistros.Pacientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.MultiDev.hopeapp.R;

public class RegistroSintomas extends AppCompatActivity {
    private TextView txtTitulo;
    private EditText edtSintoma,edtIntensidad,edtFecha, edtHora, edtDetalles;
    private Button btRegisrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_sintomas);
        this.inicializarComponentes();
    }
    private void inicializarComponentes(){
        this.txtTitulo = findViewById(R.id.txtTituloRegisroSint);
        this.edtSintoma = findViewById(R.id.vRegSintEdtSintoma);
        this.edtIntensidad = findViewById(R.id.vRegSintEdtIntensidad);
        this.edtFecha = findViewById(R.id.vRegSintEdtFecha);
        this.edtHora = findViewById(R.id.vRegSintEdtHora);
        this.edtDetalles = findViewById(R.id.vRegSintEdtDetalles);
        this.btRegisrar = findViewById(R.id.BtnRegSintBtnRegistrar);
    }

}