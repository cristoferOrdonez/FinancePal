package com.example.financepal.entidades;

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

    public long getGastoTotalHistorico() {
        return gastoTotalHistorico;
    }

    public long getIngresoTotalHistorico() {
        return ingresoTotalHistorico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}



