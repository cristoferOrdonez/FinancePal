package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Declaracion de botones
    private Button botonAcceder;

    //Declaracion de las casillas editables de texto
    private EditText editTextCorreoElectronico, editTextContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializacion de botones
        botonAcceder = findViewById(R.id.botonAcceder);

        //Inicialización de editTexts
        editTextCorreoElectronico = findViewById((R.id.editTextCorreoElectronico));
        editTextContrasena = findViewById(R.id.editTextTextContrasena);

    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View view) {
        Intent miIntent = new Intent(MainActivity.this, Registro.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void acceder(View view) {
        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("InfoUsuariosFinancePal.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();

            boolean flag = true;

            while (linea != null) {

                String credencialesS = linea.substring(linea.indexOf("correoElectronico:"), linea.length());
                String correoElectronicoS = credencialesS.substring(credencialesS.indexOf(":") + 2, credencialesS.indexOf(";"));
                String contrasenaS = credencialesS.replaceAll(";", "").replace("correoElectronico: ", "").replaceFirst(correoElectronicoS, "").replace(" contrasena: ", "");

                if (correoElectronicoS.equalsIgnoreCase(editTextCorreoElectronico.getText().toString()) && contrasenaS.equals(editTextContrasena.getText().toString())) {
                    Intent miIntent = new Intent(MainActivity.this, PantallaPrincipal.class);
                    miIntent.putExtra("correoElectronico", correoElectronicoS);
                    startActivity(miIntent);
                    flag = false;
                }


                linea = br.readLine();
            }
            if(flag){
                editTextContrasena.setText("");
                editTextCorreoElectronico.setText("");
                Toast.makeText(this, "El correo electronico o la contraseña son incorrectos, por favor, vuelva a ingresar sus credenciales.", Toast.LENGTH_SHORT).show();
            }



            archivo.close();
            if(!flag)
                finishAffinity();
        } catch(IOException e){

            Toast.makeText(this, "El correo electronico o la contraseña son incorrectos, por favor, vuelva a ingresar sus credenciales.", Toast.LENGTH_SHORT).show();
            editTextCorreoElectronico.setText("");
            editTextContrasena.setText("");

        }

    }

}