package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.financepal.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DbUsuarios extends DbHelperFP {

    Context context;
    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long agregarUsuario(String nombresUsuario, String apellidosUsuario, int edadUsuaio, String correoUsuario, String contrasenaUsuario) {
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

    public Usuario verUsuario(String correoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        Usuario UsuarioInfo = null;
        Cursor cursorUsuarios;

        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE correoUsuarioUsuarios = ? LIMIT 1", new String[]{correoUsuario});

        if (cursorUsuarios.moveToFirst()) {
            UsuarioInfo = new Usuario(cursorUsuarios.getInt(0), cursorUsuarios.getString(1), cursorUsuarios.getString(2), cursorUsuarios.getInt(3), cursorUsuarios.getString(4), cursorUsuarios.getString(5));
        }
        cursorUsuarios.close();

        db.close();

        return UsuarioInfo;
    }

    public boolean actualizarUsuario(String correoInicial, String nombresUsuario, String apellidosUsuario, int edadUsuario, String correoUsuario, String contrasenaUsuario) {
        boolean correcto;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombresUsuario", nombresUsuario);
            values.put("apellidosUsuario", apellidosUsuario);
            values.put("edadUsuario", edadUsuario);
            values.put("correoUsuarioUsuarios", correoUsuario.toLowerCase());
            values.put("contrasenaUsuario", contrasenaUsuario);

            int rowsAffected = db.update(TABLE_USUARIOS, values, "correoUsuarioUsuarios = ?", new String[]{correoInicial});

            correcto = (rowsAffected > 0);

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

        List<String> correos = new ArrayList<>();

        Cursor cursorCorreos = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);


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
