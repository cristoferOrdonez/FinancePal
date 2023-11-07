package com.example.financepal.entidades;

public class UsuarioGastos {

    private String correogasto, nombregasto;
    private int idcatgasto, idprioridad, montogasto, recurrenciagasto;

    public UsuarioGastos(String correogasto, String nombregasto, int idcatgasto, int idprioridad, int montogasto, int recurrenciagasto) {
        this.correogasto = correogasto;
        this.nombregasto = nombregasto;
        this.idcatgasto = idcatgasto;
        this.idprioridad = idprioridad;
        this.montogasto = montogasto;
        this.recurrenciagasto = recurrenciagasto;
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
}
