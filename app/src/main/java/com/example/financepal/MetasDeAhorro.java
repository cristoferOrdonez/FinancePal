package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MetasDeAhorro extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonInicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas_de_ahorro);


        botonInicio = findViewById(R.id.botonInicioMetasDeAhorro);
        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAInicio(view);
            }
        });




        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        Toast.makeText(this, "Metas de Ahorro", Toast.LENGTH_SHORT).show();

    }

    public void cambiarAInicio(View view){

        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }



}