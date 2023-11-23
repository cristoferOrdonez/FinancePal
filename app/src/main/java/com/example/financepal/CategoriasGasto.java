package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.financepal.adaptadores.CustomAdapterCategGastos;
import com.example.financepal.adaptadores.CustomAdapterGastos;
import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHistorico;
import com.example.financepal.db.DbIngresos;
import com.example.financepal.entidades.UsuarioCategoriasGasto;
import com.example.financepal.entidades.UsuarioGastos;

import java.io.IOException;
import java.util.List;

public class CategoriasGasto extends AppCompatActivity {
    String correoElectronicoS;
    ListView ListViewCatGastos;
    List<UsuarioCategoriasGasto> lista;

    private DbGastos db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_gasto);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        db = new DbGastos(this);
        listarDatos();
        db.close();
    }

    private void listarDatos() {
        try{
            ListViewCatGastos = (ListView) findViewById(R.id.listViewCategGastos);
            lista=db.buscarCategGastos(correoElectronicoS);
            CustomAdapterCategGastos adapter = new CustomAdapterCategGastos(this,lista);
            ListViewCatGastos.setAdapter(adapter);
            ListViewCatGastos.refreshDrawableState();
            int numcateg=adapter.getCount();
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();
        }

        ListViewCatGastos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UsuarioCategoriasGasto usuario = lista.get(i);

                int id = usuario.getIdcatgasto();
                if (lista.size()>1){
                    mostrarDialogoCompleto(id);
                }
                else{
                    mostrarDialogoAcortado(id);
                }

            }
        });

    }

    private void mostrarDialogoAcortado(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadro_dialogo_gastos2, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button modificar = view.findViewById(R.id.botonModificarGastos2);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                cambiarParaModificarCatGasto(id);

            }
        });

        Button cancelar = view.findViewById(R.id.botonCancelarGastos2);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void mostrarDialogoCompleto(int id) {

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
                cambiarParaModificarCatGasto(id);

            }
        });

        Button eliminar = view.findViewById(R.id.botonEliminarGastos);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    eliminarCatGasto(id);
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

    private void eliminarCatGasto(int id) throws IOException{

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadro_dialogo_categ_gastos, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button aceptar = view.findViewById(R.id.botonAceptarCategGastos);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                db.eliminarCatGasto(id);
                DbHistorico dbHistorico = new DbHistorico(CategoriasGasto.this);
                dbHistorico.actualizarHistorico(correoElectronicoS, new DbIngresos(CategoriasGasto.this).obtenerIngresosTotales(correoElectronicoS), db.mostrarGastosTotales(correoElectronicoS));

                listarDatos();


            }
        });


        Button cancelar = view.findViewById(R.id.botonCancelarCategGastos);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void cambiarParaModificarCatGasto(int id) {
        Intent myIntent = new Intent(this, AgregarCategoriaGasto.class);
        myIntent.putExtra("funcionBoton", "Guardar");
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
    }

    public void cambiaraAtras(View view){
        Intent miIntent = new Intent(this, Gastos.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();
    }

    public void cambiaraNuevaCateg(View view){
        Intent miIntent = new Intent(this, AgregarCategoriaGasto.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        miIntent.putExtra("funcionBoton", "Crear");
        startActivity(miIntent);
    }
}