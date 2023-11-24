package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.financepal.db.DbIngresos;
import com.example.financepal.db.DbNombreMetas;
import com.example.financepal.db.DbGastos;
import com.example.financepal.entidades.Usuario;
import com.example.financepal.entidades.UsuarioGastos;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import android.widget.TextView;

public class Balance extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonInicio, botonHistorico, botonMisDatos;

    TextView espacioIngresosTotales, espacioGastoTotales, espacioMetasMensualesTotales, espacioBalance, espacioMayorGasto, espacioGastoMasRecurrente, espacioGastoMenorPrioridad, espacioFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonInicio = findViewById(R.id.botonInicioBALANCE);
        botonInicio.setOnClickListener(view -> cambiarAInicio(view));

        botonHistorico = findViewById(R.id.botonHistoricoBALANCE);
        botonHistorico.setOnClickListener(view -> cambiarAHistorico(view));

        botonMisDatos = findViewById(R.id.botonMisDatosBALANCE);
        botonMisDatos.setOnClickListener(view -> cambiarAMisDatos(view));


        espacioIngresosTotales = findViewById(R.id.espacioIngresosTotales); //
        espacioGastoTotales = findViewById(R.id.espacioGastosTotales); //
        espacioMetasMensualesTotales = findViewById(R.id.espacioMetasMensualesTotales); ///
        espacioBalance = findViewById(R.id.espacioBalance); //
        espacioMayorGasto = findViewById(R.id.espacioMayorGasto); ///
        espacioGastoMasRecurrente = findViewById(R.id.espacioGastoMasRecurrente);
        espacioGastoMenorPrioridad = findViewById(R.id.espacioGastoMenorPrioridad);
        espacioFecha = findViewById(R.id.textViewFechaBALANCE);

        GregorianCalendar calendario = new GregorianCalendar();

        espacioFecha.setText("Fecha:\n" + (calendario.get(Calendar.MONTH) + 1) + "/" + calendario.get(Calendar.YEAR));

        NumberFormat col = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));


        DbNombreMetas dbNombreMetas = new DbNombreMetas(this);
        Double montomensualmetas = dbNombreMetas.sumarMontosMensuales(correoElectronicoS);
        espacioMetasMensualesTotales.setText("Metas mensuales totales:\n" + (col.format(montomensualmetas)) + " COP");


        DbIngresos dbIngresos = new DbIngresos(this);
        Long montoMensualIngresos = dbIngresos.obtenerIngresosTotales(correoElectronicoS);
        espacioIngresosTotales.setText("Ingresos totales:\n" + col.format(montoMensualIngresos) + " COP");

        DbGastos dbGastos = new DbGastos(this);
        long totalGastos = dbGastos.mostrarGastosTotales(correoElectronicoS);
        espacioGastoTotales.setText("Gastos totales:\n" + (col.format(totalGastos)) + " COP");

        espacioBalance.setText("Balance:\n" + col.format(montoMensualIngresos - totalGastos) + " COP");


        UsuarioGastos gastoAltoUsuario = (dbGastos.gastoMasAlto(correoElectronicoS));
        if (gastoAltoUsuario != null) {
            String nombreGastoMasAlto = gastoAltoUsuario.getNombregasto();
            int cantGastoMasAlto = gastoAltoUsuario.getMontogasto();
            espacioMayorGasto.setText("Mayor gasto:\n" + nombreGastoMasAlto + "\n" + (col.format(cantGastoMasAlto) + " COL"));
        } else {
            espacioMayorGasto.setText("Mayor gasto:\nNo hay gastos registrados");
        }


        UsuarioGastos gastoRecurrenteUsuario = dbGastos.gastoMasRecurrente(correoElectronicoS);
        if (gastoRecurrenteUsuario != null) {
            String nombreGastoMasRecurrente = gastoRecurrenteUsuario.getNombregasto();
            int cantGastoMasREcurrente = gastoRecurrenteUsuario.getMontogasto()*gastoRecurrenteUsuario.getRecurrenciagasto();
            espacioGastoMasRecurrente.setText("Gasto más recurrente:\n" + nombreGastoMasRecurrente+"\n"+(col.format(cantGastoMasREcurrente) + " COL"));
        } else {
            espacioGastoMasRecurrente.setText("Gasto más recurrente:\nNo hay gastos registrados");
        }

/*
        List<UsuarioGastos> gastosPrioridades = dbGastos.gastoMasAltoPrioridades(correoElectronicoS);
        if (!gastosPrioridades.isEmpty()) {
            UsuarioGastos mayorMontoMenorPrioridadUsuario = gastosPrioridades.get(2);
            if (mayorMontoMenorPrioridadUsuario != null) {
                String nombreMenorPrioridad = mayorMontoMenorPrioridadUsuario.getNombregasto();
                int montoMenorPrioridad = mayorMontoMenorPrioridadUsuario.getMontogasto();
                espacioGastoMenorPrioridad.setText(nombreMenorPrioridad + "\n" + (col.format(montoMenorPrioridad) + " COL"));
            } else {
                espacioGastoMenorPrioridad.setText("No hay gastos registrados");
            }
        } else {
            espacioGastoMenorPrioridad.setText("No hay lista");
        }
        */
        UsuarioGastos gastoPrioridad = dbGastos.gastoMasPrioridades(correoElectronicoS);
        if (gastoPrioridad != null) {
            String nombreGastoMasPrioridad = gastoPrioridad.getNombregasto();
            int cantGastoMasPrioridad = gastoPrioridad.getMontogasto();
            espacioGastoMenorPrioridad.setText("Mayor gasto con menor prioridad:\n" + nombreGastoMasPrioridad+"\n"+(col.format(cantGastoMasPrioridad) + " COL"));
        } else {
            espacioGastoMenorPrioridad.setText("Mayor gasto con menor prioridad:\nNo hay gastos registrados");
        }





        dbNombreMetas.close();
        dbIngresos.close();
        dbGastos.close();


    }

    public void cambiarAInicio(View view){

        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    public void cambiarAHistorico(View view){

        Intent miIntent = new Intent(this, Historico.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    public void cambiarAMisDatos(View view){

        Intent miIntent = new Intent(this, MisDatos.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

}