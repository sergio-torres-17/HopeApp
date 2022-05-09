package com.MultiDev.hopeapp.Objetos;

public class Sesion {
    private String usuario;
    private boolean esDoctor;
    private String infoUsuario;

    public Sesion(String usuario, boolean esDoctor, String infoUsuario) {
        this.usuario = usuario;
        this.esDoctor = esDoctor;
        this.infoUsuario = infoUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isEsDoctor() {
        return esDoctor;
    }

    public void setEsDoctor(boolean esDoctor) {
        this.esDoctor = esDoctor;
    }

    public String getInfoUsuario() {
        return infoUsuario;
    }

    public void setInfoUsuario(String infoUsuario) {
        this.infoUsuario = infoUsuario;
    }
}
