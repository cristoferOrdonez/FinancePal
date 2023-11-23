package com.example.financepal.entidades;

public class InfoGasto {
    public String nombre;
    public int monto;
    public int recurrencia;

    public InfoGasto(String nombre, int monto, int recurrencia) {
        this.nombre = nombre;
        this.monto = monto;
        this.recurrencia = recurrencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getRecurrencia() {
        return recurrencia;
    }

    public void setRecurrencia(int recurrencia) {
        this.recurrencia = recurrencia;
    }
}
