package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHistorico;
import com.example.financepal.db.DbIngresos;
import com.example.financepal.entidades.UsuarioCategoriasGasto;
import com.example.financepal.entidades.UsuarioGastos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        BotonCrearGuardarCatGastos.setText(getIntent().getStringExtra("funcionBoton"));

        try {
            establecerEditText();
        } catch (IOException e) {
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }

    public void detectarIntencionBoton(View view) {
        if(BotonCrearGuardarCatGastos.getText().toString().equals("Crear")){
            agregarCatGasto(view);
        } else {
            modificarCatGasto(view);
        }

    }

    public void establecerEditText() throws IOException {

        if(getIntent().getStringExtra("funcionBoton").equals("Guardar")){

            int id = getIntent().getExtras().getInt("id");
            UsuarioCategoriasGasto infousuario = db.buscarCategGasto(id);

            EditTextNombreCatGastos.setText(infousuario.getNombrecatgasto());
            EditTextDescCatGastos.setText(infousuario.getDesccatgasto());

        }

    }

    private void modificarCatGasto(View view) {
        if(EditTextNombreCatGastos.getText().toString().isEmpty()||EditTextDescCatGastos.getText().toString().isEmpty()){
            Toast.makeText(this,"Por favor llene todos los campos",Toast.LENGTH_LONG).show();
        }
        else{
            if(!verificarInformacion()){
                UsuarioCategoriasGasto usuario = new UsuarioCategoriasGasto();
                usuario.setIdcatgasto(getIntent().getExtras().getInt("id"));
                usuario.setNombrecatgasto(EditTextNombreCatGastos.getText().toString().trim());
                usuario.setDesccatgasto(EditTextDescCatGastos.getText().toString().trim());
                boolean res= db.editarCatGasto(usuario);
                DbHistorico dbHistorico = new DbHistorico(this);
                dbHistorico.actualizarHistorico(correoElectronicoS, new DbIngresos(this).obtenerIngresosTotales(correoElectronicoS), db.mostrarGastosTotales(correoElectronicoS));

                if (res) {
                    Toast.makeText(this, "Se ha modificado la categoria.", Toast.LENGTH_SHORT).show();
                    cambiaraAtras(view);
                } else {
                    Toast.makeText(this, "Error al modificar la categoria.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "El nombre de la categoría ya existe. Intente otra vez.", Toast.LENGTH_SHORT).show();
            }
            db.close();

        }

    }

    public boolean verificarInformacion(){
        HashMap<Integer,String> nombres = db.verificarRepetidosCategGasto(correoElectronicoS);
        boolean repetido=false;
        for (Map.Entry<Integer, String> set :
                nombres.entrySet()) {

            if(set.getValue().equalsIgnoreCase(EditTextNombreCatGastos.getText().toString().trim())){
                if(set.getKey() == getIntent().getExtras().getInt("id")){
                }
                else{
                    repetido=true;
                }
                break;
            }
        }
        return repetido;
    }

    private void agregarCatGasto(View view) {
        boolean res;
        if(EditTextNombreCatGastos.getText().toString().isEmpty()||EditTextDescCatGastos.getText().toString().isEmpty()){
            Toast.makeText(this,"Por favor llene todos los campos",Toast.LENGTH_LONG).show();
        }
        else{
            if(!verificarInformacion()){
                UsuarioCategoriasGasto usuario = new UsuarioCategoriasGasto();
                usuario.setCorreocatgasto(correoElectronicoS);
                usuario.setNombrecatgasto(EditTextNombreCatGastos.getText().toString().trim());
                usuario.setDesccatgasto(EditTextDescCatGastos.getText().toString().trim());

                res= db.insertarNuevaCategoria(usuario);
                DbHistorico dbHistorico = new DbHistorico(this);
                dbHistorico.actualizarHistorico(correoElectronicoS, new DbIngresos(this).obtenerIngresosTotales(correoElectronicoS), db.mostrarGastosTotales(correoElectronicoS));

                if (res) {
                    Toast.makeText(this, "Se ha agregado la categoría.", Toast.LENGTH_SHORT).show();
                    cambiaraAtras(view);
                } else {
                    Toast.makeText(this, "Error al agregar.", Toast.LENGTH_SHORT).show();
                    EditTextDescCatGastos.setText("");
                    EditTextNombreCatGastos.setText("");
                }
            }
            else{
                Toast.makeText(this, "El nombre de la categoría ya existe. Intente otra vez.", Toast.LENGTH_SHORT).show();
            }

            db.close();
        }
    }

    public void cambiaraAtras(View view){
        Intent miIntent = new Intent(this, CategoriasGasto.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
}