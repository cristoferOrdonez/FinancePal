package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.financepal.adaptadores.ListaMetasAdapter;
import com.example.financepal.db.DbHelper;
import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.entidades.MetasInfo;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class MetasDeAhorro extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonInicio;

    Button botonNuevaMeta;

    RecyclerView listaMetas;

     ArrayList<MetasInfo> listaArrayMetasInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas_de_ahorro);
        listaMetas= findViewById(R.id.listaMetas);

        listaMetas.setLayoutManager(new LinearLayoutManager(this));

        DbNombreMetas dbNombreMetas= new DbNombreMetas(MetasDeAhorro.this);

        listaArrayMetasInfo=new ArrayList<>();

        ListaMetasAdapter adapter=new ListaMetasAdapter(dbNombreMetas.mostrarMetas());
        listaMetas.setAdapter(adapter);


        botonInicio = findViewById(R.id.botonAtrasInicioMETAS);
        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAInicio(view);
            }
        });

        botonNuevaMeta = findViewById(R.id.botonNuevaMeta);
        botonNuevaMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarRegistroMeta(view);
            }
        });

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        Toast.makeText(this, "METAS DE AHORRO", Toast.LENGTH_SHORT).show();

        DbHelper dbHelper= new DbHelper(MetasDeAhorro.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

    }

    public void cambiarAInicio(View view){

        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }
    public void cambiarRegistroMeta(View view){

        Intent miIntent = new Intent(this, ingresoInformacionMetasDeAhorro.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }


}