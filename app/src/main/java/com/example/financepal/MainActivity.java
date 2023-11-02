package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financepal.db.DbUsuarios;
import com.example.financepal.entidades.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Declaracion de los editText
    private EditText editTextCorreoElectronico, editTextContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de editTexts
        editTextCorreoElectronico = findViewById((R.id.editTextCorreoElectronico));
        editTextContrasena = findViewById(R.id.editTextTextContrasena);

    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void registro(View view) {
        Intent miIntent = new Intent(this, Registro.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void acceder(View view) {

        DbUsuarios dbUsuarios = new DbUsuarios(this);

        if(verificarExistencia(dbUsuarios.obtenerCorreosElectronicos())){

            Usuario usuario = dbUsuarios.verUsuario(editTextCorreoElectronico.getText().toString().toLowerCase());

            if(editTextContrasena.getText().toString().equals(usuario.contrasena)){

                Intent intent = new Intent(this, PantallaPrincipal.class);
                intent.putExtra("correoElectronico", usuario.correoElectronico);
                startActivity(intent);
                finishAffinity();

            } else {

                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

            }


        } else {

            Toast.makeText(this, "El correo electronico ingresado no se encuentra registrado.", Toast.LENGTH_SHORT).show();
            editTextCorreoElectronico.setText("");
            editTextContrasena.setText("");

        }

        /*

        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("InfoUsuariosFinancePal.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();

            boolean flag = true;

            while (linea != null) {

                String credencialesS = linea.substring(linea.indexOf("correoElectronico:"));
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

         */

    }

    public boolean verificarExistencia(List<String> correos){

        boolean existencia = false;

        for(String correo : correos){

            existencia = correo.equalsIgnoreCase(editTextCorreoElectronico.getText().toString());

            if(existencia)
                break;

        }

        return existencia;

    }

}