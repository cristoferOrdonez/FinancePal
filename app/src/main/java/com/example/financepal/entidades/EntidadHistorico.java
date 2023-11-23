package com.example.financepal.entidades;


import android.app.appsearch.StorageInfo;

public class EntidadHistorico {

    private int id;
    private String fechaHistorico;
    private String gastoTotalHistorico;
    private String ingresoTotalHistorico;

    public EntidadHistorico(int id, String ingresoTotalHistorico, String gastoTotalHistorico, String fechaHistorico){

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

    public String getGastoTotalHistorico() {
        return gastoTotalHistorico;
    }

    public void setGastoTotalHistorico(String gastoTotalHistorico) {
        this.gastoTotalHistorico = gastoTotalHistorico;
    }

    public String getIngresoTotalHistorico() {
        return ingresoTotalHistorico;
    }

    public void setIngresoTotalHistorico(String ingresoTotalHistorico) {
        this.ingresoTotalHistorico = ingresoTotalHistorico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}



