package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.financepal.PantallaPrincipal;
import com.example.financepal.entidades.EntidadHistorico;
import com.example.financepal.entidades.Ingreso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DbHistorico extends DbHelperFP{
    Context context;

    public DbHistorico(@Nullable Context context){

        super(context);
        this.context = context;

    }

    public List<EntidadHistorico> mostrarHistorico(String correoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<EntidadHistorico> listaHistorico = new ArrayList<>();
        EntidadHistorico historicoInfo;
        Cursor cursorHistorico = db.rawQuery("SELECT * FROM " + TABLE_HISTORICO + " WHERE correoUsuarioHistorico = ?", new String[]{correoUsuario});
        NumberFormat col = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        GregorianCalendar calendar = new GregorianCalendar();
        int mesActual = calendar.get(Calendar.MONTH) + 1;
        int anoActual = calendar.get(Calendar.YEAR);

        if (cursorHistorico.moveToFirst()) {
            do {
                if(!(cursorHistorico.getInt(4) == mesActual && cursorHistorico.getInt(5) == anoActual)) {
                    historicoInfo = new EntidadHistorico(cursorHistorico.getInt(0), cursorHistorico.getLong(2), cursorHistorico.getLong(3), cursorHistorico.getInt(4) + "/" + cursorHistorico.getInt(5));
                    listaHistorico.add(historicoInfo);
                }
            } while (cursorHistorico.moveToNext());
        }
        cursorHistorico.close();
        //Collections.reverse(listaHistorico);
        db.close();
        return listaHistorico;
    }

    public void actualizarHistorico(String correoElectronico, long ingresosTotales, long gastosTotales){

        DbHelperFP dbHelper = new DbHelperFP(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        GregorianCalendar calendar = new GregorianCalendar();

        int mesActual = calendar.get(Calendar.MONTH) + 1;
        int anoActual = calendar.get(Calendar.YEAR);

        try {
            db.execSQL(" DELETE FROM " + TABLE_HISTORICO + " WHERE correoUsuarioHistorico = '" + correoElectronico + "' AND fechaMesHistorico = '" + mesActual + "' AND fechaYearHistorico = '" + anoActual + "' ");

        } catch (Exception e) {

        }

        try {

            ContentValues values = new ContentValues();
            values.put("correoUsuarioHistorico", correoElectronico); // Nuevo campo para almacenar el correo electronico del usuario
            values.put("ingresoTotalHistorico", ingresosTotales);
            values.put("gastoTotalHistorico", gastosTotales);
            values.put("fechaMesHistorico", mesActual);
            values.put("fechaYearHistorico", anoActual);

            db.insert(TABLE_HISTORICO, null, values);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();

    }



}
