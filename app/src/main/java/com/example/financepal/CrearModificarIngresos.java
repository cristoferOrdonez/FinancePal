package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import java.io.OutputStreamWriter;

public class CrearModificarIngresos extends AppCompatActivity {

    String correoElectronicoS, nombre, infoIngreso;
    Button botonCrearGuardarIngreso;
    EditText editTextNombre, editTextMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_modificar_ingresos);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonCrearGuardarIngreso = findViewById(R.id.botonCrearGuardarCrearModificarIngresos);
        botonCrearGuardarIngreso.setText(getIntent().getStringExtra("funcionBoton"));

        editTextNombre = findViewById(R.id.editTextNombreCrearModificarIngresos);
        editTextMonto = findViewById(R.id.editTextMontoCrearModificarIngresos);


        try {
            establecerEditText();
        } catch (IOException e) {
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }

    }

    public void cambiarAIngresos(View view){

        Intent myIntent = new Intent(this, Ingresos.class);
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(myIntent);
        finishAffinity();

    }

    public boolean verificarInformacion(){

        String mensaje = "";

        if(editTextNombre.getText().toString().equals(""))
            mensaje += "Nombre o razón invalida\n";

        if(editTextMonto.getText().toString().equals("") || Integer.parseInt(editTextMonto.getText().toString()) <= 0) {
            mensaje += "Monto invalido";
            editTextMonto.setText("");
        }

        if (mensaje.equals("")) {

            return true;

        } else {

            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            return false;

        }
    }

    public void crearIngreso(View view) throws IOException {

        if(verificarInformacion()){

            String nombreEdit = editTextNombre.getText().toString();
            int montoEdit = Integer.parseInt(editTextMonto.getText().toString());
            String infoIngreso = "nombre: " + nombreEdit + "; monto: " + montoEdit + ";\n";

            try{
                InputStreamReader archivo = new InputStreamReader(openFileInput(correoElectronicoS + "_INGRESOS.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String contenido = "";

                while (linea != null){

                    contenido += linea + "\n";
                    linea = br.readLine();

                }

                if(!contenido.contains(nombreEdit)) {
                    contenido += infoIngreso;
                    OutputStreamWriter archivoNuevo = new OutputStreamWriter(openFileOutput(correoElectronicoS + "_INGRESOS.txt", Context.MODE_PRIVATE));
                    archivoNuevo.write(contenido);
                    archivoNuevo.flush();
                    archivoNuevo.close();
                    Toast.makeText(this, "Se ha creado exitosamente el ingreso.", Toast.LENGTH_SHORT).show();
                    cambiarAIngresos(view);
                } else {
                    Toast.makeText(this, "Ya existe un ingreso con el nombre indicado.", Toast.LENGTH_SHORT).show();
                }

                editTextNombre.setText("");
                editTextMonto.setText("");
                br.close();
                archivo.close();

            }catch(IOException e){

                OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(correoElectronicoS + "_INGRESOS.txt", Context.MODE_PRIVATE));
                archivo.write(infoIngreso);
                archivo.flush();
                archivo.close();
                Toast.makeText(this, "Se ha creado exitosamente el ingreso.", Toast.LENGTH_SHORT).show();
                cambiarAIngresos(view);

            }

        }

    }

    public void establecerEditText() throws IOException {

        if(getIntent().getStringExtra("funcionBoton").equals("Guardar")){

            nombre = getIntent().getStringExtra("nombre");

            InputStreamReader archivo = new InputStreamReader(openFileInput(correoElectronicoS + "_INGRESOS.txt"));
            BufferedReader br = new BufferedReader(archivo);

            String nombreAr;

            String linea = br.readLine();

            while(linea != null){

                nombreAr = linea.substring(8, linea.indexOf("; monto"));

                if(nombreAr.equals(nombre)) {

                    infoIngreso = linea;

                    String monto = linea.substring(linea.indexOf("monto: ") + 7,linea.length() - 1);



                    editTextNombre.setText(nombre);
                    editTextMonto.setText(monto);

                    break;

                }

                linea = br.readLine();

            }

            archivo.close();
            br.close();

        }

    }

    public void modificarIngreso(View view) throws IOException {

        if(verificarInformacion()){

            String nombreEdit = editTextNombre.getText().toString();
            String montoEdit = editTextMonto.getText().toString();

            String infoNuevoIngreso = "nombre: " + nombreEdit + "; monto: " + montoEdit + ";";

            if(infoNuevoIngreso.equals(infoIngreso)){

                Toast.makeText(this, "No ha cambiado ningún dato.", Toast.LENGTH_SHORT).show();

            } else {

                InputStreamReader archivo = new InputStreamReader(openFileInput(correoElectronicoS + "_INGRESOS.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String contenido = "";

                while(linea != null){

                    contenido += linea + "\n";
                    linea = br.readLine();

                }

                if(contenido.contains(nombreEdit) && !nombreEdit.equals(nombre)){

                    editTextNombre.setText(nombre);
                    Toast.makeText(this, "Ya existe un ingreso con el nombre indicado", Toast.LENGTH_SHORT).show();

                } else {

                    OutputStreamWriter archivoNuevo = new OutputStreamWriter(openFileOutput(correoElectronicoS + "_INGRESOS.txt", Context.MODE_PRIVATE));
                    archivoNuevo.write(contenido.replaceAll(infoIngreso, infoNuevoIngreso));
                    archivoNuevo.flush();
                    archivoNuevo.close();
                    Toast.makeText(this, "Se ha modificado el ingreso correctamente.", Toast.LENGTH_SHORT).show();
                    editTextNombre.setText("");
                    editTextMonto.setText("");
                    cambiarAIngresos(view);

                }
                br.close();
                archivo.close();

            }

        }

    }

    public void detectarIntencionBoton(View view) throws IOException {

        if(botonCrearGuardarIngreso.getText().toString().equals("Crear")){
            crearIngreso(view);
        } else {
            modificarIngreso(view);
        }

    }

}