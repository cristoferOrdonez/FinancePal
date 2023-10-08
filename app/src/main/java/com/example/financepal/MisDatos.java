package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MisDatos extends AppCompatActivity {

    String nombresS, apellidosS, edadS, correoElectronicoS, contrasenaS, infoUsuario;
    EditText editTextNombres, editTextApellidos, editTextEdad, editTextCorreoElectronico, editTextContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);

        editTextNombres = findViewById(R.id.editTextNombresMISDATOS);
        editTextApellidos = findViewById(R.id.editTextApellidosMISDATOS);
        editTextEdad = findViewById(R.id.editTextEdadMISDATOS);
        editTextCorreoElectronico = findViewById(R.id.editTextCorreoElectronicoRMISDATOS);
        editTextContrasena = findViewById(R.id.editTextContrasenaRMISDATOS);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico").toString();

        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("InfoUsuariosFinancePal.txt"));
            BufferedReader br = new BufferedReader(archivo);

            String correoElectronico;

            String linea = br.readLine();

            while(linea != null){

                correoElectronico = linea.substring(linea.indexOf("correoElectronico") + "correoElectronico: ".length(), linea.indexOf("contrasena") - 2);

                if(correoElectronico.equalsIgnoreCase(correoElectronicoS)) {

                    infoUsuario = linea;

                    nombresS = linea.substring(9, linea.indexOf("; apellidos"));
                    apellidosS = linea.substring(linea.indexOf("apellidos") + "apellidos: ".length(), linea.indexOf("edad") - 2);
                    edadS = linea.substring(linea.indexOf("edad") + "edad: ".length(), linea.indexOf("correoElectronico") - 2);
                    contrasenaS = linea.substring(linea.indexOf("contrasena") + "contrasena: ".length(), linea.length() - 1);

                    editTextNombres.setText(nombresS);
                    editTextApellidos.setText(apellidosS);
                    editTextEdad.setText(edadS);
                    editTextCorreoElectronico.setText(correoElectronicoS);
                    editTextContrasena.setText(contrasenaS);

                    break;

                }

                linea = br.readLine();

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Toast.makeText(this, "MIS DATOS", Toast.LENGTH_SHORT).show();

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

    public void cambiarAHistorico(View view){

        Intent miIntent = new Intent(this, Historico.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }



}