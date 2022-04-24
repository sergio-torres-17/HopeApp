package com.MultiDev.hopeapp.GUIRegistros;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.MultiDev.hopeapp.GUIRegistros.Doctores.RegistroDoctor;
import com.MultiDev.hopeapp.GUIRegistros.Pacientes.RegistroPaciente;
import com.MultiDev.hopeapp.Objetos.Usuario;
import com.MultiDev.hopeapp.R;
import com.MultiDev.hopeapp.WebService.Genericos.RequestList;
import com.MultiDev.hopeapp.WebService.Herramientas.ToolJson;
import com.android.volley.Response;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;


public class VentanaRegistroGen extends AppCompatActivity {
    private TextView txtTitulo;
    private LinearLayout layout;
    private EditText txtNombre,txtApellidos,txtEdad,txtEmail,txtPass,txtConfPass;
    private Button btnSiguiente, btnCancelar;
    private boolean esDoctor;
    private final int GOOGLE_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_registro_gen);
        inicializarObjetos();
        inicializarEventos();
    }
    private void inicializarObjetos(){
        this.txtTitulo = findViewById(R.id.txtTituloRegistroGen);
        this.layout = findViewById(R.id.vRegisroGenLyInfo);
        this.txtNombre = findViewById(R.id.vRegistroGenedtNombre);
        this.txtApellidos = findViewById(R.id.vRegistroGenEdtApelidos);
        this.txtEdad = findViewById(R.id.vRegistroGenEdtEdad);
        this.txtEmail = findViewById(R.id.vRegistroGenEdtEmail);
        this.txtPass = findViewById(R.id.vRegistroGenEdtPwd);
        this.txtConfPass = findViewById(R.id.vRegistroGenEdtPwdConf);
        this.btnSiguiente = findViewById(R.id.btnRegGenSiguiente);
        this.btnCancelar = findViewById(R.id.btnRegGenCancelar);
        this.esDoctor = getIntent().getExtras().getBoolean("esDoctor");
        System.out.println("Tipo de usuario: "+((this.esDoctor)?"Es un doctor":"Es un paciente"));
    }
    private void inicializarEventos(){
        this.btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(validacionCampos()){
                   if( validarConfirmacionPass()){
                       if(esDoctor){
                           Intent intent = new Intent(VentanaRegistroGen.this, RegistroDoctor.class);
                           Usuario usuario = new Usuario(txtNombre.getText().toString(), txtApellidos.getText().toString(), Integer.parseInt(txtEdad.getText().toString()), txtEmail.getText().toString(), txtConfPass.getText().toString());
                           intent.putExtra("usrTxt", usuario.toString());
                           startActivity(intent);
                       }else{
                           Intent intent = new Intent(VentanaRegistroGen.this, RegistroPaciente.class);
                           Usuario usuario = new Usuario(txtNombre.getText().toString(), txtApellidos.getText().toString(), Integer.parseInt(txtEdad.getText().toString()), txtEmail.getText().toString(), txtConfPass.getText().toString());
                           intent.putExtra("usrTxt", usuario.toString());
                           startActivity(intent);
                       }
                   }else{
                       AlertDialog.Builder builder = new AlertDialog.Builder(VentanaRegistroGen.this);
                       builder.setTitle("Error en la contraseña");
                       builder.setMessage("La contraseña y su confirmación no coinciden");
                       builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       builder.show();
                   }
               }else{
                   AlertDialog.Builder builder = new AlertDialog.Builder(VentanaRegistroGen.this);
                   builder.setTitle("Faltan datos por llenar");
                   builder.setMessage("Uno o mas cuadros de infromación están vacíos");
                   builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                       }
                   });
                   builder.show();
               }
            }
        });
        this.txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                rutinaSecundariaValidacion();
            }
        });
        this.txtApellidos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                rutinaSecundariaValidacion();
            }
        });
        this.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                rutinaSecundariaValidacion();
            }
        });
        this.txtEdad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                rutinaSecundariaValidacion();
            }
        });
        this.txtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void afterTextChanged(Editable editable) {
                rutinaSecundariaValidacion();
            }
        });
        this.txtConfPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

           @Override
            public void afterTextChanged(Editable editable) {
               rutinaSecundariaValidacion();
            }
        });
    }
    private boolean validacionCampos(){
        return !this.txtNombre.getText().toString().equals("")&&
                !this.txtApellidos.getText().toString().equals("")&&
                !this.txtEdad.getText().toString().equals("")&&
                !this.txtEmail.getText().toString().equals("")&&
                !this.txtPass.getText().toString().equals("");
    }
    private boolean validarConfirmacionPass(){
        return this.txtPass.getText().toString().equals(this.txtConfPass.getText().toString());
    }
    private void validarUsuarioExistente(){
        new RequestList(VentanaRegistroGen.this).validarExistenciaDeUsuario(txtNombre.getText().toString(), txtApellidos.getText().toString(), txtEmail.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty() && !response.equals("0")){
                    System.out.println("Respuesta de la peticion "+response);
                    JSONObject res = ToolJson.extraerSolUnElemento(response);
                    int code = -1;
                    try {
                        code =res.getInt("Existe");
                    } catch (JSONException e) {
                        System.err.println(e.getMessage());
                    }
                    if(code==0){
                        btnSiguiente.setEnabled(true);
                        Toast.makeText(VentanaRegistroGen.this, "El usuario es valido para ser registrado", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        btnSiguiente.setEnabled(false);
                        Toast.makeText(VentanaRegistroGen.this, "El usuario ya existe en el sistema", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void rutinaSecundariaValidacion(){
        if(validacionCampos())
            validarUsuarioExistente();
        else
            this.btnSiguiente.setEnabled(false);
    }
}