package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Balance extends AppCompatActivity {

    String correoElectronicoS; //informacion del correo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

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