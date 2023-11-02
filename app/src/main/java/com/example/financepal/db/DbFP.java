package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.financepal.entidades.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DbFP extends DbHelperFP {

    Context context;
    public DbFP(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarIngreso(String correoUsuario, String nombreIngreso, String montoIngreso) {
        long id = 0;
        try {
            DbHelperFP dbHelper = new DbHelperFP(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("correoUsuarioIngresos", correoUsuario); // Nuevo campo para almacenar el correo electronico del usuario
            values.put("nombreIngreso", nombreIngreso);
            values.put("montoIngreso", montoIngreso);

            id = db.insert(TABLE_INGRESOS, null, values);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public List<Ingreso> mostrarIngresos(String correoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Ingreso> listaMetas = new ArrayList<>();
        Ingreso ingresoInfo = null;
        Cursor cursorIngresos = null;

        cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE correoUsuarioIngresos = ?", new String[]{correoUsuario});

        NumberFormat col = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        if (cursorIngresos.moveToFirst()) {
            do {
                ingresoInfo = new Ingreso(cursorIngresos.getInt(0), cursorIngresos.getString(2), col.format(Long.parseLong(cursorIngresos.getString(3))) + " COP");

                listaMetas.add(ingresoInfo);
            } while (cursorIngresos.moveToNext());
        }
        cursorIngresos.close();

        db.close();
        return listaMetas;
    }
    public Ingreso verIngreso(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Ingreso IngresoInfo = null;
        Cursor cursorIngresos;

        cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE idIngreso = ? LIMIT 1", new String[]{String.valueOf(id)});

        if (cursorIngresos.moveToFirst()) {
            IngresoInfo = new Ingreso(cursorIngresos.getInt(0), cursorIngresos.getString(2), cursorIngresos.getString(3));
        }
        cursorIngresos.close();

        db.close();
        return IngresoInfo;
    }

    public boolean editarIngreso(long id, String nombreIngreso, String montoIngreso) {
        boolean correcto = false;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombreIngreso", nombreIngreso);
            values.put("montoIngreso", montoIngreso);

            int rowsAffected = db.update(TABLE_INGRESOS, values, "idIngreso = ?", new String[]{String.valueOf(id)});

            if (rowsAffected > 0) {
                correcto = true;
            } else {
                correcto = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean elimnarIngreso(long id) {
        boolean correcto = false;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL( " DELETE FROM "+ TABLE_INGRESOS + " WHERE idIngreso = '" +id+ "' ");
            correcto=true;

        } catch (Exception e) {
            e.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public List obtenerNombresMetas(String correoUsuario){

        SQLiteDatabase db = this.getWritableDatabase();

        List<String> nombres = new ArrayList<String>();

        Cursor cursorIngresos = null;

        cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE correoUsuarioIngresos = ?", new String[]{correoUsuario});


        if (cursorIngresos.moveToFirst()) {
            do {
                nombres.add(cursorIngresos.getString(2));

            } while (cursorIngresos.moveToNext());
        }
        cursorIngresos.close();

        db.close();

        return nombres;

    }

    public long agregarUsuario(String nombresUsuario, String apellidosUsuario, String edadUsuaio, String correoUsuario, String contrasenaUsuario) {
        long id = 0;
        try {
            DbHelperFP dbHelper = new DbHelperFP(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombresUsuario", nombresUsuario); // Nuevo campo para almacenar el correo electronico del usuario
            values.put("apellidosUsuario", apellidosUsuario);
            values.put("edadUsuario", edadUsuaio);
            values.put("correoUsuarioUsuarios", correoUsuario);
            values.put("contrasenaUsuario", contrasenaUsuario);

            id = db.insert(TABLE_USUARIOS, null, values);

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;

    }

    public Usuario verUsuario(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Usuario UsuarioInfo = null;
        Cursor cursorUsuarios;

        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE idUsuario = ? LIMIT 1", new String[]{String.valueOf(id)});

        if (cursorUsuarios.moveToFirst()) {
            UsuarioInfo = new Usuario(cursorUsuarios.getInt(0), cursorUsuarios.getString(2), cursorUsuarios.getString(3), cursorUsuarios.getString(4), cursorUsuarios.getString(5), cursorUsuarios.getString(6));
        }
        cursorUsuarios.close();

        db.close();

        return UsuarioInfo;
    }

    public Usuario verUsuario(String correoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        Usuario UsuarioInfo = null;
        Cursor cursorUsuarios;

        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE correoUsuarioUsuarios = ? LIMIT 1", new String[]{correoUsuario});

        if (cursorUsuarios.moveToFirst()) {
            UsuarioInfo = new Usuario(cursorUsuarios.getInt(0), cursorUsuarios.getString(1), cursorUsuarios.getString(2), cursorUsuarios.getString(3), cursorUsuarios.getString(4), cursorUsuarios.getString(5));
        }
        cursorUsuarios.close();

        db.close();

        return UsuarioInfo;
    }

    public boolean actualizarUsuario(long id, String nombresUsuario, String apellidosUsuario, String edadUsuario, String correoUsuario, String contrasenaUsuario) {
        boolean correcto = false;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombresUsuario", nombresUsuario);
            values.put("apellidosUsuario", apellidosUsuario);
            values.put("edadUsuario", edadUsuario);
            values.put("correoUsuarioUsuarios", correoUsuario);
            values.put("contrasenaUsuario", contrasenaUsuario);

            int rowsAffected = db.update(TABLE_USUARIOS, values, "idUsuario = ?", new String[]{String.valueOf(id)});

            if (rowsAffected > 0) {
                correcto = true;
            } else {
                correcto = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    /*

    public boolean elimnarIngreso(long id) {
        boolean correcto = false;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL( " DELETE FROM "+ TABLE_INGRESOS + " WHERE id = '" +id+ "' ");
            correcto=true;

        } catch (Exception e) {
            e.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

     */

    public List obtenerCorreosElectronicos(){

        SQLiteDatabase db = this.getWritableDatabase();

        List<String> correos = new ArrayList<String>();

        Cursor cursorCorreos = null;

        cursorCorreos = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);


        if (cursorCorreos.moveToFirst()) {
            do {
                correos.add(cursorCorreos.getString(4));

            } while (cursorCorreos.moveToNext());
        }
        cursorCorreos.close();
        db.close();

        return correos;

    }


}
