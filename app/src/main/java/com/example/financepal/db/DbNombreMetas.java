package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.financepal.entidades.MetasInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DbNombreMetas extends  DbHelperFP{
    Context context;
    public DbNombreMetas(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarMeta(String correoUsuario, String nombreMeta, String fechaMeta, Integer montoMeta) {
        long id = 0;
        try {
            DbHelperFP dbHelper = new DbHelperFP(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("correoUsuario", correoUsuario); // Nuevo campo para almacenar el correo electronico del usuario
            values.put("nombreMeta", nombreMeta);
            values.put("fechaMeta", fechaMeta);
            values.put("montoMeta", montoMeta);

            id = db.insert(TABLE_METAS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<MetasInfo> mostrarMetas(String correoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<MetasInfo> listaMetas = new ArrayList<>();
        MetasInfo metasInfo = null;
        Cursor cursorMetas = null;

        cursorMetas = db.rawQuery("SELECT * FROM " + TABLE_METAS + " WHERE correoUsuario = ?", new String[]{correoUsuario});

        if (cursorMetas.moveToFirst()) {
            do {
                metasInfo = new MetasInfo();
                metasInfo.setId(cursorMetas.getInt(0));
                metasInfo.setNombreMeta(cursorMetas.getString(2));
                metasInfo.setMontoMeta(cursorMetas.getInt(3));
                metasInfo.setFechaMeta(cursorMetas.getString(4));

                listaMetas.add(metasInfo);
            } while (cursorMetas.moveToNext());
        }
        cursorMetas.close();

        return listaMetas;
    }
    public MetasInfo verMetas(String correoUsuario, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        MetasInfo metaInfo = null;
        Cursor cursorMetas;

        cursorMetas = db.rawQuery("SELECT * FROM " + TABLE_METAS + " WHERE correoUsuario = ? AND idMetas = ? LIMIT 1", new String[]{correoUsuario, String.valueOf(id)});

        if (cursorMetas.moveToFirst()) {
            metaInfo = new MetasInfo();
            metaInfo.setId(cursorMetas.getInt(0));
            metaInfo.setNombreMeta(cursorMetas.getString(2));
            metaInfo.setMontoMeta(cursorMetas.getInt(3));
            metaInfo.setFechaMeta(cursorMetas.getString(4));
        }
        cursorMetas.close();

        return metaInfo;
    }

    public boolean editarMeta(int id, String nombreMeta, String fechaMeta, Integer montoMeta) {
        boolean correcto = false;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombreMeta", nombreMeta);
            values.put("fechaMeta", fechaMeta);
            values.put("montoMeta", montoMeta);

            int rowsAffected = db.update(TABLE_METAS, values, "idMetas=?", new String[]{String.valueOf(id)});

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

    public boolean elimnarMeta(int id) {
        boolean correcto = false;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL( " DELETE FROM "+ TABLE_METAS + " WHERE idMetas = '" +id+ "' ");
            correcto=true;



        } catch (Exception e) {
            e.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}