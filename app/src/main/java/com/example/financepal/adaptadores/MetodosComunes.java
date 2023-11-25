package com.example.financepal.adaptadores;

public class MetodosComunes {

    private final static String[] meses = {"Ene.", "Feb.", "Mar.", "Abr.", "May.", "Jun.", "Jul.", "Ago.", "Sep.", "Oct.", "Nov.", "Dic."};

    public static String obtenerPrefijoMes(int mes){
        return meses[mes - 1];
    }

}
