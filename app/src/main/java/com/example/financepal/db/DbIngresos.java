package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.financepal.entidades.Ingreso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DbIngresos extends DbHelperFP {

    Context context;
    public DbIngresos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarIngreso(String correoUsuario, String nombreIngreso, String montoIngreso) {
        long id = 0;
        try {
            DbHelperFP dbHelper = new DbHelperFP(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("correoUsuario", correoUsuario); // Nuevo campo para almacenar el correo electronico del usuario
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

        cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE correoUsuario = ?", new String[]{correoUsuario});

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

        cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE id = ? LIMIT 1", new String[]{String.valueOf(id)});

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

            int rowsAffected = db.update(TABLE_INGRESOS, values, "id=?", new String[]{String.valueOf(id)});

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

    public List obtenerNombresMetas(String correoUsuario){

        SQLiteDatabase db = this.getWritableDatabase();

        List<String> nombres = new ArrayList<String>();

        Cursor cursorIngresos = null;

        cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE correoUsuario = ?", new String[]{correoUsuario});


        if (cursorIngresos.moveToFirst()) {
            do {
                nombres.add(cursorIngresos.getString(2));

            } while (cursorIngresos.moveToNext());
        }
        cursorIngresos.close();

        db.close();

        return nombres;

    }

}
