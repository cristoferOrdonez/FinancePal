package com.example.financepal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.entidades.MetasInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarActivityMetas extends AppCompatActivity {
    String correoElectronicoS;
    EditText txtNombreMeta, txtFechaMeta, txtMontoMeta;
    Button guardar;
    FloatingActionButton fabEditar, fabElminar;


    boolean correcto;
    ImageView botonAtras;

    MetasInfo metaInfo;
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


        botonAtras= findViewById(R.id.botonAtrasRegistroMetasdeAhorro);

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAMetasDeAhorro(view);
            }
        });

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

                if(!txtNombreMeta.getText().toString().equals("") && !txtFechaMeta.getText().toString().equals("") && !txtMontoMeta.getText().toString().equals("")){
                    correcto = dbNombreMetas.editarMeta(id, txtNombreMeta.getText().toString(), txtFechaMeta.getText().toString(), Integer.parseInt(txtMontoMeta.getText().toString()));

                    if(correcto){
                        Toast.makeText(EditarActivityMetas.this, "META MODIFICADA", Toast.LENGTH_LONG).show();
                        verRegistroMeta();
                    }
                    else{
                        Toast.makeText(EditarActivityMetas.this, "ERROR AL EDITAR MODIFICAR META", Toast.LENGTH_LONG).show();

                    }
                }
                else{
                    Toast.makeText(EditarActivityMetas.this, "DEBE LLENAR LOS CAMPOS", Toast.LENGTH_LONG).show();

                }
            }
        });




    }

    private void configurarFechaEditText() {
        // Establecer el límite de caracteres para la fecha
        int maxLength = 7; // mm/aaaa
        txtFechaMeta.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        // Agregar un TextWatcher a txtFechaMeta
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

    }
}
