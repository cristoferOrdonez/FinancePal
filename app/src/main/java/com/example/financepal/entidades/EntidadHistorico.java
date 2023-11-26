package com.example.financepal.entidades;


import android.app.appsearch.StorageInfo;

public class EntidadHistorico {

    private int id;
    private String fechaHistorico;
    private long gastoTotalHistorico;
    private long ingresoTotalHistorico;

    public EntidadHistorico(int id, long ingresoTotalHistorico, long gastoTotalHistorico, String fechaHistorico){

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

    public long getGastoTotalHistorico() {
        return gastoTotalHistorico;
    }

    public void setGastoTotalHistorico(long gastoTotalHistorico) {
        this.gastoTotalHistorico = gastoTotalHistorico;
    }

    public long getIngresoTotalHistorico() {
        return ingresoTotalHistorico;
    }

    public void setIngresoTotalHistorico(long ingresoTotalHistorico) {
        this.ingresoTotalHistorico = ingresoTotalHistorico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}



