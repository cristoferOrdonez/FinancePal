package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.financepal.entidades.MetasInfo;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

public class DbNombreMetas extends  DbHelperFP{
    Context context;
    public DbNombreMetas(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarMeta(String correoUsuarioMetas, String nombreMeta, String fechaMeta, long montoMeta) {
        long id = 0;
        try {
            DbHelperFP dbHelper = new DbHelperFP(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("correoUsuarioMetas", correoUsuarioMetas); // Nuevo campo para almacenar el correo electronico del usuario
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
        MetasInfo metasInfo;
        Cursor cursorMetas;
        NumberFormat col = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));


        cursorMetas = db.rawQuery("SELECT * FROM " + TABLE_METAS + " WHERE correoUsuarioMetas = ?", new String[]{correoUsuario});

        if (cursorMetas.moveToFirst()) {
            do {
                metasInfo = new MetasInfo();
                metasInfo.setId(cursorMetas.getInt(0));
                metasInfo.setNombreMeta(cursorMetas.getString(2));
                metasInfo.setMontoMeta(cursorMetas.getLong(3));
                metasInfo.setFechaMeta(cursorMetas.getString(4));

                metasInfo.setMontoTotalFormateado(col.format(cursorMetas.getLong(3)));
                metasInfo.setMontoMensualFormateado(col.format(calcularMontoMensual(cursorMetas.getString(4), cursorMetas.getLong(3))));

                metasInfo.setMontoMensual(calcularMontoMensual(metasInfo.getFechaMeta(), metasInfo.getMontoMeta()));


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

        cursorMetas = db.rawQuery("SELECT * FROM " + TABLE_METAS + " WHERE correoUsuarioMetas = ? AND idMetas = ? LIMIT 1", new String[]{correoUsuario, String.valueOf(id)});

        if (cursorMetas.moveToFirst()) {
            metaInfo = new MetasInfo();
            metaInfo.setId(cursorMetas.getInt(0));
            metaInfo.setNombreMeta(cursorMetas.getString(2));
            metaInfo.setMontoMeta(cursorMetas.getLong(3));
            metaInfo.setFechaMeta(cursorMetas.getString(4));
        }
        cursorMetas.close();

        return metaInfo;
    }

    public boolean editarMeta(int id, String nombreMeta, String fechaMeta, Long montoMeta) {
        boolean correcto;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombreMeta", nombreMeta);
            values.put("fechaMeta", fechaMeta);
            values.put("montoMeta", montoMeta);

            int rowsAffected = db.update(TABLE_METAS, values, "idMetas=?", new String[]{String.valueOf(id)});

            correcto = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public double sumarMontosMensuales(String correoUsuario) {
        double sumaMontosMensuales = 0;

        ArrayList<MetasInfo> listaMetas = mostrarMetas(correoUsuario);

        for (MetasInfo meta : listaMetas) {
            // Sumar el monto mensual de cada meta
            sumaMontosMensuales += meta.getMontoMensual();
        }

        return sumaMontosMensuales;
    }

    /*
    public boolean actualizarCorreosMetas(String correoAntiguo, String correoNuevo) {
        boolean correcto;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("correoUsuarioMetas", correoNuevo.toLowerCase());

            int rowsAffected = db.update(TABLE_METAS, values, "correoUsuarioMetas = ?", new String[]{correoAntiguo});

            correcto = (rowsAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

     */


    public void eliminarMetasVencidas(String correoUsuario) {

        ArrayList<MetasInfo> listaMetas = mostrarMetas(correoUsuario);

        for (MetasInfo meta : listaMetas) {
            // Verificar si la fecha de la meta es anterior a la fecha actual
            if (esFechaAnterior(meta.getFechaMeta())) {
                // Eliminar la meta
                elimnarMeta(meta.getId());
            }
        }
    }

    private boolean esFechaAnterior(String fechaMeta) {
        try {
            Date fechaActual = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
            Date fechaMetaDate = dateFormat.parse(fechaMeta);

            return fechaMetaDate != null && fechaMetaDate.before(fechaActual);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double calcularMontoMensual(String fechaMeta, long montoTotalMeta) {
        double montoMensual = 0;

        try {
            Date fechaActual = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
            Date fechaMetaDate = dateFormat.parse(fechaMeta);

            if (fechaMetaDate != null) {
                Calendar calendarActual = Calendar.getInstance();
                calendarActual.setTime(fechaActual);

                Calendar calendarMeta = Calendar.getInstance();
                calendarMeta.setTime(fechaMetaDate);

                int diferenciaMeses = (calendarMeta.get(Calendar.YEAR) - calendarActual.get(Calendar.YEAR)) * 12 +
                        calendarMeta.get(Calendar.MONTH) - calendarActual.get(Calendar.MONTH);

                if (diferenciaMeses > 0) {
                    montoMensual = (double) montoTotalMeta / diferenciaMeses;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return montoMensual;
    }


    public boolean elimnarMeta(int id) {
        boolean correcto;

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL( " DELETE FROM "+ TABLE_METAS + " WHERE idMetas = '" +id+ "' ");
            correcto=true;



        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
