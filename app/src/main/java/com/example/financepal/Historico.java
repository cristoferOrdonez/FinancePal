package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Historico extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonBalance, botonInicio, botonMisDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);


        botonBalance = findViewById(R.id.botonBalanceHISTORICO);
        botonBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarABalance(view);
            }
        });

        botonInicio = findViewById(R.id.botonInicioHISTORICO);
        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAInicio(view);
            }
        });

        botonMisDatos = findViewById(R.id.botonMisDatosHISTORICO);
        botonMisDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAMisDatos(view);
            }
        });


        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        Toast.makeText(this, "HISTORICO", Toast.LENGTH_SHORT).show();

    }

    public void cambiarAInicio(View view){

        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    public void cambiarABalance(View view){

        Intent miIntent = new Intent(this, Balance.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    public void cambiarAMisDatos(View view){

        Intent miIntent = new Intent(this, MisDatos.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

}