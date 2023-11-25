package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHistorico;
import com.example.financepal.db.DbIngresos;
import com.example.financepal.entidades.EntidadHistorico;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

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

        DbHistorico dbHistorico = new DbHistorico(this);
        dbHistorico.actualizarHistorico(correoElectronicoS, new DbIngresos(this).obtenerIngresosTotales(correoElectronicoS), new DbGastos(this).mostrarGastosTotales(correoElectronicoS));

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
        finishAffinity();

    }
    public void cambiarAMetasDeAhorro(View view) {
        Intent miIntent = new Intent(this, MetasDeAhorro.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }


    public void cambiarAGastos(View view){
        Intent miIntent = new Intent(this, Gastos.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == event.KEYCODE_BACK){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de Finance Pal?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which){

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which){

                            dialog.dismiss();

                        }

                    });
            builder.show();
        }

        return super.onKeyDown(keyCode, event);

    }
}