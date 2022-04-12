package com.MultiDev.hopeapp.GUIRegistros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.MultiDev.hopeapp.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class VentanaRegistroUno extends AppCompatActivity {
    private TextView txtTitulo;
    private LinearLayout layout;
    private RadioButton rbPaciente, rbMedico;
    private Button btnEmail, btnGoogle;
    private boolean esDoctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_registro_uno);
        inicializar();
        inicializarEventos();
    }
    private void inicializar(){
        this.txtTitulo = findViewById(R.id.txtTituloRegistroUno);
        this.layout = findViewById(R.id.lyOpcionesRegistro);
        this.rbPaciente = findViewById(R.id.rbPaciente);
        this.rbMedico = findViewById(R.id.rbDoctor);
        this.btnEmail = findViewById(R.id.btnRegistroEmail);
        this.btnGoogle = findViewById(R.id.btnRegistroGoogle);
    }
    private void inicializarEventos(){
        this.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validacionInicializacion()){
                    Intent intent = new Intent(VentanaRegistroUno.this, VentanaRegistroGen.class);
                    intent.putExtra("esDoctor",esDoctor);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(VentanaRegistroUno.this);
                    builder.setTitle("Debe seleccionar el tipo de usuario");
                    builder.setMessage("Debes seleccionar si eres paciente o doctor");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }
        });
    }
    private boolean validacionInicializacion(){
        if(this.rbMedico.isChecked()){
            this.esDoctor = true;
            return true;
        }else if(this.rbPaciente.isChecked()){
            this.esDoctor = false;
            return true;
        }else{
            this.esDoctor = false;
            return false;
        }
    }
}