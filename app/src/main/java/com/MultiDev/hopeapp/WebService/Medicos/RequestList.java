package com.MultiDev.hopeapp.WebService.Medicos;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.MultiDev.hopeapp.Herramientas.Herramientas;
import com.MultiDev.hopeapp.Objetos.Doctor;
import com.MultiDev.hopeapp.Objetos.Paciente;
import com.MultiDev.hopeapp.WebService.ConstantesURL;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.nio.file.ProviderMismatchException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;

public class RequestList {
    private Context context;

    public RequestList(Context context) {
        this.context = context;
    }

    public RequestList() {
    }
    public void login(String usr, String pwd, Context context , Response.Listener listener){
        String usr_s = usr,pwd_s=pwd;

        StringRequest peticion = new StringRequest(Request.Method.POST, ConstantesURL.R_LOGIN, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Ocurrió un error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> prm = new HashMap<>();
                prm.put("usr", usr_s);
                prm.put("pwd", pwd_s);
                return prm;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(peticion);
    }
    public void registrarMedico( Doctor doctor,Response.Listener listener){
        StringRequest request = new StringRequest(Request.Method.POST, ConstantesURL.R_REGISTRO_DOCTORES, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error al insertar el doctor: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prm = new HashMap<>();
                prm.put("nombre",doctor.getUsuario().getNombre());
                prm.put("apellidos", doctor.getUsuario().getApellidos());
                prm.put("edad", String.valueOf(doctor.getUsuario().getEdad()));
                prm.put("email", doctor.getUsuario().getEmail());
                prm.put("pass", doctor.getUsuario().getPassword());
                prm.put("cedula", doctor.getCedula());
                prm.put("especialidad", doctor.getEspecialidad());
                prm.put("estudios", doctor.getEstudios());
                prm.put("historial", doctor.getEstudios());
                prm.put("certificado",doctor.getEstudios());
                prm.put("imgPerfil", Herramientas.convertirBinImagen(doctor.getUsuario().getImgPerfil()));
                return prm;
            }
        };
        RequestQueue fila = Volley.newRequestQueue(context);
        fila.getCache().clear();
        fila.add(request);
    }
    public void mostrarPacientes(Response.Listener<String> listener){
        StringRequest request = new StringRequest(0,ConstantesURL.R_VER_PACIENTES_SIN_TUTELA,listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error al solicitar pacientes " + error.getCause());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prm = new HashMap<>();
                prm.put("","");
                return prm;
            }
        };
        RequestQueue fila = Volley.newRequestQueue(this.context);
        fila.getCache().clear();
        fila.add(request);

    }
    public void InsertarTutela(Response.Listener<String> response, String nombreMedico, String nombrePaciente){
        StringRequest request = new StringRequest(Request.Method.POST,ConstantesURL.R_INSERTAR_TUTELA_PARA_PACIENTE,response, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error al solicitar pacientes " + error.getCause());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prm = new HashMap<>();
                prm.put("nombreDoctor",nombreMedico);
                prm.put("nombrePaciente",nombrePaciente);
                prm.put("fechaHora",new Date(System.currentTimeMillis()).toString());
                return prm;
            }
        };
        RequestQueue fila = Volley.newRequestQueue(this.context);
        fila.getCache().clear();
        fila.add(request);
    }
}
