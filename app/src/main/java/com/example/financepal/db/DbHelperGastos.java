package com.example.financepal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelperGastos extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "gastos.db";
    public static final String TABLE_GASTOS = "t_gastosu";
    public static final String TABLE_CATEGORIAS_GASTO = "t_categ_gastosu";

    public DbHelperGastos(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CATEGORIAS_GASTO+"(" +
                "idcatgasto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "correocatgasto TEXT NOT NULL," +
                "nombrecatgasto TEXT NOT NULL," +
                "desccatgasto TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_GASTOS +"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "correogasto TEXT NOT NULL," +
                "nombregasto TEXT NOT NULL," +
                "idcatgasto1 INTEGER NOT NULL," +
                "montogasto BIGINT NOT NULL," +
                "prioridadgasto TEXT NOT NULL," +
                "recurrenciagasto TINYINT NOT NULL," +
                "FOREIGN KEY(idcatgasto1) REFERENCES "+TABLE_CATEGORIAS_GASTO+"(idcatgasto))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_GASTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIAS_GASTO);
        onCreate(sqLiteDatabase);
    }
}
