package com.MultiDev.hopeapp.WebService.Pacientes;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.MultiDev.hopeapp.Herramientas.Herramientas;
import com.MultiDev.hopeapp.Objetos.Paciente;
import com.MultiDev.hopeapp.WebService.ConstantesURL;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RequestList {
    private Context context;

    public RequestList(Context context) {
        this.context = context;
    }

    public void cargarTiposCancer(Response.Listener<String> eventoPeticion){
        StringRequest peticion = new StringRequest(Request.Method.GET, ConstantesURL.R_MOSTRAR_TIPOS_CANCER, eventoPeticion, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error al hacer la petición: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue fila = Volley.newRequestQueue(this.context);
        fila.getCache().clear();
        fila.add(peticion);
    }
    public void cargarEtapasCancer(Response.Listener<String> eventoPeticion){
        StringRequest peticion = new StringRequest(Request.Method.GET, ConstantesURL.R_MOSTRAR_ETAPAS_CANCER, eventoPeticion, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error al hacer la petición (Etapas cancer): "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue fila = Volley.newRequestQueue(this.context);
        fila.getCache().clear();
        fila.add(peticion);
    }

   public void registroPaciente(Paciente paciente,Response.Listener<String> eventoPeticion){
       StringRequest peticion = new StringRequest(Request.Method.POST, ConstantesURL.R_INSERTAR_PACIENTE, eventoPeticion, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(context, "Error al hacer la petición (Registro de paciente): "+error.getMessage(), Toast.LENGTH_LONG).show();
           }
       }){
           @Nullable
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> prm = new HashMap<>();
               prm.put("nombres",paciente.getUsr().getNombre());
               prm.put("apellidos",paciente.getUsr().getApellidos());
               prm.put("edad",String.valueOf(paciente.getUsr().getEdad()));
               prm.put("email",paciente.getUsr().getEmail());
               prm.put("pass",paciente.getUsr().getPassword());
               prm.put("tipocancer", paciente.getTipo());
               prm.put("etapa", paciente.getEtapa());
               prm.put("fotoPerfil", Herramientas.convertirBinImagen(paciente.getUsr().getImgPerfil()));
               prm.put("fotoExpediente", Herramientas.convertirBinImagen(paciente.getImgExpediente()));
               return prm;
           }
       };
       RequestQueue fila = Volley.newRequestQueue(this.context);
       fila.getCache().clear();
       fila.add(peticion);
   }
   public void login(String usr, String pwd, Response.Listener<String> com){
        StringRequest request = new StringRequest(Request.Method.POST, ConstantesURL.R_LOGIN_PACIENTES, com, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("Error al generar el login de pacientes: "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> rpm = new HashMap<>();
                rpm.put("usr", usr);
                rpm.put("pwd",pwd);
                return rpm;
            }
        };
       RequestQueue fila = Volley.newRequestQueue(this.context);
       fila.getCache().clear();
       fila.add(request);
   }
   public void traerInfoPosLoginPacientes(String usr, Response.Listener<String> respuesta){
        StringRequest request = new StringRequest(Request.Method.POST, ConstantesURL.R_TRAER_INFO_POS_LOGIN_PACIENTES, respuesta, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error al traer info pos login pacientes "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> prm = new HashMap<>();
                prm.put("usr", usr);
                return prm;
            }
        };
       RequestQueue fila = Volley.newRequestQueue(this.context);
       fila.getCache().clear();
       fila.add(request);
   }
}
