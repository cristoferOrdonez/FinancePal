package com.example.financepal.entidades;

public class UsuarioCategoriasGasto {
    private int idcatgasto;
    private String correocatgasto;
    private String nombrecatgasto;
    private String desccatgasto;

    public int getIdcatgasto() {
        return idcatgasto;
    }

    public void setIdcatgasto(int idcatgasto) {
        this.idcatgasto = idcatgasto;
    }

    public String getCorreocatgasto() {
        return correocatgasto;
    }

    public void setCorreocatgasto(String correocatgasto) {
        this.correocatgasto = correocatgasto;
    }

    public String getNombrecatgasto() {
        return nombrecatgasto;
    }

    public void setNombrecatgasto(String nombrecatgasto) {
        this.nombrecatgasto = nombrecatgasto;
    }

    public String getDesccatgasto() {
        return desccatgasto;
    }

    public void setDesccatgasto(String desccatgasto) {
        this.desccatgasto = desccatgasto;
    }

    public String toString(){
        return nombrecatgasto;
    }
}
