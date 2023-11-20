package com.example.financepal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    FloatingActionButton fabEditar, fabElminar;

    ImageView botonAtras;

    MetasInfo metaInfo;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_meta);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");




        txtNombreMeta = findViewById(R.id.textoNombreMetaEditar);
        txtFechaMeta = findViewById(R.id.textoFechaMetaEditar);
        txtMontoMeta = findViewById(R.id.textoMontoMetaEditar);

        fabEditar = findViewById(R.id.fabEditarMeta);
        fabElminar=findViewById(R.id.fabEliminarMeta);

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
                intent.putExtra("correoElectronico", correoElectronicoS);

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
        metaInfo= dbNombreMetas.verMetas(correoElectronicoS,id);

        if(metaInfo!= null){
            txtNombreMeta.setText((metaInfo.getNombreMeta()));
            txtFechaMeta.setText(metaInfo.getFechaMeta());
            txtMontoMeta.setText(String.valueOf(metaInfo.getMontoMeta()));
            guardar.setVisibility(View.INVISIBLE);
            txtNombreMeta.setInputType(InputType.TYPE_NULL);
            txtFechaMeta.setInputType(InputType.TYPE_NULL);
            txtMontoMeta.setInputType(InputType.TYPE_NULL);
        }


        fabElminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder( VerMeta.this);
                builder.setMessage("Desea eliminar esta meta?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dbNombreMetas.elimnarMeta(id)){
                            listaMetas();
                            cambiarAMetasDeAhorro(view);

                        }


                    }
                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });




    }
    public void cambiarAMetasDeAhorro(View view) {

        Intent miIntent = new Intent(this, MetasDeAhorro.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
    private void listaMetas(){
        Intent intent = new Intent(this, MetasDeAhorro.class);
    }
}