package com.MultiDev.hopeapp.GUIRegistros.Pacientes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.MultiDev.hopeapp.R;
import com.MultiDev.hopeapp.WebService.Herramientas.ToolJson;
import com.MultiDev.hopeapp.WebService.Objetos.ObjRespuestaWS;
import com.MultiDev.hopeapp.WebService.Objetos.Sintoma;
import com.MultiDev.hopeapp.WebService.Pacientes.RequestList;
import com.MultiDev.hopeapp.WebService.ToolsFirebase.FireDb;
import com.android.volley.Response;

import java.util.Calendar;

public class RegistroSintomas extends AppCompatActivity {
    private TextView txtTitulo;
    private EditText edtFecha, edtHora, edtDetalles;
    private AutoCompleteTextView edtSintoma,edtIntensidad;
    private Button btRegisrar;
    private RequestList requestList;
    private Calendar calendar;
    private int idPaciente;
    private String nombreDoctor,nombrePaciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_sintomas);
        this.inicializarComponentes();
        this.inicializarEventos();
        idPaciente = 1;
        nombrePaciente = "Daniela Romo Trejo";
        new RequestList(this).verIdDoctorACargo(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Respuesta del doctor "+response);
                if(!response.isEmpty()){

                    nombreDoctor = new ToolJson().verNombreDoctorACargo(response);
                    System.out.println("Nombre del doctor a cargo "+nombreDoctor);
                }
            }
        }, 1);
    }
    private void inicializarComponentes(){
        this.txtTitulo = findViewById(R.id.txtTituloRegisroSint);
        this.edtSintoma = findViewById(R.id.vRegSintEdtSintoma);
        this.edtIntensidad = findViewById(R.id.vRegSintEdtIntensidad);
        this.edtFecha = findViewById(R.id.vRegSintEdtFecha);
        this.edtHora = findViewById(R.id.vRegSintEdtHora);
        this.edtDetalles = findViewById(R.id.vRegSintEdtDetalles);
        this.btRegisrar = findViewById(R.id.BtnRegSintBtnRegistrar);
        this.requestList = new RequestList(this);
        this.calendar = Calendar.getInstance();
    }
    private void inicializarEventos(){
        this.requestList.traerSintomas(response -> {
            if(!response.isEmpty()){
                edtSintoma.setAdapter(new ArrayAdapter<>(RegistroSintomas.this, android.R.layout.simple_dropdown_item_1line, new ToolJson().listarSintomas(response)));
            }
        });
        this.requestList.traerIntensidadSintomas(response -> {
            if(!response.isEmpty()){
                edtIntensidad.setAdapter(new ArrayAdapter<String>(RegistroSintomas.this, android.R.layout.simple_dropdown_item_1line, new ToolJson().listarIntensidadSintomas(response)));
            }
        });
        this.edtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCalendario();
            }
        });
        this.edtHora.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    abrirReloj();
            }
        });
        this.btRegisrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validacion()){
                    new RequestList(RegistroSintomas.this).insertarSintomas(new Sintoma(idPaciente, edtSintoma.getText().toString(), edtHora.getText().toString(), edtSintoma.getText().toString(),
                            edtDetalles.getText().toString(), (edtFecha.getText().toString())), new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            System.out.println("Respuesta: "+response);
                            if(!response.isEmpty()&&! response.equals("0")){
                                new FireDb(nombreDoctor).insertarSintoma(edtSintoma.getText().toString(), nombrePaciente);

                                ObjRespuestaWS objRespuestaWS = new ObjRespuestaWS(response, RegistroSintomas.this);
                                objRespuestaWS.mostrarRespuesta("El sintoma fue registrado exitosamente", "Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
    //Eventos
    public void abrirCalendario(){
        int dia, mes,anio;

        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anio = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(RegistroSintomas.this, 0, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                System.out.println("Año "+i);
                System.out.println("Mes: "+(i1+1));
                System.out.println("Day: "+(i2));
                edtFecha.setText(+i+"/"+i1+"/"+i2);
            }
        }, anio, mes,dia);
        dialog.setTitle("Fecha del sintoma");
        dialog.show();
    }
    public  void abrirReloj(){
        int hora, minuto;
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        minuto   = calendar.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                edtHora.setText(i+":"+i1);
            }
        }, hora,minuto, true);
        dialog.setTitle("Selecciona la hora del sintoma");
        dialog.show();
    }
    private boolean validacion(){
        return !edtSintoma.getText().toString().equals("")&&
               !edtIntensidad.getText().toString().equals("")&&
               !edtFecha.getText().toString().equals("")&&
               !edtHora.getText().toString().equals("");
    }
}