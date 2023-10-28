package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Ingresos extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonMas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonMas = findViewById(R.id.botonMasIngresos);
        botonMas.setOnClickListener(view -> cambiarParaCrearIngreso(view));

    }

    public void volver(View view){

        Intent myIntent = new Intent(this, PantallaPrincipal.class);
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(myIntent);
        finishAffinity();

    }

    public void cambiarParaCrearIngreso(View view){

        Intent myIntent = new Intent(this, CrearModificarIngresos.class);
        myIntent.putExtra("funcionBoton", "Crear");
        startActivity(myIntent);
        finishAffinity();

    }

    public void cambiarParaModificarIngreso(View view){

        Intent myIntent = new Intent(this, CrearModificarIngresos.class);
        myIntent.putExtra("funcionBoton", "Guardar");
        startActivity(myIntent);
        finishAffinity();

    }

}