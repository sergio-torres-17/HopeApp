package com.MultiDev.hopeapp.WebService.Herramientas;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.MultiDev.hopeapp.Objetos.Sesion;
import com.MultiDev.hopeapp.R;

public class ControlSesiones {
    private Activity act;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ControlSesiones(Activity act) {
        this.act = act;
        this.preferences = act.getSharedPreferences(act.getString(R.string.preferences_file_key),Context.MODE_PRIVATE);
    }
    public void  guardarSesion(String usuario, boolean esDoctor, String infoFragmentada){
        editor = preferences.edit();
        editor.putString("usr", usuario);
        editor.putBoolean("esDoctor",esDoctor);
        editor.putString("infoUsuario",infoFragmentada);
        editor.commit();
    }
    public void borrarSesion(){
        editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
    public Sesion leerSesion(){
        this.preferences = act.getSharedPreferences(act.getString(R.string.preferences_file_key),Context.MODE_PRIVATE);
        Sesion sesion = new Sesion(preferences.getString("usr", "none"), preferences.getBoolean("esDoctor", false), preferences.getString("infoUsuario", "none"));
        return sesion;
    }
    public boolean haySesion(){
        return (!preferences.getString("usr", "none").equals("none"));
    }

}
