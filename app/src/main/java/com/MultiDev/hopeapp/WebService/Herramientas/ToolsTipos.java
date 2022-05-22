package com.MultiDev.hopeapp.WebService.Herramientas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ToolsTipos <T>{
    public ArrayList<T> listarElementosPorPeticion(String json, String[] etiquetas){
        ArrayList<T> dev = new ArrayList<>();
        JSONArray arr;
        JSONObject obj;
        try {
            arr = new JSONArray(json);
            for (int in = 0; in<arr.length();in++){
                obj = arr.getJSONObject(in);
                for (int i = 0; i<etiquetas.length;i++){
                    dev.add((T)obj.getString(etiquetas[i]));
                }
            }

        } catch (JSONException e) {
            System.err.println("Error al convertir json a lista "+e.getMessage());
        }
        return dev;
    }
}
