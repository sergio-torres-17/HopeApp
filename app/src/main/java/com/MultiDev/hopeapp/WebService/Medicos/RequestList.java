package com.MultiDev.hopeapp.WebService.Medicos;

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


    public void login(String usr, String pwd,Context context ,Response.Listener listener){
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
                Map<String, String> prm = new HashMap<String,String>();
                prm.put("usr", usr);
                prm.put("pwd", pwd);
                return prm;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(peticion);
    }
}
