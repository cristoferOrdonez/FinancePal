package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;

import java.io.IOException;

import com.example.financepal.adaptadores.CustomAdapterGastos;
import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHistorico;
import com.example.financepal.db.DbIngresos;
import com.example.financepal.entidades.UsuarioGastos;

import java.util.List;

public class Gastos extends AppCompatActivity {
    String correoElectronicoS;
    ListView ListViewGastos;
    List<UsuarioGastos> lista;
    int id;

    private DbGastos db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        db = new DbGastos(this);
        setContentView(R.layout.activity_gastos);
        listarDatos();
        db.close();

    }

    public void listarDatos(){
        try{
            ListViewGastos = (ListView) findViewById(R.id.listViewGastosu);
            lista=db.buscarUsuario(correoElectronicoS);
            CustomAdapterGastos adapter = new CustomAdapterGastos(this,lista);
            ListViewGastos.setAdapter(adapter);
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
        }

        ListViewGastos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UsuarioGastos usuario = lista.get(i);

                int id = usuario.getIdgastos();

                mostrarDialogo(id);
            }
        });

    }

    public void mostrarDialogo(int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadro_dialogo_gastos, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button modificar = view.findViewById(R.id.botonModificarGastos);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                cambiarParaModificarGasto(id);

            }
        });

        Button eliminar = view.findViewById(R.id.botonEliminarGastos);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    eliminarGasto(id);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                dialog.dismiss();

            }
        });

        Button cancelar = view.findViewById(R.id.botonCancelarGastos);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void eliminarGasto(int id) throws IOException {
        if(db.eliminarGasto(id)){
            Toast.makeText(this, "Se ha eliminado el gasto.", Toast.LENGTH_SHORT).show();
        }
        DbHistorico dbHistorico = new DbHistorico(this);
        dbHistorico.actualizarHistorico(correoElectronicoS, new DbIngresos(this).obtenerIngresosTotales(correoElectronicoS), db.mostrarGastosTotales(correoElectronicoS));

        listarDatos();
    }

    public void cambiarAPantallaPrincipal(View view){
        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }

    public void cambiarACategoriaGastos(View view){
        Intent miIntent = new Intent(this, CategoriasGasto.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
    }

    public void cambiarANuevoGasto(View view){
        Intent miIntent = new Intent(this, AgregarGasto.class);
        miIntent.putExtra("funcionBoton", "Crear");
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
    }

    public void cambiarParaModificarGasto(int id){

        Intent myIntent = new Intent(this, AgregarGasto.class);
        myIntent.putExtra("funcionBoton", "Guardar");
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        myIntent.putExtra("id", id);
        startActivity(myIntent);

    }
}

