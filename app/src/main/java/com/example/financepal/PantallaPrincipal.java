package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PantallaPrincipal extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonBalance, botonHistorico, botonMisDatos;
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

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        String nombre = "";

        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("InfoUsuariosFinancePal.txt"));
            BufferedReader br = new BufferedReader(archivo);

            String correoElectronico;

            String linea = br.readLine();

            while(linea != null){

                correoElectronico = linea.substring(linea.indexOf("correoElectronico") + "correoElectronico: ".length(), linea.indexOf("contrasena") - 2);

                if(correoElectronico.equalsIgnoreCase(correoElectronicoS)) {

                    nombre = linea.substring(0, linea.indexOf("edad")).replaceAll("nombres: ", "").replaceAll("; apellidos:", "").replaceAll("; ", "");
                    break;

                }

                linea = br.readLine();

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Toast.makeText(this, "INICIO", Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Bienvenido " + nombre, Toast.LENGTH_SHORT).show();

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

    public void cambiarAGastos(View view){
        Intent miIntent = new Intent(this, Gastos.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}