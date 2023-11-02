package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financepal.db.DbUsuarios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

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

    public void cambiarAAcceso(View view) {
        Intent miIntent = new Intent(this, MainActivity.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void VerificarInformacionRegistro(View view) throws IOException {

        boolean flag = true;
        String mensajeError = "";

        if(this.nombres.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombres validos\n";
            flag = false;
        }
        if(this.apellidos.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado apellidos validos\n";
            flag = false;
        }
        if(this.edad.getText().toString().trim().equals("") || Integer.parseInt(this.edad.getText().toString().trim()) > 150) {
            mensajeError += "No ha ingresado una edad valida\n";
            flag = false;
        }
        if(!this.correoElectronicoR.getText().toString().contains("@") || this.correoElectronicoR.getText().toString().replaceAll("@","").trim().equals("") || correoElectronicoR.getText().toString().contains(" ")){
            mensajeError += "No ha ingresado un correo electronico valido\n";
            flag = false;
        }
        if(this.contrasenaR.getText().toString().length() < 8){
            mensajeError += "Debe ingresar una contraseña de por lo menos 8 caracteres\n";
            flag = false;
        }
        if(this.contrasenaR.getText().toString().contains(" ")){
            mensajeError += "La contraseña no puede contener espacios en blanco\n";
            flag = false;
        }

        if(flag)
            Registrar(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();

    }

    public void Registrar(View view) throws IOException {
        String nombres = this.nombres.getText().toString().trim();
        String apellidos = this.apellidos.getText().toString().trim();
        String edad = this.edad.getText().toString().trim();
        String correoElectronicoR = this.correoElectronicoR.getText().toString();
        String contrasenaR = this.contrasenaR.getText().toString();

        DbUsuarios dbUsuarios = new DbUsuarios(this);

        if(!verificarRepeticion(dbUsuarios.obtenerCorreosElectronicos())){

            long i = dbUsuarios.agregarUsuario(nombres, apellidos, edad, correoElectronicoR.toLowerCase(), contrasenaR);
            Toast.makeText(this, "Se ha registrado con exitosamente.", Toast.LENGTH_SHORT).show();

            cambiarAAcceso(view);

        } else {

            Toast.makeText(this, "El correo electronico ingresado ya se encuentra registrado", Toast.LENGTH_SHORT).show();

        }

        /*

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

         */

    }

    public boolean verificarRepeticion(List<String> correos) {

        boolean repeticion = false;

        for(String correo : correos){

            repeticion = correo.equalsIgnoreCase(correoElectronicoR.getText().toString().trim());

            if(repeticion)
                break;

        }

        return repeticion;

    }

}