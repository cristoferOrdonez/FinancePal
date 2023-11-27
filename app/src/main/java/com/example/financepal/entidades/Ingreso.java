package com.example.financepal.entidades;

public class Ingreso {

    private String nombre;
    private String monto;
    private String fecha;
    private long id;

    public Ingreso(long id, String nombre, String monto, String fecha) {
        this.nombre = nombre;
        this.monto = monto;
        this.id = id;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMonto() {
        return monto;
    }

    public long getId() { return id; }

    public String getFecha() { return fecha; }

}
