package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Registro extends AppCompatActivity {

    //Declaracion variables de informacion
    EditText nombres, apellidos, edad, correoElectronicoR, contrasenaR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombres = findViewById(R.id.editTextNombresMISDATOS);
        apellidos = findViewById(R.id.editTextApellidosMISDATOS);
        edad = findViewById(R.id.editTextEdadMISDATOS);
        correoElectronicoR = findViewById(R.id.editTextCorreoElectronicoRMISDATOS);
        contrasenaR = findViewById(R.id.editTextContrasenaRMISDATOS);

    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View view) {
        Intent miIntent = new Intent(Registro.this, MainActivity.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void VerificarInformacionRegistro(View view) throws IOException {

        boolean flag = true;
        String mensajeError = "";

        if(this.nombres.getText().toString().equals("")) {
            mensajeError += "No ha ingresado nombres validos\n";
            flag = false;
        }
        if(this.apellidos.getText().toString().equals("")) {
            mensajeError += "No ha ingresado apellidos validos\n";
            flag = false;
        }
        if(this.edad.getText().toString().equals("") || Integer.parseInt(this.edad.getText().toString()) > 150) {
            mensajeError += "No ha ingresado una edad valida\n";
            flag = false;
        }
        if(!this.correoElectronicoR.getText().toString().contains("@") || this.correoElectronicoR.getText().toString().replaceAll("@","").equals("")){
            mensajeError += "No ha ingresado un correo electronico valido\n";
            flag = false;
        }
        if(this.contrasenaR.getText().toString().length() < 8){
            mensajeError += "Debe ingresar una contraseña de por lo menos 8 caracteres\n";
            flag = false;
        }

        if(flag)
            Registrar(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();

    }

    public void Registrar(View view) throws IOException {
        String nombres = this.nombres.getText().toString();
        String apellidos = this.apellidos.getText().toString();
        String edad = this.edad.getText().toString();
        String correoElectronicoR = this.correoElectronicoR.getText().toString();
        String contrasenaR = this.contrasenaR.getText().toString();
        String infoNuevoUsuario = "nombres: " + nombres + "; apellidos: " + apellidos + "; edad: " + edad + "; correoElectronico: " + correoElectronicoR.toLowerCase() + "; contrasena: " + contrasenaR + ";\n";

        try{

            InputStreamReader archivo = new InputStreamReader(openFileInput("InfoUsuariosFinancePal.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String contenido = "";

            while(linea != null){

                contenido += linea + "\n";
                linea = br.readLine();

            }
            if(!contenido.contains(correoElectronicoR)) {
                contenido += infoNuevoUsuario;
                OutputStreamWriter archivoNuevo = new OutputStreamWriter(openFileOutput("InfoUsuariosFinancePal.txt", Context.MODE_PRIVATE));
                archivoNuevo.write(contenido);
                archivoNuevo.flush();
                archivoNuevo.close();
                Toast.makeText(this, "Se ha registrado exitosamente.", Toast.LENGTH_SHORT).show();
                Intent miIntent = new Intent(this, MainActivity.class);
                startActivity(miIntent);
                finishAffinity();
            } else {
                Toast.makeText(this, "El correo electronico ingresado ya se encuentra registrado.", Toast.LENGTH_SHORT).show();
            }

            this.nombres.setText("");
            this.apellidos.setText("");
            this.edad.setText("");
            this.correoElectronicoR.setText("");
            this.contrasenaR.setText("");
            br.close();
            archivo.close();

        }catch(IOException e){
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("InfoUsuariosFinancePal.txt", Context.MODE_PRIVATE));
            archivo.write(infoNuevoUsuario);
            archivo.flush();
            archivo.close();
            Toast.makeText(this, "Se ha registrado exitosamente.", Toast.LENGTH_SHORT).show();
            Intent miIntent = new Intent(Registro.this, MainActivity.class);
            startActivity(miIntent);
            finishAffinity();
        }



    }

}