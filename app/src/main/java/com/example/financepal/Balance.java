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

import java.text.NumberFormat;
import java.util.Locale;

public class Balance extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonInicio, botonHistorico, botonMisDatos;

    EditText espacioIngresosTotales, espacioGastoTotales, espacioMetasMensualesTotales, espacioBalance, espacioMayorGasto, espacioGastoMasRecurrente, espacioGastoMenorPrioridad;

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


        espacioIngresosTotales=findViewById(R.id.espacioIngresosTotales);
        espacioGastoTotales=findViewById(R.id.espacioGastosTotales);
        espacioMetasMensualesTotales=findViewById(R.id.espacioMetasMensualesTotales);
        espacioBalance=findViewById(R.id.espacioBalance);
        espacioMayorGasto=findViewById(R.id.espacioMayorGasto);
        espacioGastoMasRecurrente=findViewById(R.id.espacioGastoMasRecurrente);
        espacioGastoMenorPrioridad=findViewById(R.id.espacioGastoMenorPrioridad);

        espacioIngresosTotales.setInputType(InputType.TYPE_NULL);
        espacioGastoTotales.setInputType(InputType.TYPE_NULL);
        espacioMetasMensualesTotales.setInputType(InputType.TYPE_NULL);
        espacioBalance.setInputType(InputType.TYPE_NULL);
        espacioMayorGasto.setInputType(InputType.TYPE_NULL);
        espacioGastoMasRecurrente.setInputType(InputType.TYPE_NULL);
        espacioGastoMenorPrioridad.setInputType(InputType.TYPE_NULL);


        NumberFormat col = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));







        DbNombreMetas dbNombreMetas =new DbNombreMetas(this);
        Double montomensualmetas =dbNombreMetas.sumarMontosMensuales(correoElectronicoS);
        espacioMetasMensualesTotales.setText((col.format(montomensualmetas))+" COP");
        dbNombreMetas.close();

        DbIngresos dbIngresos = new DbIngresos(this);
        Long montoMensualIngresos = dbIngresos.obtenerIngresosTotales(correoElectronicoS);
        espacioIngresosTotales.setText(col.format(montoMensualIngresos) + " COP");



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