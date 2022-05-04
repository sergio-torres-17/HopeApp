package com.MultiDev.hopeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.MultiDev.hopeapp.GUIRegistros.VentanaRegistroUno;
import com.MultiDev.hopeapp.WebService.Herramientas.ToolJson;
import com.MultiDev.hopeapp.WebService.Medicos.RequestList;
import com.MultiDev.hopeapp.WebService.Objetos.ObjRespuestaWS;
import com.android.volley.Response;

import org.json.JSONArray;

import java.security.PrivateKey;

public class Login extends AppCompatActivity {
    private EditText txtUser, txtPass;
    private ImageView imgLogo;
    private Button btnLogIn, btnLogInGoogle;
    private TextView preTxtRegisterLogin,txtClkRegister;
    private Animation anim1, anim2;
    private CheckBox chkPass;
    private ObjRespuestaWS respuesta;
    private String infoUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.inicializarValores();
        this.animar();
        this.inicializarEventos();
    }
    private void inicializarValores(){
        this.txtUser = findViewById(R.id.edtUsuario);
        this.txtPass = findViewById(R.id.edtPassword);
        this.btnLogIn= findViewById(R.id.btnLogin);
        this.imgLogo = findViewById(R.id.imgLogoLogin);
        this.chkPass = findViewById(R.id.chkShowPass);
        this.btnLogInGoogle = findViewById(R.id.btnLoginGoogle);
        this.preTxtRegisterLogin = findViewById(R.id.txtPreRegLogin);
        this.txtClkRegister = findViewById(R.id.txtClkRegistro);
    }
    private void animar(){
        anim1 = AnimationUtils.loadAnimation(Login.this, R.anim.down_des);
        this.txtUser.setAnimation(this.anim1);
        this.txtPass.setAnimation(this.anim1);
        this.btnLogIn.setAnimation(this.anim1);
        this.imgLogo.setAnimation(this.anim1);
        this.chkPass.setAnimation(this.anim1);
        this.btnLogInGoogle.setAnimation(this.anim1);
        this.preTxtRegisterLogin.setAnimation(this.anim1);
        this.txtClkRegister.setAnimation(this.anim1);
    }
    private void inicializarEventos(){
        this.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validarCampos()){
                    new com.MultiDev.hopeapp.WebService.Genericos.RequestList(Login.this).traerInfoPosLogin(txtUser.getText().toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(!response.equals("0")&&!response.isEmpty()){
                                System.out.println("Respuesta Pos login "+response);
                                infoUsuario = new ToolJson().infoFragmentada(response,new String[]{"id_usuario","IdDoctor","nombre","apellidos","Edad","cedula","Estatus","Email"});
                            }
                        }
                    });
                    loginMaestro();
                }else{
                    AlertDialog.Builder ad = new AlertDialog.Builder(Login.this);
                    ad.setTitle("Campos Vacíos");
                    ad.setMessage("Uno o más campos están vacíos\nFavor de validar.");
                    ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                }
            }
        });
        this.txtClkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, VentanaRegistroUno.class));
            }
        });
    }
    private boolean validarCampos(){
        return (this.txtUser.getText().toString().length()>0&&
                this.txtPass.getText().toString().length()>0);
    }
    private void loginMaestro(){
        new RequestList(Login.this).login(txtUser.getText().toString(), txtPass.getText().toString(), Login.this,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    respuesta = new ObjRespuestaWS(response, Login.this);
                    if(respuesta.isStatus()){

                            respuesta.mostrarRespuesta("¡Bienvenido!", "Entrar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("infoUsuario", infoUsuario);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    }else{
                        respuesta.mostrarRespuesta("¡Algo salió Mal!", "Ok");
                    }
                }
            }
        });
    }
}