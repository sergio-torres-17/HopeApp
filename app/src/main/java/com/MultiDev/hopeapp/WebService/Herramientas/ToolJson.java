package com.MultiDev.hopeapp.WebService.Herramientas;

import com.MultiDev.hopeapp.Objetos.Paciente;
import com.MultiDev.hopeapp.Objetos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ToolJson{
    public JSONObject convertStrJSON(String json){
        JSONObject dev = null;
        try {
            JSONArray array = new JSONArray(json);
            dev = array.getJSONObject(0);
        } catch (JSONException e) {
            System.err.println("Error al parsear JSON: "+e.getMessage());
        }
        return dev;
    }
    public  static JSONArray convertStrJSONArray(String json){
        JSONArray dev = null;
        try {
             dev = new JSONArray(json);
        } catch (JSONException e) {
            System.err.println("Error al parsear JSON: "+e.getMessage());
        }
        return dev;
    }
    public static List<Paciente> convertirJsonListaPacientes(String json){
        List<Paciente> dev = new ArrayList<>();
        JSONArray arr = ToolJson.convertStrJSONArray(json);
        JSONObject aux;
        Paciente objAux;
        for (int i = 0; i<arr.length();i++){
            try {
                aux = arr.getJSONObject(i);
                objAux = new Paciente(new Usuario(aux.getString("nombre"),aux.getString("Apellidos"),aux.getInt("edad")), aux.getString("etapa"),aux.getString("tipo"));
                dev.add(objAux);
            } catch (JSONException e) {
                System.err.println("Error al parsear el json el arreglo: "+e.getMessage());
            }
        }
        return dev;
    }
    public static JSONObject extraerSolUnElemento(String json){
        JSONObject dev = null;
        try {
            JSONArray aux = new JSONArray(json);
            dev = aux.getJSONObject(0);
        } catch (JSONException e) {
            System.err.println("Error al extrar un sólo elemento: "+e.getMessage());
        }
        return dev;
    }
    public String infoFragmentada(String json, String[] etiquetas){
        String dev = "";
        JSONObject auxObj = null;
        try{
            auxObj = new JSONArray(json).getJSONObject(0);
            for (int i = 0;i<etiquetas.length; i++){
                if(i == etiquetas.length-1){
                    dev += auxObj.getString(etiquetas[i]) ;
                }else{
                    dev += auxObj.getString(etiquetas[i])+",";
                }
            }
        }catch(Exception e){
            System.err.println("Error al fragmentar la informacion "+e.getMessage());
        }
        return dev;
    }
    public String[] listarTiposCancer(String json){
        String[] dev = null;
        JSONArray arr;
        JSONObject obj;
        try {
            arr = new JSONArray(json);
            dev = new String[arr.length()];
            for (int in = 0; in<arr.length();in++){
                obj = arr.getJSONObject(in);
                dev[in] = (obj.getString("tipo"));
            }
        } catch (JSONException e) {
            System.err.println("Error al convertir json a lista tipos "+e.getMessage());
        }
        return dev;
    }
    public String[] listarEtapasCancer(String json){
        String[] dev = null;
        JSONArray arr;
        JSONObject obj;
        try {
            arr = new JSONArray(json);
            dev = new String[arr.length()];
            for (int in = 0; in<arr.length();in++){
                obj = arr.getJSONObject(in);
                dev[in] = (obj.getString("etapa"));
            }
        } catch (JSONException e) {
            System.err.println("Error al convertir json a lista etapas: "+e.getMessage());
        }
        return dev;
    }
    public String[] listarSintomas(String json){
        String[] dev = null;
        JSONArray arr;
        JSONObject obj;
        try {
            arr = new JSONArray(json);
            dev = new String[arr.length()];
            for (int i = 0; i<arr.length();i++){
                obj = arr.getJSONObject(i);
                dev[i] =obj.getString("descripcion");
            }
        }catch (JSONException e){
            System.err.println("Error al convertir json a lista SINTOMAS: "+e.getMessage());
        }
        return dev;
    }
    public String[] listarIntensidadSintomas(String json){
        String[] dev = null;
        JSONArray arr;
        JSONObject obj;
        try {
            arr = new JSONArray(json);
            dev = new String[arr.length()];
            for (int i = 0; i<arr.length();i++){
                obj = arr.getJSONObject(i);
                dev[i] =obj.getString("descripcion");
            }
        }catch (JSONException e){
            System.err.println("Error al convertir json a lista SINTOMAS: "+e.getMessage());
        }
        return dev;
    }
    public String verNombreDoctorACargo(String json){
        String dev = null;
        try{
            dev = new JSONArray(json).getJSONObject(0).getString("nombre_doctor");
        }catch(Exception e){
            System.err.println("Error al parsear el nombre del doctor a cargo "+e.getMessage() );
        }
        return dev;
    }
    public String[] parsearInfoPacienteDetallada(String[] etiquetas, String json){
        String[] dev = null;
        try {
            JSONObject obj = new JSONArray(json).getJSONObject(0);
            dev = new String[etiquetas.length];
            for (int i = 0;i<etiquetas.length;i++)
                dev[i] = obj.getString(etiquetas[i]);
        } catch (JSONException e) {
            System.err.println("Error al parsear info detallada del paciente  "+e.getMessage());
        }

        return dev;
    }
}
