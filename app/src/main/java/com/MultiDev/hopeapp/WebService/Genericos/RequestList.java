package com.MultiDev.hopeapp.WebService.Genericos;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

    public void validarExistenciaDeUsuario(String nombre, String apellidos, String correo, Response.Listener<String> formaPeticion){
        StringRequest peticion = new StringRequest(Request.Method.POST, ConstantesURL.R_VALIDAR_EXISTENCIA_USUARIO, formaPeticion, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Ocurrió un error al validar la existencia del usuario: "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prm = new HashMap<>();
                System.out.println("Datos a mandar:\nNombre: "+nombre+"\nApellidos: "+apellidos+"\nCorreo: "+correo);
                prm.put("nombre", nombre);
                prm.put("apellidos", apellidos);
                prm.put("email", correo);
                return prm;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(peticion);
    }
    public void verTipoDeUsuario(String usr,Response.Listener<String> escuchador){
        StringRequest peticion = new StringRequest(Request.Method.POST, ConstantesURL.R_VER_TIPOS_USUARIOS, escuchador, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("Error info pos login "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prm = new HashMap<>();
                prm.put("usr",usr);
                return prm;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.getCache().clear();
        queue.add(peticion);
    }
    public void traerInfoPosLogin(String correo,Response.Listener<String> frm){
        StringRequest request = new StringRequest(Request.Method.POST, ConstantesURL.R_TRAER_INFO_POS_LOGIN, frm, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("Error info pos login "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> prm = new HashMap<>();
                prm.put("email",correo);
                return prm;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(request);
    }
}
