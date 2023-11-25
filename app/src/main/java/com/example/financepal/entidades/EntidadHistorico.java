package com.example.financepal.entidades;


import android.app.appsearch.StorageInfo;

public class EntidadHistorico {

    private int id;
    private String fechaHistorico;
    private int gastoTotalHistorico;
    private int ingresoTotalHistorico;

    public EntidadHistorico(int id, int ingresoTotalHistorico, int gastoTotalHistorico, String fechaHistorico){

        this.id = id;
        this.ingresoTotalHistorico = ingresoTotalHistorico;
        this.gastoTotalHistorico = gastoTotalHistorico;
        this.fechaHistorico = fechaHistorico;

    }

    public String getFechaHistorico() {
        return fechaHistorico;
    }

    public void setFechaHistorico(String fechaAnoHistorico) {
        this.fechaHistorico = fechaAnoHistorico;
    }

    public int getGastoTotalHistorico() {
        return gastoTotalHistorico;
    }

    public void setGastoTotalHistorico(int gastoTotalHistorico) {
        this.gastoTotalHistorico = gastoTotalHistorico;
    }

    public int getIngresoTotalHistorico() {
        return ingresoTotalHistorico;
    }

    public void setIngresoTotalHistorico(int ingresoTotalHistorico) {
        this.ingresoTotalHistorico = ingresoTotalHistorico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}



