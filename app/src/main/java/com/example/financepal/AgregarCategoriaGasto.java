package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financepal.db.DbGastos;
import com.example.financepal.entidades.UsuarioCategoriasGasto;

public class AgregarCategoriaGasto extends AppCompatActivity {
    String correoElectronicoS;
    private DbGastos db;
    EditText EditTextNombreCatGastos;
    EditText EditTextDescCatGastos;
    Button BotonCrearGuardarCatGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        setContentView(R.layout.activity_agregar_categoria_gasto);

        EditTextDescCatGastos=findViewById(R.id.editTextDescCatGastos);
        EditTextNombreCatGastos=findViewById(R.id.editTextNombreCatGastos);
        db = new DbGastos(this);

        BotonCrearGuardarCatGastos = findViewById(R.id.botonCrearGuardarCatGastos);
        //BotonCrearGuardarCatGastos.setText(getIntent().getStringExtra("funcionBoton"));
    }

    public void detectarIntencionBoton(View view) {

        agregarCatGasto(view); //ELIMINAR

        /*if(BotonCrearGuardarCatGastos.getText().toString().equals("Crear")){
            agregarCatGasto(view);
        } else {
            //modificarCatGasto(view);
        }*/

    }

    private void agregarCatGasto(View view) {
        boolean res;
        if(EditTextNombreCatGastos.getText().toString().isEmpty()||EditTextDescCatGastos.getText().toString().isEmpty()){
            Toast.makeText(this,"Por favor llene todos los campos",Toast.LENGTH_LONG).show();
        }
        else{
            UsuarioCategoriasGasto usuario = new UsuarioCategoriasGasto();
            usuario.setCorreocatgasto(correoElectronicoS);
            usuario.setNombrecatgasto(EditTextNombreCatGastos.getText().toString());
            usuario.setDesccatgasto(EditTextDescCatGastos.getText().toString());

            res= db.insertarNuevaCategoria(usuario);
            if (res) {
                Toast.makeText(this, "Se ha agregado la categoría.", Toast.LENGTH_SHORT).show();
                cambiaraAtras(view);
            } else {
                Toast.makeText(this, "Error al agregar.", Toast.LENGTH_SHORT).show();
                EditTextDescCatGastos.setText("");
                EditTextNombreCatGastos.setText("");
            }
        }
    }

    public void cambiaraAtras(View view){
        Intent miIntent = new Intent(this, CategoriasGasto.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
}