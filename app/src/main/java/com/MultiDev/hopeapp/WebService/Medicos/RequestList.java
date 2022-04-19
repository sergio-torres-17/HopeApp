package com.MultiDev.hopeapp.WebService.Medicos;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.MultiDev.hopeapp.Objetos.Paciente;
import com.MultiDev.hopeapp.WebService.ConstantesURL;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.file.ProviderMismatchException;
import java.util.ArrayList;
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
                prm.put("usr", usr);
                prm.put("pwd", pwd);
                return prm;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(peticion);
    }
    public void registrarMedico(Response.Listener listener){
        StringRequest request = new StringRequest(Request.Method.POST, ConstantesURL.R_REGISTRO_DOCTORES, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
        RequestQueue fila = Volley.newRequestQueue(context);
        fila.add(request);
    }
    public List<Paciente> mostrarPacientes(Response.Listener listener){
        List<Paciente> result = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, ConstantesURL.R_VER_PACIENTES_SIN_TUTELA, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error al solicitar pacientes "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prm = new HashMap<>();
                return prm;
            }
        };
        RequestQueue fila = Volley.newRequestQueue(this.context);
        fila.add(request);
        return result;
    }
}
