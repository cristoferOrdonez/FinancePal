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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class DbGastos extends DbHelperFP {

    Context context;
    public DbGastos(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public void insertarprimeraCategoria(String correo){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO "+TABLE_CATEGORIAS_GASTO+" (correocatgasto,nombrecatgasto,desccatgasto) VALUES" +
                "('"+correo+"','Vivienda','Gastos relacionados con el hogar')," +
                "('"+correo+"','Transporte','Gastos relacionados con transporte')," +
                "('"+correo+"','Impuestos','Gastos relacionados con el pago de impuestos')," +
                "('"+correo+"','Mercado','Gastos relacionados con el mercado')");
    }


    public boolean insertarNuevaCategoria(UsuarioCategoriasGasto u){
        DbHelperFP dbHelper = new DbHelperFP(context);
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
            DbHelperFP dbHelper = new DbHelperFP(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor datos = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIAS_GASTO+" WHERE correocatgasto ='"+idcorreo+"'",null);
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
            DbHelperFP dbHelper = new DbHelperFP(context);
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
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_GASTOS+" WHERE correogasto='"+idcorreo+"' ORDER BY idgastos DESC",null);
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
                        usuario.setFechamesgasto(datos.getString(7));
                        usuario.setFechaanogasto(datos.getString(8));
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
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_CATEGORIAS_GASTO+" WHERE (correocatgasto='"+idcorreo+"')",null);
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

    public HashMap<Integer,String> verificarRepetidosCategGasto(String idcorreo){
        List<UsuarioCategoriasGasto> lista =buscarCategGastos(idcorreo);
        HashMap<Integer,String> nombres= new HashMap<>();
        for ( UsuarioCategoriasGasto i:lista){
            nombres.put(i.getIdcatgasto(),i.getNombrecatgasto());

        }
        return nombres;
    }

    public UsuarioCategoriasGasto buscarCategGasto(int id){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_CATEGORIAS_GASTO+" WHERE idcatgasto="+id,null);
        UsuarioCategoriasGasto usuario = null;
        try{
            if(datos!=null){
                if(datos.moveToFirst()){
                    do{
                        usuario = new UsuarioCategoriasGasto();
                        usuario.setIdcatgasto(datos.getInt(0));
                        usuario.setCorreocatgasto(datos.getString(1));
                        usuario.setNombrecatgasto(datos.getString(2));
                        usuario.setDesccatgasto(datos.getString(3));
                    }while(datos.moveToNext());
                }
            }
        }catch(Exception e){
            e.toString();
        }

        db.close();
        return usuario;
    }

    public boolean editarCatGasto(UsuarioCategoriasGasto g){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean correcto;

        try {
            ContentValues values = new ContentValues();
            values.put("nombrecatgasto",g.getNombrecatgasto());
            values.put("desccatgasto",g.getDesccatgasto());

            int rowsAffected = db.update(TABLE_CATEGORIAS_GASTO, values, "idcatgasto = ?", new String[]{String.valueOf(g.getIdcatgasto())});

            correcto = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean editarGasto(UsuarioGastos g){
        DbHelperFP dbHelper = new DbHelperFP(context);
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
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_GASTOS+" WHERE idgastos="+id+" ORDER BY idgastos DESC",null);
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
                    usuario.setFechamesgasto(datos.getString(7));
                    usuario.setFechaanogasto(datos.getString(8));
                }
            }
        }catch(Exception e){
            e.toString();
        }

        db.close();
        return usuario;
    }

    public boolean eliminarGasto(int id){
        DbHelperFP dbHelper = new DbHelperFP(context);
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

    public boolean eliminarCatGasto(int id){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean correcto;
        try {
            db.execSQL( "DELETE FROM "+TABLE_GASTOS+" WHERE idcatgasto1="+id);
            db.execSQL( " DELETE FROM "+ TABLE_CATEGORIAS_GASTO + " WHERE idcatgasto="+id);
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
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        GregorianCalendar calendario = new GregorianCalendar();
        long id=0;
        try{
            values.put("correogasto",g.getCorreogasto());
            values.put("nombregasto",g.getNombregasto());
            values.put("idcatgasto1",g.getIdcatgasto());
            values.put("idprioridad1",g.getIdprioridad());
            values.put("montogasto",g.getMontogasto());
            values.put("recurrenciagasto",g.getRecurrenciagasto());
            values.put("fechamesgasto", calendario.get(Calendar.MONTH) + 1);
            values.put("fechaanogasto", calendario.get(Calendar.YEAR));
            id=db.insert(TABLE_GASTOS,null,values);
        } catch(Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT);
        }
        db.close();
        return id;
    }

    public String mostrarNombreCategoria(UsuarioGastos g){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_CATEGORIAS_GASTO+" WHERE idcatgasto="+g.getIdcatgasto(),null);
        datos.moveToFirst();
        String nombre = datos.getString(2);
        db.close();
        return nombre;
    }


    public String mostrarNombrePrioridad(UsuarioGastos g){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_PRIORIDAD+" WHERE idprioridad='"+g.getIdprioridad()+"'",null);
        datos.moveToFirst();
        String nombre = datos.getString(1);
        db.close();
        return nombre;
    }

    public long mostrarGastosTotales(String correo){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_GASTOS+" WHERE correogasto='"+correo+"' ORDER BY montogasto DESC",null);
        long total=0;
        if (datos.moveToFirst()) {
            do {
                total += datos.getInt(5);

            } while (datos.moveToNext());
        }
        return total;

    }

    public UsuarioGastos gastoMasAlto(String correo){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_GASTOS+" WHERE correogasto='"+correo+"'",null);
        UsuarioGastos usuario = null;
        if(datos.moveToFirst()){
            usuario = new UsuarioGastos();
            usuario.setIdgastos(datos.getInt(0));
            usuario.setCorreogasto(datos.getString(1));
            usuario.setNombregasto(datos.getString(2));
            usuario.setIdcatgasto(datos.getInt(3));
            usuario.setIdprioridad(datos.getInt(4));
            usuario.setMontogasto(datos.getInt(5));
            usuario.setRecurrenciagasto(datos.getInt(6));
            usuario.setFechamesgasto(datos.getString(7));
            usuario.setFechaanogasto(datos.getString(8));
        }
        return usuario;
    }

    public UsuarioGastos gastoMasRecurrente(String correo){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM "+ TABLE_GASTOS+" WHERE correogasto='"+correo+"' ORDER BY recurrenciagasto DESC",null);
        UsuarioGastos usuario = null;
        if(datos.moveToFirst()){
            usuario = new UsuarioGastos();
            usuario.setIdgastos(datos.getInt(0));
            usuario.setCorreogasto(datos.getString(1));
            usuario.setNombregasto(datos.getString(2));
            usuario.setIdcatgasto(datos.getInt(3));
            usuario.setIdprioridad(datos.getInt(4));
            usuario.setMontogasto(datos.getInt(5));
            usuario.setRecurrenciagasto(datos.getInt(6));
            usuario.setFechamesgasto(datos.getString(7));
            usuario.setFechaanogasto(datos.getString(8));
        }
        return usuario;
    }

    public UsuarioGastos gastoMasAltoPrioridades(String correo){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery("SELECT * FROM (SELECT * FROM "+ TABLE_GASTOS+" WHERE (correogasto='"+correo+"' AND idprioridad1=3) ORDER BY montogasto DESC LIMIT 1)",null);
        UsuarioGastos usuario = null;
        if(datos.moveToFirst()){
            usuario = new UsuarioGastos();
            usuario.setIdgastos(datos.getInt(0));
            usuario.setCorreogasto(datos.getString(1));
            usuario.setNombregasto(datos.getString(2));
            usuario.setIdcatgasto(datos.getInt(3));
            usuario.setIdprioridad(datos.getInt(4));
            usuario.setMontogasto(datos.getInt(5));
            usuario.setRecurrenciagasto(datos.getInt(6));
            usuario.setFechamesgasto(datos.getString(7));
            usuario.setFechaanogasto(datos.getString(8));
        }
        return usuario;
    }

    public UsuarioGastos gastoMasPrioridades(String correo){
        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor datos = db.rawQuery(" SELECT * FROM "+ TABLE_GASTOS+" WHERE (correogasto='"+correo+"' AND idprioridad1=3 ) ORDER BY montogasto DESC",null);
        UsuarioGastos usuario = null;
        if(datos.moveToFirst()){
            usuario = new UsuarioGastos();
            usuario.setIdgastos(datos.getInt(0));
            usuario.setCorreogasto(datos.getString(1));
            usuario.setNombregasto(datos.getString(2));
            usuario.setIdcatgasto(datos.getInt(3));
            usuario.setIdprioridad(datos.getInt(4));
            usuario.setMontogasto(datos.getInt(5));
            usuario.setRecurrenciagasto(datos.getInt(6));
            usuario.setFechamesgasto(datos.getString(7));
            usuario.setFechaanogasto(datos.getString(8));
        }
        return usuario;
    }

}