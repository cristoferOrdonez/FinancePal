package com.example.financepal.entidades;

public class Usuario {

    private long id;
    private String nombres;
    private String apellidos;
    private int edad;
    private String correoElectronico;
    private String contrasena;

    public Usuario(long id, String nombres, String apellidos, int edad, String correoElectronico, String contrasena) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getEdad() {
        return edad;
    }//

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

}