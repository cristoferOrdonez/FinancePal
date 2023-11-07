package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHelperGastos;
import com.example.financepal.entidades.UsuarioGastos;

import java.util.List;

public class Gastos extends AppCompatActivity {
    String correoElectronicoS;
    ListView ListViewGastos;
    List<InfoGasto> lst;

    private DbGastos db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        db = new DbGastos(this);
        setContentView(R.layout.activity_gastos);
        listarDatos();


    }

    public void listarDatos(){
        ListView ListViewGastos = findViewById(R.id.listViewGastos);
        ArrayList<UsuarioGastos>lista=db.buscarUsuario(correoElectronicoS);
        if(!lista.isEmpty()){

            CustomAdapterGastos adapter = new CustomAdapterGastos(this,lista);
            ListViewGastos.setAdapter(adapter);
            ListViewGastos.setVisibility(View.VISIBLE);
        }
        else{
            ListViewGastos.setVisibility(View.INVISIBLE);
        }

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

