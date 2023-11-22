package com.example.financepal.entidades;


import android.app.appsearch.StorageInfo;

public class EntidadHistorico {

    private int id;
    private int fechaMesHistorico;
    private int fechaAnoHistorico;
    private int gastoTotalHistorico;

    public int getFechaAnoHistorico() {
        return fechaAnoHistorico;
    }

    public void setFechaAnoHistorico(int fechaAnoHistorico) {
        this.fechaAnoHistorico = fechaAnoHistorico;
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

    private int ingresoTotalHistorico;

    public int getFechaMesHistorico() {
        return fechaMesHistorico;
    }

    public void setFechaMesHistorico(int fechaMesHistorico) {
        this.fechaMesHistorico = fechaMesHistorico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}



