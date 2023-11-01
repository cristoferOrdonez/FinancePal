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

public class DbIngresos extends DbHelperIngresos {

    Context context;
    public DbIngresos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarIngreso(String correoUsuario, String nombreIngreso, String montoIngreso) {
        long id = 0;
        try {
            DbHelperIngresos dbHelper = new DbHelperIngresos(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("correoUsuario", correoUsuario); // Nuevo campo para almacenar el correo electronico del usuario
            values.put("nombreIngreso", nombreIngreso);
            values.put("montoIngreso", montoIngreso);

            id = db.insert(TABLE_INGRESOS, null, values);
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
                ingresoInfo = new Ingreso(cursorIngresos.getInt(0), cursorIngresos.getString(2), col.format(Integer.parseInt(cursorIngresos.getString(3))) + " COP");

                listaMetas.add(ingresoInfo);
            } while (cursorIngresos.moveToNext());
        }
        cursorIngresos.close();

        return listaMetas;
    }
    public Ingreso verIngreso(String correoUsuario, long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Ingreso IngresoInfo = null;
        Cursor cursorIngresos;

        cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE correoUsuario = ? AND id = ? LIMIT 1", new String[]{correoUsuario, String.valueOf(id)});

        if (cursorIngresos.moveToFirst()) {
            IngresoInfo = new Ingreso(cursorIngresos.getInt(0), cursorIngresos.getString(2), cursorIngresos.getString(3));
        }
        cursorIngresos.close();

        return IngresoInfo;
    }

    public boolean editarIngreso(long id, String nombreIngreso, String montoIngreso) {
        boolean correcto = false;

        DbHelperIngresos dbHelper = new DbHelperIngresos(context);
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

        DbHelperIngresos dbHelper = new DbHelperIngresos(context);
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

}
