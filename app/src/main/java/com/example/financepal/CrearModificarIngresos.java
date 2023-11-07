package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financepal.db.DbIngresos;
import com.example.financepal.entidades.Ingreso;
import java.io.IOException;
import java.util.List;

public class CrearModificarIngresos extends AppCompatActivity {

    String correoElectronicoS;

    Ingreso infoIngreso;
    long id;
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

    public void cambiarAIngresos(View view) {

        Intent myIntent = new Intent(this, Ingresos.class);
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(myIntent);
        finishAffinity();

    }

    public boolean verificarInformacion() {

        String mensaje = "";

        if(editTextNombre.getText().toString().trim().equals(""))
            mensaje += "Nombre o razón invalida\n";

        if(editTextMonto.getText().toString().trim().equals("") || Integer.parseInt(editTextMonto.getText().toString().trim()) <= 0) {
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

    public void crearIngreso(View view) {

        if(verificarInformacion()){

            DbIngresos dbIngresos = new DbIngresos(this);

            List<String> nombresMetas = dbIngresos.obtenerNombresIngresos(correoElectronicoS);

            if(!verificarRepeticion(nombresMetas)) {

                dbIngresos.insertarIngreso(correoElectronicoS, editTextNombre.getText().toString().trim(), editTextMonto.getText().toString().trim());
                Toast.makeText(this, "Se ha creado el ingreso exitosamente.", Toast.LENGTH_SHORT).show();
                cambiarAIngresos(view);

            } else {

                Toast.makeText(this, "Ya existe un ingreso con el nombre indicado", Toast.LENGTH_SHORT).show();

            }



        }

    }

    public void establecerEditText() throws IOException {

        if(getIntent().getStringExtra("funcionBoton").equals("Guardar")){

            id = getIntent().getExtras().getLong("id");

            DbIngresos dbIngresos = new DbIngresos(this);

            infoIngreso = dbIngresos.verIngreso(id);

            editTextNombre.setText(infoIngreso.getNombre());
            editTextMonto.setText(infoIngreso.getMonto());

        }

    }

    public void modificarIngreso(View view) {

        if(verificarInformacion()){

            String nombreEdit = editTextNombre.getText().toString().trim();
            String montoEdit = editTextMonto.getText().toString().trim();

            if(infoIngreso.getNombre().equals(nombreEdit) && infoIngreso.getMonto().equals(montoEdit)) {

                Toast.makeText(this, "No ha modificado ningún dato.", Toast.LENGTH_SHORT).show();

            } else {

                DbIngresos dbIngresos = new DbIngresos(this);

                if(verificarRepeticion(dbIngresos.obtenerNombresIngresos(correoElectronicoS)) && !nombreEdit.equalsIgnoreCase(infoIngreso.getNombre())) {

                    Toast.makeText(this, "Ya existe un ingreso con el nombre indicado.", Toast.LENGTH_SHORT).show();

                } else {

                    boolean correcto = dbIngresos.editarIngreso(id, nombreEdit, montoEdit);

                    if (correcto) {
                        Toast.makeText(this, "Se ha modificado el ingreso.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error al modificar el ingreso.", Toast.LENGTH_SHORT).show();
                    }

                    cambiarAIngresos(view);

                }

            }

        }

    }

    public void detectarIntencionBoton(View view) {

        if(botonCrearGuardarIngreso.getText().toString().equals("Crear")){
            crearIngreso(view);
        } else {
            modificarIngreso(view);
        }

    }

    public boolean verificarRepeticion(List<String> nombres) {

        boolean repeticion = false;

        for(String nombre : nombres){

            repeticion = nombre.equalsIgnoreCase(editTextNombre.getText().toString().trim());

            if(repeticion)
                break;

        }

        return repeticion;

    }

}