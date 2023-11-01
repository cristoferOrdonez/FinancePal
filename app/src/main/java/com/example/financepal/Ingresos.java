package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.financepal.adaptadores.AdaptadorIngresos;
import com.example.financepal.db.DbIngresos;
import com.example.financepal.entidades.Ingreso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Ingresos extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonMas;
    ListView listViewIngresos;
    List<Ingreso> list;

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonMas = findViewById(R.id.botonMasIngresos);
        botonMas.setOnClickListener(view -> cambiarParaCrearIngreso(view));

        establecerLista();

    }

    public void mostrarDialogo(long id){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.cuadros_dialogo_ingresos, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button modificar = view.findViewById(R.id.botonModificarINGRESOS);
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                cambiarParaModificarIngreso(id);

            }
        });

        Button eliminar = view.findViewById(R.id.botonEliminarINGRESOS);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    eliminarIngreso(id);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                dialog.dismiss();

            }
        });

        Button cancelar = view.findViewById(R.id.botonCancelarINGRESOS);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public List<Ingreso> getData() throws IOException {

        String nombre;
        int monto;
        list = new ArrayList<>();

        InputStreamReader archivo = new InputStreamReader(openFileInput(correoElectronicoS + "_INGRESOS.txt"));
        BufferedReader br = new BufferedReader(archivo);

        String linea = br.readLine();

        int i = 1;

        while(linea != null){

            nombre = linea.substring(8, linea.indexOf("; monto"));
            monto = Integer.parseInt(linea.substring(linea.indexOf("monto: ") + 7, linea.length() - 1));
            NumberFormat col = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
            list.add(new Ingreso(i, nombre, col.format(monto) + " COP")); //Hay que ponernos de acuerdo con el formato
            i++;
            linea = br.readLine();

        }

        archivo.close();
        br.close();

        return list;

    }

    public void volver(View view){

        Intent myIntent = new Intent(this, PantallaPrincipal.class);
        myIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(myIntent);
        finishAffinity();

    }

    public void cambiarParaCrearIngreso(View view){

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

        DbIngresos dbIngresos = new DbIngresos(Ingresos.this);

        list = dbIngresos.mostrarIngresos(correoElectronicoS);

        AdaptadorIngresos adaptador = new AdaptadorIngresos(this, list); ///
        listViewIngresos.setAdapter(adaptador);

        listViewIngresos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ingreso ing = list.get(i);

                long id = ing.id;

                mostrarDialogo(id);
            }
        });

    }

    public void eliminarIngreso(long id) throws IOException {

        /*

        String nombreAr;
        InputStreamReader archivo = new InputStreamReader(openFileInput(correoElectronicoS + "_INGRESOS.txt"));
        BufferedReader br = new BufferedReader(archivo);
        String linea = br.readLine();
        String contenido = "";

        while(linea != null){

            nombreAr = linea.substring(8, linea.indexOf("; monto"));

            if(!nombreAr.equals(nombre))
                contenido += linea + "\n";

            linea = br.readLine();

        }

        OutputStreamWriter archivoNuevo = new OutputStreamWriter(openFileOutput(correoElectronicoS + "_INGRESOS.txt", Context.MODE_PRIVATE));
        archivoNuevo.write(contenido);
        archivoNuevo.flush();
        archivoNuevo.close();
        Toast.makeText(this, "Se ha eliminado el ingreso.", Toast.LENGTH_SHORT).show();

        br.close();
        archivo.close();

         */

        DbIngresos dbIngresos = new DbIngresos(this);

        if(dbIngresos.elimnarIngreso(id)){
            Toast.makeText(this, "Se ha eliminado el ingreso.", Toast.LENGTH_SHORT).show();
        }

        establecerLista();


    }

}