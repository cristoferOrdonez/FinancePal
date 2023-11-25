package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.financepal.adaptadores.AdaptadorHistorico;
import com.example.financepal.adaptadores.CustomAdapterGastos;
import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHistorico;
import com.example.financepal.entidades.EntidadHistorico;
import com.example.financepal.entidades.UsuarioGastos;

import java.util.List;

public class Historico extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonInicio, botonBalance, botonMisDatos;
    private DbHistorico db;
    List<EntidadHistorico> lista;

    ListView ListViewHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        db = new DbHistorico(this);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonInicio = findViewById(R.id.botonInicioHISTORICO);
        botonInicio.setOnClickListener(view -> cambiarAInicio(view));

        botonBalance = findViewById(R.id.botonBalanceHISTORICO);
        botonBalance.setOnClickListener(view -> cambiarABalance(view));

        botonMisDatos = findViewById(R.id.botonMisDatosHISTORICO);
        botonMisDatos.setOnClickListener(view -> cambiarAMisDatos(view));

        ListViewHistorico = findViewById(R.id.listViewHistorico);
        try {
            lista=db.mostrarHistorico(correoElectronicoS);
            AdaptadorHistorico adapter = new AdaptadorHistorico(this,lista);
            ListViewHistorico.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }


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