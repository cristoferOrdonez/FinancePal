package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.financepal.entidades.UsuarioCategoriasGasto;
import com.example.financepal.entidades.UsuarioGastos;

import java.util.ArrayList;
import java.util.List;

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


    public boolean insertarNuevaCategoria(UsuarioCategoriasGasto u){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id=0;
        boolean correcto;

        try{
            ContentValues values= new ContentValues();
            values.put("correocatgasto",u.getCorreocatgasto());
            values.put("nombrecatgasto",u.getNombrecatgasto());
            values.put("desccatgasto", u.getDesccatgasto());

            id = db.insert(TABLE_CATEGORIAS_GASTO,null,values);

            correcto= id!=-1;
        }
        catch(Exception e){
            e.printStackTrace();
            correcto = false;
        }
        finally {
            db.close();
        }


        return correcto;
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

    public List<UsuarioGastos> buscarUsuario(String idcorreo){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_GASTOS+" WHERE correogasto='"+idcorreo+"'",null);
        List<UsuarioGastos> lista = new ArrayList<>();
        UsuarioGastos usuario = null;
        try{
            if(datos!=null){
                if(datos.moveToFirst()){
                    do{
                        usuario = new UsuarioGastos();
                        usuario.setIdgastos(datos.getInt(0));
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
        }catch(Exception e){
            e.toString();
        }

        db.close();
        return lista;
    }

    public List<UsuarioCategoriasGasto> buscarCategGastos(String idcorreo){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_CATEGORIAS_GASTO+" WHERE correocatgasto='"+idcorreo+"' OR correocatgasto='0000'",null);
        List<UsuarioCategoriasGasto> lista = new ArrayList<>();
        UsuarioCategoriasGasto usuario = null;
        try{
            if(datos!=null){
                if(datos.moveToFirst()){
                    do{
                        usuario = new UsuarioCategoriasGasto();
                        usuario.setIdcatgasto(datos.getInt(0));
                        usuario.setCorreocatgasto(idcorreo);
                        usuario.setNombrecatgasto(datos.getString(2));
                        usuario.setDesccatgasto(datos.getString(3));
                        lista.add(usuario);
                    }while(datos.moveToNext());
                }
            }
        }catch(Exception e){
            e.toString();
        }

        db.close();
        return lista;
    }

    public boolean editarGasto(UsuarioGastos g){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean correcto;

        try {
            ContentValues values = new ContentValues();
            values.put("correogasto",g.getCorreogasto());
            values.put("nombregasto",g.getNombregasto());
            values.put("idcatgasto1",g.getIdcatgasto());
            values.put("idprioridad1",g.getIdprioridad());
            values.put("montogasto",g.getMontogasto());
            values.put("recurrenciagasto",g.getRecurrenciagasto());

            int rowsAffected = db.update(TABLE_GASTOS, values, "idgastos = ?", new String[]{String.valueOf(g.getIdgastos())});

            correcto = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public UsuarioGastos buscarGasto(int id){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_GASTOS+" WHERE idgastos="+id,null);
        UsuarioGastos usuario = null;
        try{
            if(datos!=null){
                if(datos.moveToFirst()){
                    usuario = new UsuarioGastos();
                    usuario.setIdgastos(datos.getInt(0));
                    usuario.setCorreogasto(datos.getString(1));
                    usuario.setNombregasto(datos.getString(2));
                    usuario.setIdcatgasto(datos.getInt(3));
                    usuario.setIdprioridad(datos.getInt(4));
                    usuario.setMontogasto(datos.getInt(5));
                    usuario.setRecurrenciagasto(datos.getInt(6));
                }
            }
        }catch(Exception e){
            e.toString();
        }

        db.close();
        return usuario;
    }

    public boolean eliminarGasto(int id){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean correcto;
        try {
            db.execSQL( " DELETE FROM "+ TABLE_GASTOS + " WHERE idgastos = '" +id+ "' ");
            correcto=true;

        } catch (Exception e) {
            e.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
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
        db.close();
        return id;
    }

    public String mostrarNombreCategoria(UsuarioGastos g){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_CATEGORIAS_GASTO+" WHERE idcatgasto="+g.getIdcatgasto(),null);
        datos.moveToFirst();
        String nombre = datos.getString(2);
        db.close();
        return nombre;
    }


    public String mostrarNombrePrioridad(UsuarioGastos g){
        DbHelperGastos dbHelper = new DbHelperGastos(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_PRIORIDAD+" WHERE idprioridad='"+g.getIdprioridad()+"'",null);
        datos.moveToFirst();
        String nombre = datos.getString(1);
        db.close();
        return nombre;
    }


}
