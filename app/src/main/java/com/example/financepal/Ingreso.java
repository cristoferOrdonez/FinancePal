package com.example.financepal;

public class Ingreso {

    public String nombre;
    public String monto;
    public int id;

    public Ingreso(int id, String nombre, String monto) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
