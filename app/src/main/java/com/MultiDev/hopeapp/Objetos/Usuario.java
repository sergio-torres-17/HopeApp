package com.MultiDev.hopeapp.Objetos;

public class Usuario {
    private String nombre, apellidos;
    private int edad;
    private String email, password;

    public Usuario(String nombre, String apellidos, int edad, String email, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.email = email;
        this.password = password;
    }
    public Usuario(String[] info) {
        this.nombre = info[0];
        this.apellidos = info[1];
        this.edad = Integer.parseInt(info[2]);
        this.email = info[3];
        this.password = info[4];
    }

    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return  "" + nombre +
                "," + apellidos +
                "," + edad +
                "," + email +
                "," + password;
    }
}
