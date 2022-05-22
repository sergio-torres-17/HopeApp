package com.MultiDev.hopeapp.GUIRegistros.Doctores;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.MultiDev.hopeapp.GUIRegistros.ToolsGUI;
import com.MultiDev.hopeapp.Herramientas.Herramientas;
import com.MultiDev.hopeapp.Login;
import com.MultiDev.hopeapp.Objetos.Doctor;
import com.MultiDev.hopeapp.Objetos.Usuario;
import com.MultiDev.hopeapp.R;
import com.MultiDev.hopeapp.WebService.Medicos.RequestList;
import com.MultiDev.hopeapp.WebService.Objetos.ObjRespuestaWS;
import com.MultiDev.hopeapp.WebService.ToolsFirebase.FireDb;
import com.android.volley.Response;

import java.sql.SQLOutput;

public class RegistroDoctor extends AppCompatActivity {
    private TextView txtTitulo;
    private EditText txtEspecialidad,txtCedula,txtEstudios,txtExperiencia;
    private Button btnRegistro;
    private Usuario usuario;
    private ImageButton imgUsuario;
    private  Bitmap imgBitmap;
    private ObjRespuestaWS respuesta;
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
        this.imgUsuario = findViewById(R.id.imgFotoRegDoctor);
        this.txtExperiencia = findViewById(R.id.vRegDocEdtExperiencia);
        usuario = new Usuario(getIntent().getExtras().getString("usrTxt").split(","));
    }
    private void inicializarEventos(){
        this.btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacionRegistro();
            }
        });
        this.imgUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolsGUI.abrirCamara(RegistroDoctor.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1&&resultCode==RESULT_OK){
            Bundle extra = data.getExtras();
            imgBitmap =(Bitmap) extra.get("data");
            System.out.println("Salida de bytes de images: "+ Herramientas.convertirBinImagen(imgBitmap));
            this.imgUsuario.setImageBitmap(imgBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean validarCampos(){
        return !txtEspecialidad.getText().toString().equals("")&&
                !txtCedula.getText().toString().equals("")&&
                !txtEstudios.getText().toString().equals("");
    }
    private void confirmacionRegistro(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroDoctor.this);
        builder.setTitle("Confirmar Registro");
        builder.setMessage("¿Deseas confirmar el registro del doctor");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                insertarRegistro();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(RegistroDoctor.this, "Se canceló la inserción del dcotor", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
    private void insertarRegistro(){
        if(validarCampos()){
            this.usuario.setImgPerfil(imgBitmap);
            new RequestList(RegistroDoctor.this).registrarMedico(new Doctor(usuario, txtEspecialidad.getText().toString(), txtCedula.getText().toString(), txtEstudios.getText().toString(), txtExperiencia.getText().toString()), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Respuesta de insercion "+response);
                    if(!response.equals("0")){
                        respuesta = new ObjRespuestaWS(response, RegistroDoctor.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroDoctor.this);
                        if (respuesta.isStatus()){
                            new FireDb(usuario.getNombre()+" "+usuario.getApellidos()).crearColeccionDoctor();
                            builder.setTitle("Registro completo");
                            builder.setMessage(respuesta.getMensaje()+"\nLa aplicación se reiniciará.");
                            builder.setPositiveButton("Aceptar ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(RegistroDoctor.this, Login.class));
                                    RegistroDoctor.this.finish();
                                }
                            });
                        }
                        else{
                            builder.setTitle("Registro no completado");
                            builder.setMessage(respuesta.getMensaje());
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                        }
                        builder.show();
                    }
                }
            });
        }
        else{
        }
    }
}