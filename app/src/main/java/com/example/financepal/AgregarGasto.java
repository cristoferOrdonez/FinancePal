package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.financepal.db.DbGastos;
import com.example.financepal.entidades.UsuarioCategoriasGasto;
import com.example.financepal.entidades.UsuarioGastos;
import com.example.financepal.entidades.UsuarioPrioridadesGasto;

import java.util.ArrayList;
import java.util.List;

public class AgregarGasto extends AppCompatActivity {

    String correoElectronicoS;
    private DbGastos db;
    EditText nombre, monto, recurrencia;
    Spinner spinnerPrioridadGasto, spinnerCategoriaGasto;
    ArrayList<String> prioridadgasto, categoriagasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        setContentView(R.layout.activity_agregar_gasto);
        db = new DbGastos(this);

        nombre = (EditText) findViewById(R.id.editTextNombreCrearModificarGastos);
        monto = (EditText) findViewById(R.id.editTextMontoCrearModificarIGastos);
        recurrencia = (EditText) findViewById(R.id.editTextRecurrenciaCrearModificarGastos);
        spinnerCategoriaGasto = (Spinner) findViewById(R.id.spinnerCategoriaGasto);
        spinnerPrioridadGasto = (Spinner) findViewById(R.id.spinnerPrioridadGasto);

        List<UsuarioCategoriasGasto> listCategorias = llenarSpinnerCategorias();
        ArrayAdapter<UsuarioCategoriasGasto> arrayAdapter1 = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listCategorias);
        spinnerCategoriaGasto.setAdapter(arrayAdapter1);

        List<UsuarioPrioridadesGasto> listPrioridades = llenarSpinnerPrioridades();
        ArrayAdapter<UsuarioPrioridadesGasto> arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listPrioridades);
        spinnerPrioridadGasto.setAdapter(arrayAdapter2);

    }

    public void agregarGastos(View view){
        if(nombre.getText().toString().isEmpty()||monto.getText().toString().isEmpty()||recurrencia.getText().toString().isEmpty()){
            Toast.makeText(this,"Por favor llene todos los campos",Toast.LENGTH_LONG);
        }
        else{
            UsuarioGastos usuario = new UsuarioGastos();
            usuario.setCorreogasto(correoElectronicoS);
            usuario.setNombregasto(nombre.getText().toString());
            usuario.setIdcatgasto(((UsuarioCategoriasGasto)spinnerCategoriaGasto.getSelectedItem()).getIdcatgasto());
            usuario.setIdprioridad(((UsuarioPrioridadesGasto)spinnerPrioridadGasto.getSelectedItem()).getIdprioridad());
            usuario.setMontogasto(Integer.parseInt(monto.getText().toString()));
            usuario.setRecurrenciagasto(Integer.parseInt(recurrencia.getText().toString()));
            long res= db.insertarGasto(usuario);
            if(res==-1){
                Toast.makeText(AgregarGasto.this,"ERROR. Intente otra vez",Toast.LENGTH_SHORT);
                nombre.setText("");
                monto.setText("");
                recurrencia.setText("");

            }
            else{
                Toast.makeText(AgregarGasto.this,"Gasto Agregado",Toast.LENGTH_SHORT);
                nombre.setText("");
                monto.setText("");
                recurrencia.setText("");
            }

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