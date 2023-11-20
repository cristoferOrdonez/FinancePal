package com.example.financepal;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financepal.MetasDeAhorro;
import com.example.financepal.R;
import com.example.financepal.db.DbNombreMetas;

import android.content.Intent;


public class ingresoInformacionMetasDeAhorro extends AppCompatActivity {
    String correoElectronicoS;
    EditText nombreMeta, fechaMeta, montoMeta;
    Button botonGuardarMeta;
    ImageView botonAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_informacion_metas_de_ahorro);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        nombreMeta = findViewById(R.id.textoNombreMetaEditar);
        montoMeta = findViewById(R.id.textoMontoMetaEditar);
        fechaMeta = findViewById(R.id.textoFechaMetaEditar);
        botonGuardarMeta = findViewById(R.id.botonGuardarMetasEditar);

        // Establecer el límite de caracteres para la fecha
        int maxLength = 7; // mm/aaaa
        fechaMeta.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        // Agregar un TextWatcher a fechaMeta
        fechaMeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (count == 1 && start == 1) {
                    // Agregar automáticamente el '/' después del mes
                    fechaMeta.setText(new StringBuilder(charSequence).insert(2, "/").toString());
                    fechaMeta.setSelection(3); // Mover el cursor después del '/'
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        botonGuardarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verificar que los campos no estén vacíos
                if (camposLlenos()) {
                    // Validar la fecha ingresada
                    if (validarFecha(fechaMeta.getText().toString().trim())) {
                        DbNombreMetas dbNombreMetas = new DbNombreMetas(ingresoInformacionMetasDeAhorro.this);
                        long id = dbNombreMetas.insertarMeta(correoElectronicoS, nombreMeta.getText().toString(), fechaMeta.getText().toString(), Integer.parseInt(montoMeta.getText().toString()));
                        //Se inserta la info a la tabla
                        limpiar();
                        cambiarAMetasDeAhorro(view);
                    } else {
                        Toast.makeText(ingresoInformacionMetasDeAhorro.this, "Fecha no válida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Mostrar un mensaje al usuario indicando que todos los campos son obligatorios
                    Toast.makeText(ingresoInformacionMetasDeAhorro.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botonAtras = findViewById(R.id.botonAtrasRegistroMetasdeAhorro);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAMetasDeAhorro(view);
            }
        });
    }

    private boolean camposLlenos() {
        // Verificar que los campos no estén vacíos ni sean espacios en blanco
        return !nombreMeta.getText().toString().trim().isEmpty() &&
                !fechaMeta.getText().toString().trim().isEmpty() &&
                !montoMeta.getText().toString().trim().isEmpty();
    }

    private void limpiar() {
        nombreMeta.setText("");
        montoMeta.setText("");
        fechaMeta.setText("");
    }

    private boolean validarFecha(String fecha) {
        // Validar que la fecha tenga el formato mm/aaaa
        return fecha.matches("(0[1-9]|1[0-2])/\\d{4}");
    }

    public void cambiarAMetasDeAhorro(View view) {

        Intent miIntent = new Intent(this, MetasDeAhorro.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
}
