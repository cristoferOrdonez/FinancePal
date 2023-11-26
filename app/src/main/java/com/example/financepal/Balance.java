package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.financepal.adaptadores.MetodosComunes;
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

        espacioFecha.setText(MetodosComunes.obtenerPrefijoMes(calendario.get(Calendar.MONTH) + 1) + " " + calendario.get(Calendar.YEAR));

        NumberFormat col = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));


        DbNombreMetas dbNombreMetas = new DbNombreMetas(this);
        Double montomensualmetas = dbNombreMetas.sumarMontosMensuales(correoElectronicoS);
        espacioMetasMensualesTotales.setText((col.format(montomensualmetas)) + " COP");


        DbIngresos dbIngresos = new DbIngresos(this);
        Long montoMensualIngresos = dbIngresos.obtenerIngresosTotales(correoElectronicoS);
        espacioIngresosTotales.setText(col.format(montoMensualIngresos) + " COP");

        DbGastos dbGastos = new DbGastos(this);
        long totalGastos = dbGastos.mostrarGastosTotales(correoElectronicoS);
        espacioGastoTotales.setText((col.format(totalGastos)) + " COP");

        espacioBalance.setText(col.format(montoMensualIngresos - totalGastos) + " COP");


        UsuarioGastos gastoAltoUsuario = (dbGastos.gastoMasAlto(correoElectronicoS));
        if (gastoAltoUsuario != null) {
            String nombreGastoMasAlto = gastoAltoUsuario.getNombregasto();
            int cantGastoMasAlto = gastoAltoUsuario.getMontogasto();
            espacioMayorGasto.setText(nombreGastoMasAlto + "\n" + (col.format(cantGastoMasAlto) + " COP"));
        } else {
            espacioMayorGasto.setText("No hay gastos relacionados");
        }


        UsuarioGastos gastoRecurrenteUsuario = dbGastos.gastoMasRecurrente(correoElectronicoS);
        if (gastoRecurrenteUsuario != null) {
            String nombreGastoMasRecurrente = gastoRecurrenteUsuario.getNombregasto();
            int cantGastoMasREcurrente = gastoRecurrenteUsuario.getMontogasto()*gastoRecurrenteUsuario.getRecurrenciagasto();
            espacioGastoMasRecurrente.setText(nombreGastoMasRecurrente+"\n"+(col.format(cantGastoMasREcurrente) + " COP"));
        } else {
            espacioGastoMasRecurrente.setText("No hay gastos relacionados");
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
            espacioGastoMenorPrioridad.setText(nombreGastoMasPrioridad+"\n"+(col.format(cantGastoMasPrioridad) + " COL"));
        } else {
            espacioGastoMenorPrioridad.setText("No hay gastos relacionados");
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

    @Override
    public void onBackPressed(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == event.KEYCODE_BACK){

            SpannableString message = new SpannableString("¿Desea salir de Finance Pal?");
            message.setSpan(new ForegroundColorSpan(Color.WHITE), 0, message.length(), 0);

            SpannableString afirmacion = new SpannableString("Si");
            afirmacion.setSpan(new ForegroundColorSpan(Color.WHITE), 0, afirmacion.length(), 0);

            SpannableString negacion = new SpannableString("No");
            negacion.setSpan(new ForegroundColorSpan(Color.WHITE), 0, negacion.length(), 0);

            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
            builder.setMessage(message)
                    .setPositiveButton(afirmacion, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which){

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                    })
                    .setNegativeButton(negacion, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which){

                            dialog.dismiss();

                        }

                    });
            builder.show();
        }

        return super.onKeyDown(keyCode, event);

    }
}