package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.financepal.db.DbNombreMetas;

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


        botonGuardarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DbNombreMetas dbNombreMetas = new DbNombreMetas(ingresoInformacionMetasDeAhorro.this);
                long id = dbNombreMetas.insertarMeta(correoElectronicoS, nombreMeta.getText().toString(), fechaMeta.getText().toString(), Integer.parseInt(montoMeta.getText().toString()));
                //Se inserta la info a la tabla
                limpiar();

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

    private void limpiar() {
        nombreMeta.setText("");
        montoMeta.setText("");
        fechaMeta.setText("");
    }

    public void cambiarAMetasDeAhorro(View view) {

        Intent miIntent = new Intent(this, MetasDeAhorro.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
}
