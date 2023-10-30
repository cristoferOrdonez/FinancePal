package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.financepal.entidades.MetasInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DbNombreMetas extends  DbHelper{
    Context context;
    public DbNombreMetas(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarMeta(String nombreMeta, String fechaMeta, Integer montoMeta){
        long id=0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombreMeta", nombreMeta);
            values.put("fechaMeta", fechaMeta);
            values.put("montoMeta", montoMeta);

            id = db.insert(TABLE_METAS, null, values);
        }
        catch (Exception e){
           e.toString();
        }
        return id;
    }

    public ArrayList<MetasInfo> mostrarMetas(){

        DbHelper dbHelper =new DbHelper(context);
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        ArrayList<MetasInfo> listaMetas=new ArrayList<>();

        MetasInfo metasInfo =null;
        Cursor cursorMetas= null;

        cursorMetas= db.rawQuery("SELECT * FROM "+ TABLE_METAS, null);

        if(cursorMetas.moveToFirst()){
            do{
                metasInfo = new MetasInfo();
                metasInfo.setId(cursorMetas.getInt(0));
                metasInfo.setNombreMeta(cursorMetas.getString(1));
                metasInfo.setMontoMeta(cursorMetas.getInt(2));
                metasInfo.setFechaMeta(cursorMetas.getString(3));

                listaMetas.add(metasInfo);


            }while(cursorMetas.moveToNext());
        }
        cursorMetas.close();

        return listaMetas;
    }
    public MetasInfo verMetas(int id){

        DbHelper dbHelper =new DbHelper(context);
        SQLiteDatabase db= dbHelper.getWritableDatabase();


        MetasInfo metaInfo=null;
        Cursor cursorMetas;

        cursorMetas= db.rawQuery("SELECT * FROM "+ TABLE_METAS+ " WHERE id= "+id+ " LIMIT 1 ", null);

        if(cursorMetas.moveToFirst()) {

            metaInfo = new MetasInfo();
            metaInfo.setId(cursorMetas.getInt(0));
            metaInfo.setNombreMeta(cursorMetas.getString(1));
            metaInfo.setMontoMeta(cursorMetas.getInt(2));
            metaInfo.setFechaMeta(cursorMetas.getString(3));
        }





        cursorMetas.close();

        return metaInfo;
    }

    public boolean editarMeta(int id, String nombreMeta, String fechaMeta, Integer montoMeta) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("nombreMeta", nombreMeta);
            values.put("fechaMeta", fechaMeta);
            values.put("montoMeta", montoMeta);

            int rowsAffected = db.update(TABLE_METAS, values, "id=?", new String[]{String.valueOf(id)});

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
}
