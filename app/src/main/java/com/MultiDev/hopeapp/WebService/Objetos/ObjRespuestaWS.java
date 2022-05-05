package com.MultiDev.hopeapp.WebService.Objetos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.MultiDev.hopeapp.WebService.Herramientas.ToolJson;

import org.json.JSONException;
import org.json.JSONObject;

public class ObjRespuestaWS {
    private boolean status;
    private String mensaje;
    private Context context;

    public ObjRespuestaWS(boolean status, String mensaje, Context context) {
        this.status = status;
        this.mensaje = mensaje;
        this.context = context;
    }
    public ObjRespuestaWS(String json, Context context) {
        int resp;
        JSONObject auxJson = new ToolJson().convertStrJSON(json);

        try {
             resp = auxJson.getInt("Rsp");
            this.status = (resp==1);
            this.mensaje = auxJson.getString("Msg");
        } catch (JSONException e) {
            System.err.println("Error al inicializar los tipos desde JSON: "+e.getMessage());
        }
        this.context = context;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void mostrarRespuesta(String title){
        AlertDialog.Builder ad = new AlertDialog.Builder(this.context);
        ad.setTitle(title);
        ad.setMessage(this.mensaje);
        ad.show();
    }
    public void mostrarRespuesta(String title,String btnPositivo){
        AlertDialog.Builder ad = new AlertDialog.Builder(this.context);
        ad.setTitle(title);
        ad.setMessage(this.mensaje);
        ad.setPositiveButton(btnPositivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ad.show();
    }
    public void mostrarRespuesta(String title,String btnPos,String btnNeg){
        AlertDialog.Builder ad = new AlertDialog.Builder(this.context);
        ad.setTitle(title);
        ad.setMessage(this.mensaje);
        ad.setPositiveButton(btnPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ad.setNegativeButton(btnNeg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ad.show();
    }
    public void mostrarRespuesta(String title,String btnPositivo, DialogInterface.OnClickListener btnPosListener){
        AlertDialog.Builder ad = new AlertDialog.Builder(this.context);
        ad.setTitle(title);
        ad.setMessage(this.mensaje);
        ad.setPositiveButton(btnPositivo, btnPosListener);
        ad.show();
    }
    public void mostrarRespuesta(String title,String btnPos,String btnNeg,DialogInterface.OnClickListener btnPosListener,DialogInterface.OnClickListener btnNegListener){
        AlertDialog.Builder ad = new AlertDialog.Builder(this.context);
        ad.setTitle(title);
        ad.setMessage(this.mensaje);
        ad.setPositiveButton(btnPos,btnPosListener);
        ad.setNegativeButton(btnNeg,btnNegListener);
        ad.show();
    }
}
