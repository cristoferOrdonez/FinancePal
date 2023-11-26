package com.example.financepal.entidades;

public class UsuarioGastos {

    private String correogasto, nombregasto, fechamesgasto, fechaanogasto;
    private int idcatgasto;
    private int idprioridad;
    private long montogasto;
    private long recurrenciagasto;

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

    public UsuarioGastos(){

    }

    public int getIdgastos() {
        return idgastos;
    }

    public void setIdgastos(int idgastos) {
        this.idgastos = idgastos;
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

    public long getMontogasto() {
        return montogasto;
    }

    public void setMontogasto(long montogasto) {
        this.montogasto = montogasto;
    }

    public long getRecurrenciagasto() {
        return recurrenciagasto;
    }

    public void setRecurrenciagasto(long recurrenciagasto) {
        this.recurrenciagasto = recurrenciagasto;
    }
}
