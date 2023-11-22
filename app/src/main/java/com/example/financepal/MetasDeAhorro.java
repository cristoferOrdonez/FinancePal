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
import com.example.financepal.db.DbHelperFP;
import com.example.financepal.db.DbHelperFP;
import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.entidades.MetasInfo;

import java.sql.SQLDataException;
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


        Toast.makeText(this, "METAS DE AHORRO", Toast.LENGTH_SHORT).show();

        DbHelperFP dbHelper= new DbHelperFP(MetasDeAhorro.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        eliminarMetasVencidas();

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

    private void eliminarMetasVencidas() {
        // Llamar al método eliminarMetasVencidas de DbNombreMetas
        DbNombreMetas dbNombreMetas = new DbNombreMetas(MetasDeAhorro.this);
        dbNombreMetas.eliminarMetasVencidas(correoElectronicoS);

        // Puedes agregar un mensaje de éxito o realizar otras acciones después de eliminar las metas vencidas
        Toast.makeText(this, "Metas vencidas eliminadas", Toast.LENGTH_SHORT).show();

        // También podrías actualizar la lista de metas en la interfaz de usuario si es necesario
        // (por ejemplo, si usas un RecyclerView)
        actualizarListaMetas();
    }
    private void actualizarListaMetas() {
        DbNombreMetas dbNombreMetas = new DbNombreMetas(MetasDeAhorro.this);
        ListaMetasAdapter adapter = new ListaMetasAdapter(dbNombreMetas.mostrarMetas(correoElectronicoS), correoElectronicoS);
        listaMetas.setAdapter(adapter);
    }


}