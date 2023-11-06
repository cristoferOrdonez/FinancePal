package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbGastos extends DbHelperGastos {

    Context context;
    public DbGastos(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public void insertarprimeraCategoria(String correo){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO "+TABLE_CATEGORIAS_GASTO+" (correocatgasto,nombrecatgasto,desccatgasto) VALUES" +
                "('"+correo+"','Vivienda','Gastos relacionados con el hogar')," +
                "('"+correo+"','Transporte','Gastos relacionados con transporte')," +
                "('"+correo+"','Impuestos','Gastos relacionados con el pago de impuestos')," +
                "('"+correo+"','Mercado','Gastos relacionados con el mercado')");
    }
    /*public long insertarCategoria(String correo, String nombre, String descripcion){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id=0;

        try{
            ContentValues values= new ContentValues();
            values.put("correo",correo);
            values.put("nombre",nombre);
            values.put("descripcion", descripcion);

            long id = db.insert(TABLE_CATEGORIAS_GASTO,null,values);
        }
        catch(Exception e){
            ex.toString;
        }


        return id;
    }*/
}
