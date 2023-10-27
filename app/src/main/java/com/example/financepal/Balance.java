package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Balance extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonHistorico, botonInicio, botonMisDatos;


    ImageView botonInicio, botonHistorico, botonMisDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        botonHistorico = findViewById(R.id.botonHistoricoBALANCE);
        botonHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAHistorico(view);
            }
        });

        botonInicio = findViewById(R.id.botonInicioBALANCE);
        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAInicio(view);
            }
        });

        botonMisDatos = findViewById(R.id.botonMisDatosBALANCE);
        botonMisDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAMisDatos(view);
            }
        });



        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonInicio = findViewById(R.id.botonInicioBALANCE);
        botonInicio.setOnClickListener(view -> cambiarAInicio(view));

        botonHistorico = findViewById(R.id.botonHistoricoBALANCE);
        botonHistorico.setOnClickListener(view -> cambiarAHistorico(view));

        botonMisDatos = findViewById(R.id.botonMisDatosBALANCE);
        botonMisDatos.setOnClickListener(view -> cambiarAMisDatos(view));

        Toast.makeText(this, "BALANCE", Toast.LENGTH_SHORT).show();

    }

    public void cambiarAInicio(View view){

        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
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

}