package com.example.financepal.entidades;

import android.app.appsearch.StorageInfo;

public class MetasInfo {

    private int id;
    private String nombreMeta;
    private String fechaMeta;
    private Integer montoMeta;

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
}
