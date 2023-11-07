package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.financepal.entidades.UsuarioGastos;

import java.util.ArrayList;

public class DbGastos extends DbHelperGastos {

    Context context;
    public DbGastos(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    /*public void insertarprimeraCategoria(String correo){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO "+TABLE_CATEGORIAS_GASTO+" (correocatgasto,nombrecatgasto,desccatgasto) VALUES" +
                "('"+correo+"','Vivienda','Gastos relacionados con el hogar')," +
                "('"+correo+"','Transporte','Gastos relacionados con transporte')," +
                "('"+correo+"','Impuestos','Gastos relacionados con el pago de impuestos')," +
                "('"+correo+"','Mercado','Gastos relacionados con el mercado')");
    }*/


    public long insertarNuevaCategoria(String correo, String nombre, String descripcion){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id=0;

        try{
            ContentValues values= new ContentValues();
            values.put("correocatgasto",correo);
            values.put("nombrecatgasto",nombre);
            values.put("desccatgasto", descripcion);

            id = db.insert(TABLE_CATEGORIAS_GASTO,null,values);
        }
        catch(Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT);
        }


        return id;
    }

    public Cursor mostrarCategoriasGasto(String idcorreo){
        try{
            DbHelperGastos dbHelper = new DbHelperGastos(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor datos = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIAS_GASTO+" WHERE correocatgasto = '0000' OR correocatgasto ='"+idcorreo+"'",null);
            if(datos.moveToFirst()){
                return datos;
            }
            else{
                return null;
            }
        }
        catch(Exception e){
            return null;
        }
    }

    public Cursor mostrarPrioridadGastos(){
        try{
            DbHelperGastos dbHelper = new DbHelperGastos(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor datos = db.rawQuery("SELECT * FROM " + TABLE_PRIORIDAD,null);
            if(datos.moveToFirst()){
                return datos;
            }
            else{
                return null;
            }
        }
        catch(Exception e){
            return null;
        }
    }

    public ArrayList<UsuarioGastos> buscarUsuario(String idcorreo){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_GASTOS+" WHERE correogasto='"+idcorreo+"'",null);
        ArrayList<UsuarioGastos> lista = new ArrayList<UsuarioGastos>();
        UsuarioGastos usuario = null;
        if(datos!=null){
            if(datos.moveToFirst()){
                do{
                    usuario = new UsuarioGastos();
                    usuario.setCorreogasto(datos.getString(1));
                    usuario.setNombregasto(datos.getString(2));
                    usuario.setIdcatgasto(datos.getInt(3));
                    usuario.setIdprioridad(datos.getInt(4));
                    usuario.setMontogasto(datos.getInt(5));
                    usuario.setRecurrenciagasto(datos.getInt(6));
                    lista.add(usuario);
                }while(datos.moveToNext());
            }
        }
        db.close();
        return lista;
    }

    public long insertarGasto(UsuarioGastos g){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id=0;
        try{
            values.put("correogasto",g.getCorreogasto());
            values.put("nombregasto",g.getNombregasto());
            values.put("idcatgasto1",g.getIdcatgasto());
            values.put("idprioridad1",g.getIdprioridad());
            values.put("montogasto",g.getMontogasto());
            values.put("recurrenciagasto",g.getRecurrenciagasto());
            id=db.insert(TABLE_GASTOS,null,values);
        } catch(Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT);
        }
        return id;
    }

    public String mostrarNombreCategoria(int idcatgasto, String correocatgasto){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT nombrecatgasto FROM "+ TABLE_CATEGORIAS_GASTO+" WHERE idcatgasto='"+idcatgasto+"' AND correocatgasto='"+correocatgasto+"'",null);
        return datos.getString(0);
    }



}
