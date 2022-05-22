package com.MultiDev.hopeapp.GUIVisores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.MultiDev.hopeapp.Objetos.Sesion;
import com.MultiDev.hopeapp.R;
import com.MultiDev.hopeapp.WebService.Herramientas.ControlSesiones;
import com.MultiDev.hopeapp.WebService.Herramientas.ToolJson;
import com.MultiDev.hopeapp.WebService.Herramientas.ToolsTipos;
import com.MultiDev.hopeapp.WebService.Objetos.ObjRespuestaWS;
import com.MultiDev.hopeapp.WebService.Pacientes.RequestList;
import com.android.volley.Response;

import java.util.Date;

public class VisorPacientesDetalles extends AppCompatActivity {
    private String nombrePaciente,nombreDoctor;
    private String[] info;
    private TextView nombreCompleto,txtEdad, txtCancer,txtEtapa;
    private Button btnRegistrar;
    private Sesion sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pacientes_detalles);
        System.out.println("Paciente nombre del paciente: "+(getIntent().getExtras().getString("idPaciente")));
        this.nombrePaciente =getIntent().getExtras().getString("idPaciente");
        this.sesion = new ControlSesiones(this).leerSesion();
        this.nombreDoctor = this.sesion.getInfoUsuario().split(",")[2] + " "+this.sesion.getInfoUsuario().split(",")[3];
        System.out.println("El nombre del doctor es "+this.nombreDoctor);

        inicializarElementos();
        inicializarEventos();
        new RequestList(VisorPacientesDetalles.this).verInfoPacienteDetallada( new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Respuesta "+response);
                if(!response.isEmpty()){
                    info = new ToolJson().parsearInfoPacienteDetallada(new String[]{"nombre","apellidos","edad","fecha_reg","etapa_cancer","tipo_cancer","foto_perfil","ruta_expediente"},response);
                    nombreCompleto.setText(info[0]+" "+info[1]);
                    txtEdad.setText(info[2]);
                    txtCancer.setText(info[5]);
                    txtEtapa.setText(info[4]);
                }
            }
        }, nombrePaciente);
    }
    private void inicializarElementos(){
        this.nombreCompleto = findViewById(R.id.txtCdNombrePaciente);
        this.txtEdad = findViewById(R.id.txtCdEdad);
        this.txtCancer = findViewById(R.id.txtCdTipoCancer);
        this.txtEtapa  = findViewById(R.id.txtCdEtapaCancer);
        this.btnRegistrar = findViewById(R.id.BtnDPTomarTutela);
    }
    private void inicializarEventos(){
        this.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new com.MultiDev.hopeapp.WebService.Medicos.RequestList(VisorPacientesDetalles.this).InsertarTutela(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Respuesta "+response);
                        if(!response.isEmpty()&&!response.equals("0")){
                            ObjRespuestaWS obj = new ObjRespuestaWS(response, VisorPacientesDetalles.this);
                            if(obj.isStatus()){
                                obj.mostrarRespuesta("Tutela Asignada");
                                finish();
                            }else{
                                obj.mostrarRespuesta("Ha ocurrido un error");
                            }
                        }
                    }
                }, nombreDoctor,nombrePaciente);
            }
        });
    }
}