package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHelperGastos;

import java.util.List;

public class Gastos extends AppCompatActivity {
    String correoElectronicoS;
    ListView ListViewGastos;
    List<InfoGasto> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        DbHelperGastos dbHelper = new DbHelperGastos(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if(db!=null){
            Toast.makeText(this,"BASE DE DATOS CREADA", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"ERROR", Toast.LENGTH_SHORT).show();
        }

        DbGastos db2 = new DbGastos(this);
        db2.insertarprimeraCategoria(correoElectronicoS);


        ListView ListViewGastos = findViewById(R.id.listViewGastos);
    }


    public void cambiarAPantallaPrincipal(View view){
        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }

    public void cambiarANuevoGasto(View view){
        Intent miIntent = new Intent(this, AgregarGasto.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
}

