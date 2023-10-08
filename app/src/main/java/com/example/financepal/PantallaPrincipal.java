package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        String contenido;

        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("InfoUsuariosFinancePal.txt"));
            BufferedReader br = new BufferedReader(archivo);

            contenido = "";

            String linea = br.readLine();

            while(linea != null){

                contenido += linea;
                linea = br.readLine();

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Toast.makeText(this, contenido, Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}