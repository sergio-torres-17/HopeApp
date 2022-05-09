package com.MultiDev.hopeapp.Objetos;

import android.graphics.Bitmap;

public class Paciente {
    private Usuario usr;
    private String etapa, tipo, tiempoDiagnostico;
    private Bitmap imgExpediente;

    public Paciente(Usuario usr, String etapa, String tipo) {
        this.usr = usr;
        this.etapa = etapa;
        this.tipo = tipo;
    }
    public Paciente(Usuario usr, String etapa, String tipo,String tiempoDiagnostico ,Bitmap imgExpediente) {
        this.usr = usr;
        this.etapa = etapa;
        this.tipo = tipo;
        this.tiempoDiagnostico = tiempoDiagnostico;
        this.imgExpediente = imgExpediente;
    }
    public Paciente(String info[]){
        this.usr = new Usuario(Integer.parseInt(info[0]), info[2], info[3], Integer.parseInt(info[4]), info[5]);
        this.etapa = info[6];
        this.tipo = info[7];
    }

    public Paciente() {
        this.usr = new Usuario();
    }

    public Usuario getUsr() {
        return usr;
    }

    public void setUsr(Usuario usr) {
        this.usr = usr;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Bitmap getImgExpediente() {
        return imgExpediente;
    }

    public void setImgExpediente(Bitmap imgExpediente) {
        this.imgExpediente = imgExpediente;
    }

    public String getTiempoDiagnostico() {
        return tiempoDiagnostico;
    }

    public void setTiempoDiagnostico(String tiempoDiagnostico) {
        this.tiempoDiagnostico = tiempoDiagnostico;
    }

}
