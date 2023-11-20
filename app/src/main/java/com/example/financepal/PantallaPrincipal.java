package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PantallaPrincipal extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonBalance, botonHistorico, botonMisDatos;

    //Button buttonMetasdeAhorro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        botonBalance = findViewById(R.id.botonBalanceINICIO);
        botonBalance.setOnClickListener(view -> cambiarABalance(view));

        botonHistorico = findViewById(R.id.botonHistoricoINICIO);
        botonHistorico.setOnClickListener(view -> cambiarAHistorico(view));

        botonMisDatos = findViewById(R.id.botonMisDatosINICIO);
        botonMisDatos.setOnClickListener(view -> cambiarAMisDatos(view));

        /*
        buttonMetasdeAhorro = findViewById(R.id.buttonMetasdeAhorroINICIO);

        buttonMetasdeAhorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAMetasDeAhorro(view);
            }
        });
        */



        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

    }

    public void cambiarABalance(View view){

        Intent miIntent = new Intent(this, Balance.class);
        miIntent.putExtra("correoElectronico",correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    public void cambiarAHistorico(View view){

        Intent miIntent = new Intent(this, Historico.class);
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

    public void cambiarAIngresos(View view){

        Intent miIntent = new Intent(this, Ingresos.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);

    }
    public void cambiarAMetasDeAhorro(View view) {
        Intent miIntent = new Intent(this, MetasDeAhorro.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
    }


    public void cambiarAGastos(View view){
        Intent miIntent = new Intent(this, Gastos.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}