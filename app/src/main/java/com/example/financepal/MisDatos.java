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

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

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

        } catch (Exception e) {
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

    public void verificarNuevosDatos(View view) throws IOException {

        boolean flag = true;
        String mensajeError = "";

        if(editTextNombres.getText().toString().equals("")) {
            mensajeError += "No ha ingresado nombres validos\n";
            flag = false;
        }
        if(editTextApellidos.getText().toString().equals("")) {
            mensajeError += "No ha ingresado apellidos validos\n";
            flag = false;
        }
        if(editTextEdad.getText().toString().equals("") || Integer.parseInt(editTextEdad.getText().toString()) > 150) {
            mensajeError += "No ha ingresado una edad valida\n";
            flag = false;
        }
        if(!editTextCorreoElectronico.getText().toString().contains("@") || editTextCorreoElectronico.getText().toString().replaceAll("@","").equals("")){
            mensajeError += "No ha ingresado un correo electronico valido\n";
            flag = false;
        }
        if(editTextContrasena.getText().toString().length() < 8){
            mensajeError += "Debe ingresar una contraseña de por lo menos 8 caracteres\n";
            flag = false;
        }

        if(flag)
            actualizarInformacion(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();

    }

    public void actualizarInformacion(View view) throws IOException {

        String nombres = editTextNombres.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        String edad = editTextEdad.getText().toString();
        String correoElectronico = editTextCorreoElectronico.getText().toString();
        String contrasena = editTextContrasena.getText().toString();
        String infoNuevoUsuario = "nombres: " + nombres + "; apellidos: " + apellidos + "; edad: " + edad + "; correoElectronico: " + correoElectronico.toLowerCase() + "; contrasena: " + contrasena + ";";

        if(infoNuevoUsuario.equals(infoUsuario)){
            Toast.makeText(this, "No ha cambiado ningun dato.", Toast.LENGTH_SHORT).show();
        } else {
            InputStreamReader archivo = new InputStreamReader(openFileInput("InfoUsuariosFinancePal.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String contenido = "";

            while(linea != null){

                contenido += linea + "\n";
                linea = br.readLine();

            }

            if(contenido.contains(correoElectronico.toLowerCase()) && !correoElectronico.toLowerCase().equals(correoElectronicoS)){

                editTextCorreoElectronico.setText(correoElectronicoS);
                Toast.makeText(this, "El correo electronico ingresado ya se encuentra registrado", Toast.LENGTH_SHORT).show();

            } else {

                OutputStreamWriter archivoNuevo = new OutputStreamWriter(openFileOutput("InfoUsuariosFinancePal.txt", Context.MODE_PRIVATE));
                archivoNuevo.write(contenido.replaceAll(infoUsuario, infoNuevoUsuario));
                archivoNuevo.flush();
                archivoNuevo.close();
                Intent miIntent = new Intent(this, PantallaPrincipal.class);
                miIntent.putExtra("correoElectronico", correoElectronicoS);
                startActivity(miIntent);
                Toast.makeText(this, "Su información ha sido actualizada correctamente.", Toast.LENGTH_SHORT).show();
                finishAffinity();

            }
            br.close();
            archivo.close();
        }

    }



}