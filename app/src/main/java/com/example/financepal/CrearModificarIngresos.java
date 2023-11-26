package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHistorico;
import com.example.financepal.db.DbIngresos;
import com.example.financepal.entidades.EntidadHistorico;
import com.example.financepal.entidades.Ingreso;
import java.io.IOException;
import java.util.List;

public class CrearModificarIngresos extends AppCompatActivity {

    String correoElectronicoS, funcionBoton;

    Ingreso infoIngreso;
    long id;
    Button botonCrearGuardarIngreso;
    EditText editTextNombre, editTextMonto;
    TextView nombre, monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_modificar_ingresos);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonCrearGuardarIngreso = findViewById(R.id.botonCrearGuardarCrearModificarIngresos);
        nombre = findViewById(R.id.textViewNombreIngresos);
        monto = findViewById(R.id.textViewMontoIngresos);

        funcionBoton = getIntent().getStringExtra("funcionBoton");

        botonCrearGuardarIngreso.setText(funcionBoton);

        if(funcionBoton.equals("Crear")){

            nombre.setVisibility(View.INVISIBLE);
            monto.setVisibility(View.INVISIBLE);

        }

        editTextNombre = findViewById(R.id.editTextNombreCrearModificarIngresos);
        editTextMonto = findViewById(R.id.editTextMontoCrearModificarIngresos);
        editTextMonto.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editTextNombre.setFilters(new InputFilter[]{new InputFilter.LengthFilter((22))});


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

        if(editTextMonto.getText().toString().trim().equals("") || Long.parseLong(editTextMonto.getText().toString().trim()) <= 0) {
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

                dbIngresos.insertarIngreso(correoElectronicoS, editTextNombre.getText().toString().trim(), Long.parseLong(editTextMonto.getText().toString()));
                DbHistorico dbHistorico = new DbHistorico(this);
                dbHistorico.actualizarHistorico(correoElectronicoS, dbIngresos.obtenerIngresosTotales(correoElectronicoS), new DbGastos(this).mostrarGastosTotales(correoElectronicoS));

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
            long montoEdit = Long.parseLong(editTextMonto.getText().toString());

            if(infoIngreso.getNombre().equals(nombreEdit) && infoIngreso.getMonto().equals(montoEdit)) {

                Toast.makeText(this, "No ha modificado ningún dato.", Toast.LENGTH_SHORT).show();

            } else {

                DbIngresos dbIngresos = new DbIngresos(this);

                if(verificarRepeticion(dbIngresos.obtenerNombresIngresos(correoElectronicoS)) && !nombreEdit.equalsIgnoreCase(infoIngreso.getNombre())) {

                    Toast.makeText(this, "Ya existe un ingreso con el nombre indicado.", Toast.LENGTH_SHORT).show();

                } else {

                    boolean correcto = dbIngresos.editarIngreso(id, nombreEdit, montoEdit);
                    DbHistorico dbHistorico = new DbHistorico(this);
                    dbHistorico.actualizarHistorico(correoElectronicoS, dbIngresos.obtenerIngresosTotales(correoElectronicoS), new DbGastos(this).mostrarGastosTotales(correoElectronicoS));

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

    @Override
    public void onBackPressed(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == event.KEYCODE_BACK){

            cambiarAIngresos(new View(this));

        }

        return super.onKeyDown(keyCode, event);

    }

}