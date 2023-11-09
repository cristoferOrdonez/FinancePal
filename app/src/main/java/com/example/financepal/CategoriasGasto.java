package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.financepal.adaptadores.CustomAdapterCategGastos;
import com.example.financepal.adaptadores.CustomAdapterGastos;
import com.example.financepal.db.DbGastos;
import com.example.financepal.entidades.UsuarioCategoriasGasto;

import java.util.List;

public class CategoriasGasto extends AppCompatActivity {
    String correoElectronicoS;
    ListView ListViewCatGastos;
    List<UsuarioCategoriasGasto> lista;

    private DbGastos db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_gasto);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        db = new DbGastos(this);
        listarDatos();
        db.close();
    }

    private void listarDatos() {
        try{
            ListViewCatGastos = (ListView) findViewById(R.id.listViewCategGastos);
            lista=db.buscarCategGastos(correoElectronicoS);
            CustomAdapterCategGastos adapter = new CustomAdapterCategGastos(this,lista);
            ListViewCatGastos.setAdapter(adapter);
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void cambiaraAtras(View view){
        Intent miIntent = new Intent(this, Gastos.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }

    public void cambiaraNuevaCateg(View view){
        Intent miIntent = new Intent(this, AgregarCategoriaGasto.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
}