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
    private static final int GOOGLE_SIGN_IN = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.inicializarValores();
        this.animar();
        this.inicializarEventos();
        //System.out.println("Usuario "+usr.);
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

        //AuthCredential credential = EmailAuthProvider.getCredential(usr.getEmail(), usr.delete())
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
                    loginParaGoogle(cuenta.getEmail(), cuenta.getId());
                }
            }catch(Exception e){
                Toast.makeText(Login.this , "Algo salió mal, intenta nuevamente", Toast.LENGTH_SHORT).show();
                System.out.println("Error al mostrar usuario de google en e LOGIN: "+e.getMessage());
            }
        }
    }
    private void loginParaGoogle(String correo, String id){
        new RequestList(this).login(correo, id, this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                respuesta = new ObjRespuestaWS(response, Login.this);
                if(respuesta.isStatus()){
                    respuesta.mostrarRespuesta("¡Bienvenido!", "Entrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new com.MultiDev.hopeapp.WebService.Genericos.RequestList(Login.this).traerInfoPosLogin(correo, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if(!response.equals("0")&&!response.isEmpty()){
                                        System.out.println("Respuesta Pos login "+response);
                                        infoUsuario = new ToolJson().infoFragmentada(response,new String[]{"id_usuario","IdDoctor","nombre","apellidos","Edad","cedula","Estatus","Email"});
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        intent.putExtra("infoUsuario", infoUsuario);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                    });
                }else{
                    respuesta.mostrarRespuesta("¡Algo salió Mal!", "Ok");
                }
            }
        });
    }
}
