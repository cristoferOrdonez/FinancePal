
package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelperFP extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NOMBRE = "ingresos.db";

    public static final String TABLE_INGRESOS = "t_ingresos";

    public static final String TABLE_USUARIOS = "t_usuarios";

    public static final String TABLE_METAS="t_metas";

    protected static final String TABLE_GASTOS = "t_gastosu";
    protected static final String TABLE_CATEGORIAS_GASTO = "t_categ_gastosu";
    protected static final String TABLE_PRIORIDAD = "t_prioridadgastosu";



    public DbHelperFP(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_INGRESOS + "(" +
                "idIngreso INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "correoUsuarioIngresos TEXT NOT NULL, " +
                "nombreIngreso TEXT NOT NULL, " +
                "montoIngreso INTEGER NOT NULL," +
                "fechaMesIngreso INTEGER NOT NULL," +
                "fechaYearIngreso INTEGER NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIOS + "(" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombresUsuario TEXT NOT NULL, " +
                "apellidosUsuario TEXT NOT NULL, " +
                "edadUsuario INTEGER NOT NULL, " +
                "correoUsuarioUsuarios TEXT NOT NULL," +
                "contrasenaUsuario TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_METAS + "(" +
                "idMetas INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "correoUsuarioMetas TEXT NOT NULL, " +
                "nombreMeta TEXT NOT NULL, " +
                "montoMeta INTEGER NOT NULL, " +
                "fechaMeta TEXT NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CATEGORIAS_GASTO + "(" +
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
                "fechamesgasto TEXT NOT NULL," +
                "fechaanogasto TEXT NOT NULL," +
                "FOREIGN KEY(idcatgasto1) REFERENCES "+TABLE_CATEGORIAS_GASTO+"(idcatgasto)," +
                "FOREIGN KEY(idprioridad1) REFERENCES "+ TABLE_PRIORIDAD+" (idprioridad))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {



        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_METAS);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_GASTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIAS_GASTO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_PRIORIDAD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_INGRESOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(sqLiteDatabase);

    }

    public boolean actualizarCorreos(String correoAntiguo, String correoNuevo) {
        boolean correcto;

        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues valuesIngresos = new ContentValues(), valuesCatGastos = new ContentValues(), valuesGastos = new ContentValues(), valuesMetas = new ContentValues();
            valuesIngresos.put("correoUsuarioIngresos", correoNuevo.toLowerCase());
            valuesCatGastos.put("correocatgasto", correoNuevo.toLowerCase());
            valuesGastos.put("correogasto", correoNuevo.toLowerCase());
            valuesMetas.put("correoUsuarioMetas", correoNuevo.toLowerCase());

            int rowsAffectedIngresos = db.update(TABLE_INGRESOS, valuesIngresos, "correoUsuarioIngresos = ?", new String[]{correoAntiguo});
            int rowsAffectedCatGastos = db.update(TABLE_CATEGORIAS_GASTO, valuesCatGastos, "correocatgasto = ?", new String[]{correoAntiguo});
            int rowsAffectedGastos = db.update(TABLE_GASTOS, valuesGastos, "correogasto = ?", new String[]{correoAntiguo});
            int rowsAffectedMetas = db.update(TABLE_METAS, valuesMetas, "correoUsuarioMetas = ?", new String[]{correoAntiguo});
            correcto = (rowsAffectedIngresos + rowsAffectedCatGastos + rowsAffectedGastos + rowsAffectedMetas > 0);

        } catch (Exception e) {
            e.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
/*
    public double obtenerPromedio(String correoElectronico){

        SQLiteDatabase db = this.getWritableDatabase();

        return

    }

 */

}