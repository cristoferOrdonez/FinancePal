package com.example.financepal.entidades;

public class UsuarioPrioridadesGasto {
    private int idprioridad;
    private String nombreprioridad;

    public int getIdprioridad() {
        return idprioridad;
    }

    public void setIdprioridad(int idprioridad) {
        this.idprioridad = idprioridad;
    }

    public String getNombreprioridad() {
        return nombreprioridad;
    }

    public void setNombreprioridad(String nombreprioridad) {
        this.nombreprioridad = nombreprioridad;
    }

    public String toString(){
        return nombreprioridad;
    }
}
