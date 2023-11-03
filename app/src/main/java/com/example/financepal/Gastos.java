package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.financepal.db.DbHelperGastos;

public class Gastos extends AppCompatActivity {
    String correoElectronicoS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        DbHelperGastos dbHelper = new DbHelperGastos(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db!= null){
            Toast.makeText(this,"BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"ERROR", Toast.LENGTH_LONG).show();
        }

    }
}

