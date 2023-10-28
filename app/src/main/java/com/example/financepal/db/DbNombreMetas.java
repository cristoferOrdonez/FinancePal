package com.example.financepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

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
}
