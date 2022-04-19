package com.MultiDev.hopeapp.Objetos;

public class Doctor {
    private Usuario usuario;
    private String cedula;

    public Doctor(Usuario usuario, String cedula) {
        this.usuario = usuario;
        this.cedula = cedula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
