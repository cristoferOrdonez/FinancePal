
package com.example.financepal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DbHelperFP extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "database.db";
    public static final String TABLE_USUARIOS = "t_usuarios";
    public static final String TABLE_INGRESOS = "t_ingresos";

    public DbHelperFP(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombresUsuario TEXT NOT NULL, " +
                "apellidosUsuario TEXT NOT NULL, " +
                "edadUsuario TEXT NOT NULL, " +
                "correoUsuario TEXT NOT NULL," +
                "contrasenaUsuario TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_INGRESOS + "(" +
                "id LONG PRIMARY KEY AUTOINCREMENT, " +
                "correoUsuario TEXT NOT NULL, " +
                "nombreIngreso TEXT NOT NULL, " +
                "montoIngreso TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_INGRESOS);
        onCreate(sqLiteDatabase);
    }
}