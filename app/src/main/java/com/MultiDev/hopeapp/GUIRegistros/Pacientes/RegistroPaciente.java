package com.MultiDev.hopeapp.GUIRegistros.Pacientes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MultiDev.hopeapp.GUIRegistros.Doctores.RegistroDoctor;
import com.MultiDev.hopeapp.GUIRegistros.ToolsGUI;
import com.MultiDev.hopeapp.Herramientas.Herramientas;
import com.MultiDev.hopeapp.Login;
import com.MultiDev.hopeapp.Objetos.Paciente;
import com.MultiDev.hopeapp.Objetos.Usuario;
import com.MultiDev.hopeapp.R;
import com.MultiDev.hopeapp.WebService.Herramientas.ToolJson;
import com.MultiDev.hopeapp.WebService.Herramientas.ToolsTipos;
import com.MultiDev.hopeapp.WebService.Objetos.ObjRespuestaWS;
import com.MultiDev.hopeapp.WebService.Pacientes.RequestList;
import com.android.volley.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RegistroPaciente extends AppCompatActivity {
    private Usuario usr;
    private EditText edtTiempoDiagnostico,edtSintomasPrevios;
    private AutoCompleteTextView edtEtapaCancer,edtTipoCancer;
    private Button btnFotoPerfil, btnFotoExpediente, btnRegistrar,btnRegresar;
    private RequestList peticiones;
    private Bitmap imgPerfil,imgExpediente;
    private final int C_CAMARA = 200,C_GALERIA = 300;
    private boolean eligiendoFotoPerfil,eligiendoFotoExpediente;
    private ObjRespuestaWS respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);
        this.usr = new Usuario(getIntent().getExtras().getString("usrTxt").split(","));
        this.inicializarObjetos();
        this.cargarPredicciones();
        this.inicializarEventos();
    }
    private void inicializarObjetos(){
        this.edtTipoCancer = findViewById(R.id.edtVRegPacienteTipo);
        this.edtEtapaCancer = findViewById(R.id.edtVRegPacienteEtapa);
        this.edtTiempoDiagnostico = findViewById(R.id.edtVRegPacienteTiempo);
        this.edtSintomasPrevios = findViewById(R.id.edtVRegPacienteSintomas);
        this.btnFotoPerfil = findViewById(R.id.btnVRegPacienteFotoPerfil);
        this.btnFotoExpediente = findViewById(R.id.btnVRegPacienteFotoExp);
        this.btnRegistrar = findViewById(R.id.BtnRegPacienteRegistrar);
        this.btnRegresar = findViewById(R.id.BtnRegPactRegresar);
        this.peticiones = new RequestList(this);
        this.eligiendoFotoPerfil= this.eligiendoFotoExpediente = false;
    }
    private void cargarPredicciones(){
        this.peticiones.cargarTiposCancer(response -> {
            System.out.println("Respuesta tipos cancer: "+response);
            if(!response.isEmpty()&&!response.equals("0")){
                edtTipoCancer.setAdapter(new ArrayAdapter<String>(RegistroPaciente.this, android.R.layout.simple_dropdown_item_1line, new ToolJson().listarTiposCancer(response)));
            }
        });
        this.peticiones.cargarEtapasCancer(response -> {
            System.out.println("Respuesta etapas cancer: "+response);
            if(!response.isEmpty()&&!response.equals("0")){
                edtEtapaCancer.setAdapter(new ArrayAdapter<String>(RegistroPaciente.this, android.R.layout.simple_dropdown_item_1line, new ToolJson().listarEtapasCancer(response)));
            }
        });
    }

    private void inicializarEventos(){
        this.btnFotoPerfil.setOnClickListener(view -> {
            eligiendoFotoPerfil = true;
            final CharSequence[] opciones = {"Tomar Foto", "Elegir de galeria"};
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPaciente.this);
            builder.setTitle("Origen de la imagen: ");
            builder.setItems(opciones, (dialogInterface, i) -> {
                if(i==0)
                    abrirCamara();
                else
                    abrirGaleria();
            });
            builder.show();
        });
        this.btnFotoExpediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] opciones = {"Tomar Foto", "Elegir de galeria"};
                eligiendoFotoExpediente = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPaciente.this);
                builder.setTitle("Origen de la imagen: ");
                builder.setItems(opciones, (dialogInterface, i) -> {
                    if(i==0)
                        abrirCamara();
                    else
                        abrirGaleria();
                });
                builder.show();
            }
        });
        this.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPaciente.this);
                builder.setTitle("Confirmar Registro");
                builder.setMessage("¿Seguro que deseas insertar el usuario?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        validacionEInsercion();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    private void validacionEInsercion() {
        if(validacion()){
            this.usr.setImgPerfil(imgPerfil);
            System.out.println("Pasó validacion");
            new RequestList(RegistroPaciente.this).registroPaciente(new Paciente(this.usr, this.edtEtapaCancer.getText().toString(), this.edtTipoCancer.getText().toString(),
                    this.edtTiempoDiagnostico.getText().toString(), this.imgExpediente), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Respuesta insercion en peticion "+response);
                    if(!response.isEmpty()&&!response.equals("0")){
                        respuesta = new ObjRespuestaWS(response,RegistroPaciente.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPaciente.this);
                        if(respuesta.isStatus()){
                            builder.setTitle("Registro completo");
                            builder.setMessage(respuesta.getMensaje()+"\nLa aplicación se reiniciará.");
                            builder.setPositiveButton("Aceptar ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(RegistroPaciente.this, Login.class));
                                    finish();
                                }
                            });
                        }else{
                            builder.setTitle("Registro no completado");
                            builder.setMessage(respuesta.getMensaje());
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                        }
                        builder.show();

                    }else{
                        Toast.makeText(RegistroPaciente.this, "Error en respuesta", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPaciente.this);
            builder.setTitle("Hay campos vacíos");
            builder.setMessage("Faltan campos por llenar");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
        }



    }

    private void abrirCamara(){
        ToolsGUI.abrirCamara(this);
    }
    private void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"),C_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case C_CAMARA:
                if (requestCode==1&&resultCode==RESULT_OK){
                    Bundle extra = data.getExtras();
                    if(eligiendoFotoPerfil){
                        imgPerfil =(Bitmap) extra.get("data");
                        btnFotoPerfil.setText("Imagen Seleccionada");
                    }
                    else if(eligiendoFotoExpediente){
                        imgExpediente =(Bitmap) extra.get("data");
                        btnFotoExpediente.setText("Imagen Seleccionada ");
                    }
                }
                System.out.println("La imagen perfil es nula: "+(imgPerfil==null));
                break;
            case C_GALERIA:
               if (resultCode == RESULT_OK){
                   Uri path = data.getData();
                   try {
                       if(eligiendoFotoPerfil){
                           imgPerfil = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                           btnFotoPerfil.setText("Imagen Seleccionada");
                           eligiendoFotoPerfil = false;
                       }
                       else if(eligiendoFotoExpediente){
                           imgExpediente = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                           btnFotoExpediente.setText("Imagen Seleccionada ");
                           eligiendoFotoExpediente = false;
                       }
                   } catch (IOException e) {
                       System.err.println("Error al abrir la imagen de la galeria "+e.getMessage());
                   }
               }
                System.out.println("La imagen del expediente es nula: "+(imgExpediente==null));
                break;
        }
    }
    private boolean validacion(){
        return !edtEtapaCancer.getText().toString().equals("")&&
                !edtTipoCancer.getText().toString().equals("")&&
                !edtTiempoDiagnostico.getText().toString().equals("")&&
                this.imgPerfil!=null&&this.imgExpediente!=null;

    }
}