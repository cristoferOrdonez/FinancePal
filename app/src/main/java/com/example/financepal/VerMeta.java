package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.entidades.MetasInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerMeta extends AppCompatActivity {
    String correoElectronicoS;
    EditText txtNombreMeta, txtFechaMeta, txtMontoMeta;
    Button guardar, cancelar;

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
        cancelar = findViewById(R.id.botonCancelarMetasEditar);

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
                finishAffinity();
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
            cancelar.setVisibility(View.INVISIBLE);
            txtNombreMeta.setInputType(InputType.TYPE_NULL);
            txtFechaMeta.setInputType(InputType.TYPE_NULL);
            txtMontoMeta.setInputType(InputType.TYPE_NULL);
        }


        fabElminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(VerMeta.this, R.style.AlertDialogCustom));

                SpannableString message = new SpannableString("¿Desea eliminar esta meta?");
                message.setSpan(new ForegroundColorSpan(Color.WHITE), 0, message.length(), 0);

                SpannableString afirmacion = new SpannableString("Si");
                afirmacion.setSpan(new ForegroundColorSpan(Color.WHITE), 0, afirmacion.length(), 0);

                SpannableString negacion = new SpannableString("No");
                negacion.setSpan(new ForegroundColorSpan(Color.WHITE), 0, negacion.length(), 0);
                builder.setMessage(message).setPositiveButton(afirmacion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dbNombreMetas.elimnarMeta(id)){
                            Toast.makeText(VerMeta.this, "Se ha eliminado la meta exitosamente.", Toast.LENGTH_SHORT).show();
                            listaMetas();
                            cambiarAMetasDeAhorro(view);

                        }


                    }
                })
                        .setNegativeButton(negacion, new DialogInterface.OnClickListener() {
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

