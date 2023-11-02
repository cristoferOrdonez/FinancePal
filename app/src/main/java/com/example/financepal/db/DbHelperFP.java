package com.example.financepal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelperFP extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NOMBRE = "ingresos.db";
    public static final String TABLE_INGRESOS = "t_ingresos";

    public static final String TABLE_USUARIOS = "t_usuarios";



    public DbHelperFP(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_INGRESOS + "(" +
                "idIngreso INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "correoUsuarioIngresos TEXT, " +
                "nombreIngreso TEXT NOT NULL, " +
                "montoIngreso TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIOS + "(" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombresUsuario TEXT NOT NULL, " +
                "apellidosUsuario TEXT NOT NULL, " +
                "edadUsuario TEXT NOT NULL, " +
                "correoUsuarioUsuarios TEXT NOT NULL," +
                "contrasenaUsuario TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE "+ TABLE_INGRESOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIOS);
        onCreate(sqLiteDatabase);

    }

}
