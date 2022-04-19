package com.MultiDev.hopeapp.Objetos;

public class Paciente {
    private Usuario usr;
    private String etapa, tipo;

    public Paciente(Usuario usr, String etapa, String tipo) {
        this.usr = usr;
        this.etapa = etapa;
        this.tipo = tipo;
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
}
