package com.example.financepal.entidades;

public class UsuarioGastos {

    private String correogasto, nombregasto, fechamesgasto, fechaanogasto;
    private int idcatgasto;
    private int idprioridad;
    private int montogasto;
    private int recurrenciagasto;

    private int idgastos;

    public UsuarioGastos(String correogasto, String nombregasto, String fechamesgasto, String fechaanogasto, int idcatgasto, int idprioridad, int montogasto, int recurrenciagasto, int idgastos) {
        this.correogasto = correogasto;
        this.nombregasto = nombregasto;
        this.fechamesgasto = fechamesgasto;
        this.fechaanogasto = fechaanogasto;
        this.idcatgasto = idcatgasto;
        this.idprioridad = idprioridad;
        this.montogasto = montogasto;
        this.recurrenciagasto = recurrenciagasto;
        this.idgastos = idgastos;
    }

    public int getIdgastos() {
        return idgastos;
    }

    public void setIdgastos(int idgastos) {
        this.idgastos = idgastos;
    }

    public UsuarioGastos(){

    }

    public String getCorreogasto() {
        return correogasto;
    }

    public void setCorreogasto(String correogasto) {
        this.correogasto = correogasto;
    }

    public String getNombregasto() {
        return nombregasto;
    }

    public void setNombregasto(String nombregasto) {
        this.nombregasto = nombregasto;
    }

    public int getIdcatgasto() {
        return idcatgasto;
    }

    public void setIdcatgasto(int idcatgasto) {
        this.idcatgasto = idcatgasto;
    }

    public int getIdprioridad() {
        return idprioridad;
    }

    public void setIdprioridad(int idprioridad) {
        this.idprioridad = idprioridad;
    }

    public int getMontogasto() {
        return montogasto;
    }

    public void setMontogasto(int montogasto) {
        this.montogasto = montogasto;
    }

    public int getRecurrenciagasto() {
        return recurrenciagasto;
    }

    public void setRecurrenciagasto(int recurrenciagasto) {
        this.recurrenciagasto = recurrenciagasto;
    }

    public String getFechamesgasto() {
        return fechamesgasto;
    }

    public void setFechamesgasto(String fechamesgasto) {
        this.fechamesgasto = fechamesgasto;
    }

    public String getFechaanogasto() {
        return fechaanogasto;
    }

    public void setFechaanogasto(String fechaanogasto) {
        this.fechaanogasto = fechaanogasto;
    }
}
