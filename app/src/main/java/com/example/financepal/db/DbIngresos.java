package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.financepal.entidades.Ingreso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DbIngresos extends DbHelperFP {

    Context context;

    public DbIngresos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarIngreso(String correoUsuario, String nombreIngreso, int montoIngreso) {
        long id = 0;
        try {
            DbHelperFP dbHelper = new DbHelperFP(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            GregorianCalendar calendario = new GregorianCalendar();

            ContentValues values = new ContentValues();
            values.put("correoUsuarioIngresos", correoUsuario); // Nuevo campo para almacenar el correo electronico del usuario
            values.put("nombreIngreso", nombreIngreso);
            values.put("montoIngreso", montoIngreso);
            values.put("fechaMesIngreso", calendario.get(Calendar.MONTH) + 1);
            values.put("fechaYearIngreso", calendario.get(Calendar.YEAR));

            id = db.insert(TABLE_INGRESOS, null, values);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public List<Ingreso> mostrarIngresos(String correoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Ingreso> listaIngresos = new ArrayList<>();
        Ingreso ingresoInfo;
        Cursor cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE correoUsuarioIngresos = ?", new String[]{correoUsuario});

        NumberFormat col = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        if (cursorIngresos.moveToFirst()) {
            do {
                ingresoInfo = new Ingreso(cursorIngresos.getInt(0), cursorIngresos.getString(2), col.format(cursorIngresos.getInt(3)) + " COP", ((String.valueOf(cursorIngresos.getInt(4)).length() == 1)?"0" + cursorIngresos.getInt(4): cursorIngresos.getInt(4)) + "/" + cursorIngresos.getInt(5));

                listaIngresos.add(ingresoInfo);
            } while (cursorIngresos.moveToNext());
        }
        cursorIngresos.close();

        db.close();
        return listaIngresos;
    }

    public Ingreso verIngreso(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Ingreso IngresoInfo = null;
        Cursor cursorIngresos;

        cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE idIngreso = ? LIMIT 1", new String[]{String.valueOf(id)});

        if (cursorIngresos.moveToFirst()) {
            IngresoInfo = new Ingreso(cursorIngresos.getInt(0), cursorIngresos.getString(2), cursorIngresos.getInt(3) + "", cursorIngresos.getInt(4) + "/" + cursorIngresos.getInt(5));
        }
        cursorIngresos.close();

        db.close();
        return IngresoInfo;
    }

    public boolean editarIngreso(long id, String nombreIngreso, int montoIngreso) {
        boolean correcto;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombreIngreso", nombreIngreso);
            values.put("montoIngreso", montoIngreso);

            int rowsAffected = db.update(TABLE_INGRESOS, values, "idIngreso = ?", new String[]{String.valueOf(id)});

            correcto = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean elimnarIngreso(long id) {
        boolean correcto;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL(" DELETE FROM " + TABLE_INGRESOS + " WHERE idIngreso = '" + id + "' ");
            correcto = true;

        } catch (Exception e) {
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public List obtenerNombresIngresos(String correoUsuario) {

        SQLiteDatabase db = this.getWritableDatabase();

        List<String> nombres = new ArrayList<>();

        Cursor cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE correoUsuarioIngresos = ?", new String[]{correoUsuario});


        if (cursorIngresos.moveToFirst()) {
            do {
                nombres.add(cursorIngresos.getString(2));

            } while (cursorIngresos.moveToNext());
        }
        cursorIngresos.close();

        db.close();

        return nombres;

    }

    public double obtenerIngresosTotales(String correoUsuario){

        double total = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorIngresos = db.rawQuery("SELECT * FROM " + TABLE_INGRESOS + " WHERE correoUsuarioIngresos = ?", new String[]{correoUsuario});

        if (cursorIngresos.moveToFirst()) {
            do {
                total += cursorIngresos.getInt(3);

            } while (cursorIngresos.moveToNext());
        }
        cursorIngresos.close();

        db.close();

        return total;

    }

}