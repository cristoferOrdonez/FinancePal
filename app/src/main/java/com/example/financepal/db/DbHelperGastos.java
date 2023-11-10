package com.example.financepal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelperGastos extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NOMBRE = "gastos.db";
    public static final String TABLE_GASTOS = "t_gastosu";
    public static final String TABLE_CATEGORIAS_GASTO = "t_categ_gastosu";
    public static final String TABLE_PRIORIDAD = "t_prioridadgastosu";

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

        sqLiteDatabase.execSQL("INSERT INTO "+ TABLE_CATEGORIAS_GASTO+" (correocatgasto, nombrecatgasto,desccatgasto) VALUES" +
                "('0000','Vivienda','Gastos relacionados con el hogar')," +
                "('0000','Transporte','Gastos relacionados con transporte')," +
                "('0000','Impuestos','Gastos relacionados con el pago de impuestos')," +
                "('0000','Mercado','Gastos relacionados con el mercado')");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PRIORIDAD+"(" +
                "idprioridad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreprioridad TEXT NOT NULL)");

        sqLiteDatabase.execSQL("INSERT INTO "+ TABLE_PRIORIDAD+" (nombreprioridad) VALUES" +
                "('Alta')," +
                "('Media')," +
                "('Baja')");

        sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_GASTOS +"(" +
                "idgastos INTEGER PRIMARY KEY AUTOINCREMENT," +
                "correogasto TEXT NOT NULL," +
                "nombregasto TEXT NOT NULL," +
                "idcatgasto1 INTEGER NOT NULL," +
                "idprioridad1 INTEGER NOT NULL," +
                "montogasto BIGINT NOT NULL," +
                "recurrenciagasto TINYINT NOT NULL," +
                "FOREIGN KEY(idcatgasto1) REFERENCES "+TABLE_CATEGORIAS_GASTO+"(idcatgasto)," +
                "FOREIGN KEY(idprioridad1) REFERENCES "+ TABLE_PRIORIDAD+" (idprioridad))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_GASTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIAS_GASTO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_PRIORIDAD);
        onCreate(sqLiteDatabase);
    }
}
