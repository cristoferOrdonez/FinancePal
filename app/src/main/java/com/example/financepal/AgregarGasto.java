package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.financepal.db.DbGastos;
import com.example.financepal.entidades.UsuarioCategoriasGasto;
import com.example.financepal.entidades.UsuarioGastos;
import com.example.financepal.entidades.UsuarioPrioridadesGasto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgregarGasto extends AppCompatActivity {

    String correoElectronicoS;
    private DbGastos db;

    Button botonCrearGuardarGasto;
    EditText nombre, monto, recurrencia;
    Spinner spinnerPrioridadGasto, spinnerCategoriaGasto;
    ArrayList<String> prioridadgasto, categoriagasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        setContentView(R.layout.activity_agregar_gasto);
        db = new DbGastos(this);

        nombre = findViewById(R.id.editTextNombreCrearModificarGastos);
        monto = findViewById(R.id.editTextMontoCrearModificarIGastos);
        recurrencia = findViewById(R.id.editTextRecurrenciaCrearModificarGastos);
        spinnerCategoriaGasto = findViewById(R.id.spinnerCategoriaGasto);
        spinnerPrioridadGasto = findViewById(R.id.spinnerPrioridadGasto);

        List<UsuarioCategoriasGasto> listCategorias = llenarSpinnerCategorias();
        ArrayAdapter<UsuarioCategoriasGasto> arrayAdapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listCategorias);
        spinnerCategoriaGasto.setAdapter(arrayAdapter1);

        List<UsuarioPrioridadesGasto> listPrioridades = llenarSpinnerPrioridades();
        ArrayAdapter<UsuarioPrioridadesGasto> arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listPrioridades);
        spinnerPrioridadGasto.setAdapter(arrayAdapter2);

        botonCrearGuardarGasto = findViewById(R.id.botonCrearGuardarCrearModificarGastos);
        botonCrearGuardarGasto.setText(getIntent().getStringExtra("funcionBoton"));

        try {
            establecerEditText();
        } catch (IOException e) {
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }

    }

    public void agregarGastos(View view){
        if(nombre.getText().toString().isBlank()||nombre.getText().toString().isEmpty()||monto.getText().toString().isEmpty()||recurrencia.getText().toString().isEmpty()){
            Toast.makeText(this,"Por favor llene todos los campos",Toast.LENGTH_LONG).show();
        }
        else{
            if(!verificarInformacion()){
                UsuarioGastos usuario = new UsuarioGastos();
                usuario.setCorreogasto(correoElectronicoS);
                usuario.setNombregasto(nombre.getText().toString().stripLeading().stripTrailing());
                usuario.setIdcatgasto(((UsuarioCategoriasGasto)spinnerCategoriaGasto.getSelectedItem()).getIdcatgasto());
                usuario.setIdprioridad(((UsuarioPrioridadesGasto)spinnerPrioridadGasto.getSelectedItem()).getIdprioridad());
                usuario.setMontogasto(Integer.parseInt(monto.getText().toString()));

                usuario.setRecurrenciagasto(Integer.parseInt(recurrencia.getText().toString()));
                long res= db.insertarGasto(usuario);
                if(res==-1){
                    Toast.makeText(AgregarGasto.this,"ERROR. Intente otra vez",Toast.LENGTH_SHORT).show();
                    nombre.setText("");
                    monto.setText("");
                    recurrencia.setText("");

                }
                else{
                    Toast.makeText(AgregarGasto.this,"Gasto Agregado",Toast.LENGTH_SHORT).show();
                    nombre.setText("");
                    monto.setText("");
                    recurrencia.setText("");
                }

                cambiaraAtras(view);
            }
            else{
                Toast.makeText(this, "El nombre del gasto ya existe. Intente otra vez.", Toast.LENGTH_SHORT).show();
            }


        }

    }

    public boolean verificarInformacion(){
        HashMap<Integer,String> nombres = db.verificarRepetidosGasto(correoElectronicoS);
        boolean repetido=false;
        for (Map.Entry<Integer, String> set :
                nombres.entrySet()) {

            if(set.getValue().equalsIgnoreCase(nombre.getText().toString().trim().stripLeading().stripTrailing())){
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

    private void modificarGasto(View view) {
        try{
            if(!verificarInformacion()){
                UsuarioGastos usuario = new UsuarioGastos();
                int id = getIntent().getExtras().getInt("id");
                usuario.setIdgastos(id);
                usuario.setCorreogasto(correoElectronicoS);
                usuario.setNombregasto(nombre.getText().toString());
                usuario.setIdcatgasto(((UsuarioCategoriasGasto)spinnerCategoriaGasto.getSelectedItem()).getIdcatgasto());
                usuario.setIdprioridad(((UsuarioPrioridadesGasto)spinnerPrioridadGasto.getSelectedItem()).getIdprioridad());
                usuario.setMontogasto(Integer.parseInt(monto.getText().toString()));
                usuario.setRecurrenciagasto(Integer.parseInt(recurrencia.getText().toString()));
                if(nombre.getText().toString().isBlank()||nombre.getText().toString().isEmpty()||monto.getText().toString().isEmpty()||recurrencia.getText().toString().isEmpty()){
                    Toast.makeText(this,"Por favor llene todos los campos",Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean res= db.editarGasto(usuario);
                    if (res) {
                        Toast.makeText(this, "Se ha modificado el gasto.", Toast.LENGTH_SHORT).show();
                        cambiaraAtras(view);
                    } else {
                        Toast.makeText(this, "Error al modificar el gasto.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                Toast.makeText(this, "El nombre del gasto ya existe. Intente otra vez.", Toast.LENGTH_SHORT).show();
            }

        }
        catch(NumberFormatException e){
            Toast.makeText(this, "Ingrese todos los campos.", Toast.LENGTH_SHORT).show();
        }

    }

    public void detectarIntencionBoton(View view) {

        if(botonCrearGuardarGasto.getText().toString().equals("Crear")){
            agregarGastos(view);
        } else {
            modificarGasto(view);
        }

    }


    public void establecerEditText() throws IOException {

        if(getIntent().getStringExtra("funcionBoton").equals("Guardar")){

            int id = getIntent().getExtras().getInt("id");
            UsuarioGastos infousuario = db.buscarGasto(id);

            nombre.setText(""+infousuario.getNombregasto());
            monto.setText(""+infousuario.getMontogasto());
            spinnerPrioridadGasto.setSelection(infousuario.getIdprioridad()-1);
            recurrencia.setText(""+infousuario.getRecurrenciagasto());

        }

    }

    public List<UsuarioCategoriasGasto> llenarSpinnerCategorias(){
        List<UsuarioCategoriasGasto> listaCat= new ArrayList<>();
        Cursor datos = db.mostrarCategoriasGasto(correoElectronicoS);
        if(datos!=null){
            if(datos.moveToFirst()){
                do{
                    UsuarioCategoriasGasto usuario = new UsuarioCategoriasGasto();
                    usuario.setIdcatgasto(datos.getInt(0));
                    usuario.setCorreocatgasto(datos.getString(1));
                    usuario.setNombrecatgasto(datos.getString(2));
                    usuario.setDesccatgasto(datos.getString(3));
                    listaCat.add(usuario);
                }while(datos.moveToNext());
            }
        }
        db.close();
        return listaCat;
    }

    public List<UsuarioPrioridadesGasto> llenarSpinnerPrioridades(){
        List<UsuarioPrioridadesGasto> listaCat= new ArrayList<>();
        Cursor datos = db.mostrarPrioridadGastos();
        if(datos!=null){
            if(datos.moveToFirst()){
                do{
                    UsuarioPrioridadesGasto usuario = new UsuarioPrioridadesGasto();
                    usuario.setIdprioridad(datos.getInt(0));
                    usuario.setNombreprioridad(datos.getString(1));
                    listaCat.add(usuario);
                }while(datos.moveToNext());
            }
        }
        db.close();
        return listaCat;
    }

    public void cambiaraAtras(View view){
        Intent miIntent = new Intent(this, Gastos.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }
}