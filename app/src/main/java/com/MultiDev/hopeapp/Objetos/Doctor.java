package com.MultiDev.hopeapp.Objetos;

public class Doctor {
    private int idDoctor;
    private Usuario usuario;
    private String cedula, especialidad,estudios,historial,status, experiencia;

    public Doctor(Usuario usuario,String especialidad,  String cedula, String estudios, String experiencia) {
        this.usuario = usuario;
        this.especialidad = especialidad;
        this.cedula = cedula;
        this.estudios = estudios;
        this.experiencia = experiencia;
    }

    public Doctor(int idDoctor, Usuario usuario, String cedula, String status) {
        this.idDoctor = idDoctor;
        this.usuario = usuario;
        this.cedula = cedula;
        this.status = status;
    }

    public Doctor(String[] arreglo) {
        this.usuario = new Usuario(Integer.parseInt(arreglo[0]), arreglo[2],arreglo[3], Integer.parseInt(arreglo[4]), arreglo[5]);
        this.idDoctor = Integer.parseInt(arreglo[1]);
        this.cedula = arreglo[6];
        this.status = arreglo[5];
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

    public Doctor(int idDoctor, Usuario usuario, String cedula, String especialidad, String estudios, String historial, String status, String experiencia) {
        this.idDoctor = idDoctor;
        this.usuario = usuario;
        this.cedula = cedula;
        this.especialidad = especialidad;
        this.estudios = estudios;
        this.historial = historial;
        this.status = status;
        this.experiencia = experiencia;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }
}
