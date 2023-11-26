package com.example.financepal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.financepal.R;
import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.entidades.UsuarioCategoriasGasto;

import android.content.Intent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class ingresoInformacionMetasDeAhorro extends AppCompatActivity {
    String correoElectronicoS;
    EditText nombreMeta, fechaMeta, montoMeta;
    Button botonGuardarMeta;
    Button botonAtras;

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

        fechaMeta.setOnClickListener(view -> mostrarDialogo());

/*
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

 */

        botonGuardarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verificar que los campos no estén vacíos
                if (camposLlenos()) {
                    // Validar la fecha ingresada
                    if (validarFecha(fechaMeta.getText().toString().trim())) {
                        DbNombreMetas dbNombreMetas = new DbNombreMetas(ingresoInformacionMetasDeAhorro.this);
                        long id = dbNombreMetas.insertarMeta(correoElectronicoS, nombreMeta.getText().toString(), fechaMeta.getText().toString(), Long.parseLong(montoMeta.getText().toString()));
                        Toast.makeText(ingresoInformacionMetasDeAhorro.this, "Se ha creado la meta.", Toast.LENGTH_SHORT).show();
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

    public List<String> llenarMeses(){

        List<String> list = new ArrayList<>();


        for(int i = 1; i < 13; i++)
            if(String.valueOf(i).length() == 1)
                list.add("0" + i);
            else
                list.add(String.valueOf(i));

        return list;

    }

    public List<String> llenarYears(){

        List<String> list = new ArrayList<>();

        int inicio = new GregorianCalendar().get(Calendar.YEAR);

        for(int i = inicio; i < inicio + 101; i++)
            list.add(String.valueOf(i));

        return list;

    }

    public void mostrarDialogo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadro_dialogo_fecha_metas, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();

        Spinner spinnerMes = view.findViewById(R.id.spinnerMes);
        Spinner spinnerYear = view.findViewById(R.id.spinnerYear);

        List<String> listMeses = llenarMeses();
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listMeses);
        spinnerMes.setAdapter(arrayAdapter1);
        List<String> listYear = llenarYears();
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listYear);
        spinnerYear.setAdapter(arrayAdapter2);

        dialog.show();

        Button cancelar = view.findViewById(R.id.buttonCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        Button aceptar = view.findViewById(R.id.buttonAceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fechaMeta.setText(spinnerMes.getSelectedItem().toString() + "/" + spinnerYear.getSelectedItem().toString());

                dialog.dismiss();

            }
        });


    }

    @Override
    public void onBackPressed(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == event.KEYCODE_BACK){

            cambiarAMetasDeAhorro(new View(this));
        }

        return super.onKeyDown(keyCode, event);

    }

}
