package com.MultiDev.hopeapp.WebService.Herramientas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
}
