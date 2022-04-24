package com.MultiDev.hopeapp.GUIRegistros;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.MultiDev.hopeapp.GUIRegistros.Doctores.RegistroDoctor;
import com.MultiDev.hopeapp.GUIRegistros.Pacientes.RegistroPaciente;
import com.MultiDev.hopeapp.Objetos.Usuario;
import com.MultiDev.hopeapp.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import kotlinx.coroutines.internal.ExceptionsConstuctorKt;

public class VentanaRegistroUno extends AppCompatActivity {
    private static final int GOOGLE_SIGN_IN = 100;
    private TextView txtTitulo;
    private LinearLayout layout;
    private RadioButton rbPaciente, rbMedico;
    private Button btnEmail, btnGoogle;
    private boolean esDoctor;
    private FirebaseAuth auth;
    private GoogleSignInClient account;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
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
        this.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validacionInicializacion()){
                    autenticacionGoogle();
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


    public void autenticacionGoogle(){
        GoogleSignInOptions bu = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("933262354310-o5pqun9rlh3iegv4mudi83l9sr1dk9c0.apps.googleusercontent.com")
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(VentanaRegistroUno.this, bu);
        startActivityForResult(googleSignInClient.getSignInIntent(), GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task =  GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount cuenta = task.getResult();
                if(cuenta !=null){
                    AuthCredential credencial = GoogleAuthProvider.getCredential(cuenta.getIdToken(),null);
                    FirebaseAuth.getInstance().signInWithCredential(credencial);
                    mostrarCuadroEdad(cuenta.getGivenName(), cuenta.getFamilyName(), cuenta.getEmail());

                }
            }catch (Exception e){
                Toast.makeText(VentanaRegistroUno.this , "Algo salió mal, intenta nuevamente", Toast.LENGTH_SHORT).show();
            }
        }
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

    private void mostrarCuadroEdad(String nombre, String apellidos, String correo){
        EditText txtEdad = new EditText(VentanaRegistroUno.this);

        AlertDialog.Builder cuadrito= new AlertDialog.Builder(VentanaRegistroUno.this);
        cuadrito.setTitle("Introduce tu edad");
        cuadrito.setView(txtEdad);
        cuadrito.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(esDoctor){
                    Intent intent = new Intent(VentanaRegistroUno.this, RegistroDoctor.class);
                    intent.putExtra("usrTxt", nombre+","+apellidos+","+correo);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(VentanaRegistroUno.this, RegistroPaciente.class);
                    intent.putExtra("usrTxt", nombre+","+apellidos+","+correo);
                    startActivity(intent);
                }
            }
        });
        cuadrito.show();
    }

}