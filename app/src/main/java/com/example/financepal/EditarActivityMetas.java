package com.example.financepal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.entidades.MetasInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EditarActivityMetas extends AppCompatActivity {
    String correoElectronicoS;
    EditText txtNombreMeta, txtFechaMeta, txtMontoMeta;
    Button guardar, cancelar;
    FloatingActionButton fabEditar, fabElminar;


    boolean correcto;
    ImageView botonAtras;
    View recuadro;

    MetasInfo metaInfo;
    TextView titulo;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_meta);

        if (getIntent().getExtras() != null) {
            correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        }



        txtNombreMeta = findViewById(R.id.textoNombreMetaEditar);
        txtFechaMeta = findViewById(R.id.textoFechaMetaEditar);
        txtMontoMeta = findViewById(R.id.textoMontoMetaEditar);


        fabEditar = findViewById(R.id.fabEditarMeta);
        fabEditar.setVisibility(View.INVISIBLE);
        fabElminar=findViewById(R.id.fabEliminarMeta);
        fabElminar.setVisibility(View.INVISIBLE);

        guardar =findViewById(R.id.botonGuardarMetasEditar);
        configurarFechaEditText();
        cancelar = findViewById(R.id.botonCancelarMetasEditar);

        cancelar.setOnClickListener(view -> cambiarAMetasDeAhorro(view));
        titulo = findViewById(R.id.tituloSuperiorMetasDeAhorroEditar);
        titulo.setVisibility(View.INVISIBLE);

        recuadro = findViewById(R.id.recuadroSuperiorRegistroMetasDeAhorro2);
        recuadro.setVisibility(View.INVISIBLE);


        botonAtras= findViewById(R.id.botonAtrasRegistroMetasdeAhorro);

        botonAtras.setVisibility(View.INVISIBLE);

        if (savedInstanceState==null){
            Bundle extras = getIntent().getExtras();
            if (extras==null){
                id = Integer.parseInt(null);

            }
            else{
                id = extras.getInt("ID");

            }
        }
        else{
            id= (int) savedInstanceState.getSerializable("ID");
        }
        DbNombreMetas dbNombreMetas = new DbNombreMetas(EditarActivityMetas.this);
        metaInfo= dbNombreMetas.verMetas(correoElectronicoS, id);

        if(metaInfo!= null){
            txtNombreMeta.setText((metaInfo.getNombreMeta()));
            txtFechaMeta.setText(metaInfo.getFechaMeta());
            txtMontoMeta.setText(String.valueOf(metaInfo.getMontoMeta()));
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombreMeta.getText().toString().equals("") && !txtFechaMeta.getText().toString().equals("") && !txtMontoMeta.getText().toString().equals("")) {
                    // Validar la fecha ingresada
                    if (validarFecha(txtFechaMeta.getText().toString().trim())) {
                        correcto = dbNombreMetas.editarMeta(id, txtNombreMeta.getText().toString(), txtFechaMeta.getText().toString(), Integer.parseInt(txtMontoMeta.getText().toString()));

                        if (correcto) {
                            Toast.makeText(EditarActivityMetas.this, "META MODIFICADA", Toast.LENGTH_LONG).show();
                            verRegistroMeta();
                        } else {
                            Toast.makeText(EditarActivityMetas.this, "ERROR AL EDITAR MODIFICAR META", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(EditarActivityMetas.this, "Fecha no válida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditarActivityMetas.this, "DEBE LLENAR LOS CAMPOS", Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    private void configurarFechaEditText() {
        // Establecer el límite de caracteres para la fecha
        int maxLength = 7; // mm/aaaa
        txtFechaMeta.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        txtFechaMeta.setOnClickListener(view -> mostrarDialogo());
        // Agregar un TextWatcher a txtFechaMeta
        /*
        txtFechaMeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (count == 1 && start == 1) {
                    // Agregar automáticamente el '/' después del mes
                    txtFechaMeta.setText(new StringBuilder(charSequence).insert(2, "/").toString());
                    txtFechaMeta.setSelection(3); // Mover el cursor después del '/'
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

         */
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

    private void verRegistroMeta(){
        Intent intent = new Intent(this, VerMeta.class);
        intent.putExtra("ID", id);
        intent.putExtra("correoElectronico", correoElectronicoS);

        startActivity(intent);
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

                txtFechaMeta.setText(spinnerMes.getSelectedItem().toString() + "/" + spinnerYear.getSelectedItem().toString());

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
