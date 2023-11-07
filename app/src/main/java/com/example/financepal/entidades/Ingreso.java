package com.example.financepal.entidades;

public class Ingreso {

    private String nombre;
    private String monto;
    private long id;

    public Ingreso(long id, String nombre, String monto) {
        this.nombre = nombre;
        this.monto = monto;
        this.id = id;

    }

    public String getNombre() {
        return nombre;
    }

    public String getMonto() {
        return monto;
    }

    public long getId() { return id; }

}
