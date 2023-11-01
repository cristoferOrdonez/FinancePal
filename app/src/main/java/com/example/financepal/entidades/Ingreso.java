package com.example.financepal.entidades;

public class Ingreso {

    public String nombre;
    public String monto;
    public long id;

    public Ingreso(long id, String nombre, String monto) {
        this.nombre = nombre;
        this.monto = monto;
        this.id = id;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
