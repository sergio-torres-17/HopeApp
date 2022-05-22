package com.MultiDev.hopeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.MultiDev.hopeapp.GUIRegistros.VentanaRegistroUno;
import com.MultiDev.hopeapp.WebService.Herramientas.ControlSesiones;
import com.MultiDev.hopeapp.WebService.Herramientas.ToolJson;
import com.MultiDev.hopeapp.WebService.Medicos.RequestList;
import com.MultiDev.hopeapp.WebService.Objetos.ObjRespuestaWS;
import com.android.volley.Response;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.util.Date;

public class Login extends AppCompatActivity {
    private EditText txtUser, txtPass;
    private ImageView imgLogo;
    private Button btnLogIn, btnLogInGoogle;
    private TextView preTxtRegisterLogin,txtClkRegister;
    private Animation anim1, anim2;
    private CheckBox chkPass;
    private ObjRespuestaWS respuesta;
    private boolean esDoctor,esGoogle;
    private String infoUsuario;
    private static final int GOOGLE_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.inicializarValores();
        this.animar();
        this.inicializarEventos();
        System.out.println("Fecha "+new Date(System.currentTimeMillis()).toString());
        esGoogle = false;
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
                    obtenerTipoLogin(txtUser.getText().toString(), null);
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
        this.btnLogInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacionGoogle();
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
    /**********************************/
    /*******Login para doctores********/
    /***********************************/
    private void loginMaestroDoctores(){
        new RequestList(Login.this).login(txtUser.getText().toString(), txtPass.getText().toString(), Login.this,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    respuesta = new ObjRespuestaWS(response, Login.this);
                    if(respuesta.isStatus()){

                            respuesta.mostrarRespuesta("¡Bienvenido!", "Entrar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    traerInfoPosLoginMedicos();
                                }
                            });
                    }else{
                        respuesta.mostrarRespuesta("¡Algo salió Mal!", "Ok");
                    }
                }
            }
        });
    }
    /**********************************/
    /*******Login para pacientes********/
    /***********************************/
    public void loginPacientes(){
        new com.MultiDev.hopeapp.WebService.Pacientes.RequestList(Login.this).login(txtUser.getText().toString(), txtPass.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    System.err.println("Respuesta Login "+response);
                    respuesta = new ObjRespuestaWS(response, Login.this);
                    if(respuesta.isStatus()){

                        respuesta.mostrarRespuesta("¡Bienvenido!", "Entrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                traerInfoPosLoginPacientes();

                            }
                        });
                    }else{
                        respuesta.mostrarRespuesta("¡Algo salió Mal!", "Ok");
                    }
                }
            }
        });
    }
    //Parte de autenticación de Google
    public void autenticacionGoogle(){
        GoogleSignInOptions bu = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.firmaGoogleHopeapp))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(Login.this, bu);
        startActivityForResult(googleSignInClient.getSignInIntent(), GOOGLE_SIGN_IN);
    }
    private void borrarUsuario(){
        FirebaseUser usr =  FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth fa = FirebaseAuth.getInstance();
        if(usr!=null){
            fa.signOut();
            usr.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "Se borro la credencial", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount cuenta = task.getResult();
                if(cuenta !=null){
                    AuthCredential credencial = GoogleAuthProvider.getCredential(cuenta.getIdToken(),null);
                    FirebaseAuth.getInstance().signInWithCredential(credencial);
                    esGoogle = true;
                    tipoLoginGoogle(cuenta.getEmail(), cuenta.getId());
                }
            }catch(Exception e){
                Toast.makeText(Login.this , "Algo salió mal, intenta nuevamente", Toast.LENGTH_SHORT).show();
                System.out.println("Error al mostrar usuario de google en e LOGIN: "+e.getMessage());
            }
        }
    }
    private void tipoLoginGoogle(String correo, String id){
        obtenerTipoLogin(correo, id);

    }
    private void obtenerTipoLogin(String correo, @Nullable String id){

        new com.MultiDev.hopeapp.WebService.Genericos.RequestList(Login.this).verTipoDeUsuario(correo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.err.println("Respuesta "+response);
                if(!response.equals("0")&&!response.isEmpty()){
                    try {
                        JSONObject object = new JSONArray(response).getJSONObject(0);
                        String tipoUsuario = object.getString("tipo_usuario");
                        switch (tipoUsuario){
                            case "doctor":{
                                //Login doctor
                                esDoctor = true;
                                if(!esGoogle)
                                    loginMaestroDoctores();
                                else
                                    loginMaestroDoctoresGoogle(correo, id);

                                break;
                            }
                            case "paciente":{
                                //Login doctor
                                esDoctor = false;
                                if(!esGoogle)
                                    loginPacientes();
                                else
                                    loginPacientesMaestroGoogle(correo, id);
                                break;
                            }
                            case "No existe":{
                                //Login doctor
                                esDoctor = false;
                                Toast.makeText(Login.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }catch (Exception e){

                    }
                }
            }
        });
    }

    private void loginPacientesMaestroGoogle(String correo, String id) {
        new com.MultiDev.hopeapp.WebService.Pacientes.RequestList(Login.this).login(correo, id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                respuesta = new ObjRespuestaWS(response, Login.this);
                if(respuesta.isStatus()){
                    respuesta.mostrarRespuesta("¡Bienvenido!", "Entrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            traerInfoPosLoginPacientesGoogle(correo);
                        }
                    });
                }else{
                    respuesta.mostrarRespuesta("¡Algo salió Mal!", "Ok");
                }
            }
        });
    }

    private void loginMaestroDoctoresGoogle(String correo, String id) {
        new RequestList(this).login(correo, id, this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                respuesta = new ObjRespuestaWS(response, Login.this);
                if(respuesta.isStatus()){
                    respuesta.mostrarRespuesta("¡Bienvenido!", "Entrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            traerInfoPosLoginMedicosGoogle(correo);
                        }
                    });
                }else{
                    respuesta.mostrarRespuesta("¡Algo salió Mal!", "Ok");
                }
            }
        });
    }

    private void traerInfoPosLoginMedicos(){
        new com.MultiDev.hopeapp.WebService.Genericos.RequestList(Login.this).traerInfoPosLogin(txtUser.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("0")&&!response.isEmpty()){
                    System.out.println("Respuesta Pos login "+response);
                    infoUsuario = new ToolJson().infoFragmentada(response,new String[]{"id_usuario","IdDoctor","nombre","apellidos","Edad","cedula","Estatus","Email"});
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("esDoctor", esDoctor);
                    intent.putExtra("infoUsuario", infoUsuario);
                    new ControlSesiones(Login.this).guardarSesion(txtUser.getText().toString(), esDoctor,infoUsuario);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void traerInfoPosLoginPacientes(){
        new com.MultiDev.hopeapp.WebService.Pacientes.RequestList(this).traerInfoPosLoginPacientes(txtUser.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("0")&&!response.isEmpty()){
                    System.out.println("Respuesta Pos login "+response);
                    infoUsuario = new ToolJson().infoFragmentada(response,new String[]{"id_usuario","idPaciente","nombre","apellidos","edad","email","etapa_cancer","tipo_cancer"});
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("esDoctor", esDoctor);
                    intent.putExtra("infoUsuario", infoUsuario);
                    new ControlSesiones(Login.this).guardarSesion(txtUser.getText().toString(), esDoctor,infoUsuario);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void traerInfoPosLoginMedicosGoogle(String usuario){
        new com.MultiDev.hopeapp.WebService.Genericos.RequestList(Login.this).traerInfoPosLogin(usuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("0")&&!response.isEmpty()){
                    System.out.println("Respuesta Pos login "+response);
                    infoUsuario = new ToolJson().infoFragmentada(response,new String[]{"id_usuario","IdDoctor","nombre","apellidos","Edad","cedula","Estatus","Email"});
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("esDoctor", esDoctor);
                    intent.putExtra("infoUsuario", infoUsuario);
                    new ControlSesiones(Login.this).guardarSesion(txtUser.getText().toString(), esDoctor,infoUsuario);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void traerInfoPosLoginPacientesGoogle(String usuario){
        new com.MultiDev.hopeapp.WebService.Pacientes.RequestList(this).traerInfoPosLoginPacientes(usuario, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("0")&&!response.isEmpty()){
                    System.out.println("Respuesta Pos login "+response);
                    infoUsuario = new ToolJson().infoFragmentada(response,new String[]{"id_usuario","idPaciente","nombre","apellidos","edad","email","etapa_cancer","tipo_cancer"});
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("esDoctor", esDoctor);
                    intent.putExtra("infoUsuario", infoUsuario);
                    new ControlSesiones(Login.this).guardarSesion(txtUser.getText().toString(), esDoctor,infoUsuario);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
