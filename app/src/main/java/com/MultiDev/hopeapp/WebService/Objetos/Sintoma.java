package com.MultiDev.hopeapp.WebService.Objetos;

import java.util.Date;

public class Sintoma {
    private int idPersona;
    private String sintoma, hora, intensidad, detalles;
    private String fecha;

    public Sintoma(int idPersona, String sintoma, String hora, String intensidad, String detalles, String fecha) {
        this.idPersona = idPersona;
        this.sintoma = sintoma;
        this.hora = hora;
        this.intensidad = intensidad;
        this.detalles = detalles;
        this.fecha = fecha;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getSintoma() {
        return sintoma;
    }

    public void setSintoma(String sintoma) {
        this.sintoma = sintoma;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
