package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.example.financepal.adaptadores.ListaMetasAdapter;
import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.entidades.MetasInfo;

import java.util.ArrayList;

public class MetasDeAhorro extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonInicio;

    ImageView botonNuevaMeta;

    RecyclerView listaMetas;

     ArrayList<MetasInfo> listaArrayMetasInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas_de_ahorro);
        listaMetas= findViewById(R.id.listaMetas);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");


        listaMetas.setLayoutManager(new LinearLayoutManager(this));

        DbNombreMetas dbNombreMetas= new DbNombreMetas(MetasDeAhorro.this);

        listaArrayMetasInfo=new ArrayList<>();

        ListaMetasAdapter adapter=new ListaMetasAdapter(dbNombreMetas.mostrarMetas(correoElectronicoS), correoElectronicoS);
        listaMetas.setAdapter(adapter);


        botonInicio = findViewById(R.id.botonAtrasInicioMETAS);
        botonInicio.setOnClickListener(view -> cambiarAInicio());

        botonNuevaMeta = findViewById(R.id.botonNuevaMeta);
        botonNuevaMeta.setOnClickListener(view -> cambiarRegistroMeta());

        eliminarMetasVencidas();

    }

    public void cambiarAInicio(){

        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }
    public void cambiarRegistroMeta(){

        Intent miIntent = new Intent(this, ingresoInformacionMetasDeAhorro.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    private void eliminarMetasVencidas() {
        // Llamar al método eliminarMetasVencidas de DbNombreMetas
        DbNombreMetas dbNombreMetas = new DbNombreMetas(MetasDeAhorro.this);
        dbNombreMetas.eliminarMetasVencidas(correoElectronicoS);

        // También podrías actualizar la lista de metas en la interfaz de usuario si es necesario
        // (por ejemplo, si usas un RecyclerView)
        actualizarListaMetas();
    }
    private void actualizarListaMetas() {
        DbNombreMetas dbNombreMetas = new DbNombreMetas(MetasDeAhorro.this);
        ListaMetasAdapter adapter = new ListaMetasAdapter(dbNombreMetas.mostrarMetas(correoElectronicoS), correoElectronicoS);
        listaMetas.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == KeyEvent.KEYCODE_BACK){

            cambiarAInicio();

        }

        return super.onKeyDown(keyCode, event);

    }

    public void finishAffinity(){
        super.finishAffinity();
    }

}