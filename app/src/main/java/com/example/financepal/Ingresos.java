package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.financepal.adaptadores.AdaptadorIngresos;
import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHistorico;
import com.example.financepal.db.DbIngresos;
import com.example.financepal.entidades.Ingreso;
import java.io.IOException;
import java.util.List;

public class Ingresos extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonMas, botonAtras;
    ListView listViewIngresos;
    List<Ingreso> list;

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonMas = findViewById(R.id.botonMasIngresos);
        botonMas.setOnClickListener(view -> cambiarParaCrearIngreso());

        botonAtras = findViewById(R.id.botonAtrasINGRESOS);
        botonAtras.setOnClickListener(view -> volver());

        establecerLista();

    }

    public void mostrarDialogo(long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadros_dialogo_ingresos, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button modificar = view.findViewById(R.id.botonModificarINGRESOS);
        modificar.setOnClickListener(i -> {
                dialog.dismiss();
                cambiarParaModificarIngreso(id);
        });

        Button eliminar = view.findViewById(R.id.botonEliminarINGRESOS);
        eliminar.setOnClickListener(i -> {
                try {
                    eliminarIngreso(id);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                dialog.dismiss();
        });

        Button cancelar = view.findViewById(R.id.botonCancelarINGRESOS);
        cancelar.setOnClickListener(i -> dialog.dismiss());

    }

    public void volver(){

        Intent myIntent = new Intent(this, PantallaPrincipal.class);
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(myIntent);
        finishAffinity();

    }

    public void cambiarParaCrearIngreso(){

        Intent myIntent = new Intent(this, CrearModificarIngresos.class);
        myIntent.putExtra("funcionBoton", "Crear");
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(myIntent);
        finishAffinity();

    }

    public void cambiarParaModificarIngreso(long id){

        Intent myIntent = new Intent(this, CrearModificarIngresos.class);
        myIntent.putExtra("funcionBoton", "Guardar");
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
        finishAffinity();

    }

    public void establecerLista(){

        listViewIngresos = findViewById(R.id.listViewIngresos);

        DbIngresos dbIngresos = new DbIngresos(this);

        list = dbIngresos.mostrarIngresos(correoElectronicoS);

        AdaptadorIngresos adaptador = new AdaptadorIngresos(this, list);
        listViewIngresos.setAdapter(adaptador);

        listViewIngresos.setOnItemClickListener((adapterView, view, i, l) -> {
                Ingreso ing = list.get(i);
                long id = ing.getId();
                mostrarDialogo(id);
        });

    }

    public void eliminarIngreso(long id) throws IOException {

        DbIngresos dbIngresos = new DbIngresos(this);

        if(dbIngresos.elimnarIngreso(id)){
            Toast.makeText(this, "Se ha eliminado el ingreso.", Toast.LENGTH_SHORT).show();
        }

        DbHistorico dbHistorico = new DbHistorico(this);
        dbHistorico.actualizarHistorico(correoElectronicoS, dbIngresos.obtenerIngresosTotales(correoElectronicoS), new DbGastos(this).mostrarGastosTotales(correoElectronicoS));

        establecerLista();


    }

    @Override
    public void onBackPressed(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == KeyEvent.KEYCODE_BACK){

            volver();

        }

        return super.onKeyDown(keyCode, event);

    }

}