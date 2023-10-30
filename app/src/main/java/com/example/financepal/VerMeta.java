package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.entidades.MetasInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerMeta extends AppCompatActivity {
    String correoElectronicoS;
    EditText txtNombreMeta, txtFechaMeta, txtMontoMeta;
    Button guardar;

    FloatingActionButton fabEditar;

    ImageView botonAtras;

    MetasInfo metaInfo;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_meta);



        txtNombreMeta = findViewById(R.id.textoNombreMetaEditar);
        txtFechaMeta = findViewById(R.id.textoFechaMetaEditar);
        txtMontoMeta = findViewById(R.id.textoMontoMetaEditar);

        fabEditar = findViewById(R.id.fabEditarMeta);

        guardar =findViewById(R.id.botonGuardarMetasEditar);

        botonAtras= findViewById(R.id.botonAtrasRegistroMetasdeAhorro);

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarAMetasDeAhorro(view);
            }
        });

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( VerMeta.this, EditarActivityMetas.class);
                intent.putExtra("ID" , id);
                startActivity(intent);
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
        DbNombreMetas dbNombreMetas = new DbNombreMetas(VerMeta.this);
        metaInfo= dbNombreMetas.verMetas(id);

        if(metaInfo!= null){
            txtNombreMeta.setText((metaInfo.getNombreMeta()));
            txtFechaMeta.setText(metaInfo.getFechaMeta());
            txtMontoMeta.setText(String.valueOf(metaInfo.getMontoMeta()));
            guardar.setVisibility(View.INVISIBLE);
            txtNombreMeta.setInputType(InputType.TYPE_CLASS_TEXT);
            txtFechaMeta.setInputType(InputType.TYPE_CLASS_DATETIME);
            txtMontoMeta.setInputType(InputType.TYPE_CLASS_NUMBER);
        }




    }
    public void cambiarAMetasDeAhorro(View view) {

        Intent miIntent = new Intent(this, MetasDeAhorro.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
}