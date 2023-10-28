package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CrearModificarIngresos extends AppCompatActivity {

    String correoElectronicoS;
    Button botonCrearGuardarIngreso;
    EditText editTextNombre, editTextMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_modificar_ingresos);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonCrearGuardarIngreso = findViewById(R.id.botonCrearGuardarCrearModificarIngresos);
        botonCrearGuardarIngreso.setText(getIntent().getStringExtra("funcionBoton"));

        editTextNombre = findViewById(R.id.editTextNombreCambiarModificarIngresos);
        editTextMonto = findViewById(R.id.editTextMontoCambiarModificarIngresos);

    }

    public void cambiarAIngresos(View view){

        Intent myIntent = new Intent(this, Ingresos.class);
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(myIntent);
        finishAffinity();

    }

    public boolean verificarInformación(View view){

        String mensaje = "";

        if(editTextNombre.getText().toString().equals(""))
            mensaje += "Nombre o razón invalida";

        if(Integer.parseInt(editTextMonto.getText().toString()) <= 0)
            mensaje += "Monto invalido";

        if (mensaje.equals("")) {

            return true;

        } else {

            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            return false;

        }
    }

}