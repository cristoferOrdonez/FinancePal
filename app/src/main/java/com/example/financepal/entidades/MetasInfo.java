package com.example.financepal.entidades;

import android.app.appsearch.StorageInfo;

public class MetasInfo {

    private int id;
    private String nombreMeta;
    private String fechaMeta;
    private Integer montoMeta;

    private double montoMensual;

    private String montoMensualFormateado;
    private String montoTotalFormateado;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMeta() {
        return nombreMeta;
    }

    public void setNombreMeta(String nombreMeta) {
        this.nombreMeta = nombreMeta;
    }

    public String getFechaMeta() {
        return fechaMeta;
    }

    public void setFechaMeta(String fechaMeta) {
        this.fechaMeta = fechaMeta;
    }

    public Integer getMontoMeta() {
        return montoMeta;
    }

    public void setMontoMeta(Integer montoMeta) {
        this.montoMeta = montoMeta;
    }
    public void setMontoMensual(double montoMensual){this.montoMensual= montoMensual;}
    public double getMontoMensual(){return montoMensual;}

    public void setMontoMensualFormateado(String montoMensualFormateado){ this.montoMensualFormateado=montoMensualFormateado;}

    public String getMontoMensualFormateado() {return montoMensualFormateado;}

    public void setMontoTotalFormateado(String montoMetaFormateado){this.montoTotalFormateado=montoMetaFormateado;}

    public String getMontoTotalFormateado(){return  montoTotalFormateado;}
}
