package com.example.financepal;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.entidades.MetasInfo;

public class EditarActivityMetas extends AppCompatActivity {
    String correoElectronicoS;
    EditText txtNombreMeta, txtFechaMeta, txtMontoMeta;
    Button guardar;

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

        guardar =findViewById(R.id.botonGuardarMetasEditar);

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
