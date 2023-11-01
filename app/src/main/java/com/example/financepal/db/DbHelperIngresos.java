package com.example.financepal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelperIngresos extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NOMBRE="ingresos.db";
    public static final String TABLE_INGRESOS="t_ingresos";



    public DbHelperIngresos(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_INGRESOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "correoUsuario TEXT, " +
                "nombreIngreso TEXT NOT NULL, " +
                "montoIngreso TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE "+ TABLE_INGRESOS);
        onCreate(sqLiteDatabase);


    }

}
