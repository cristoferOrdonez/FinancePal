package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.financepal.adaptadores.AdaptadorHistorico;
import com.example.financepal.adaptadores.CustomAdapterGastos;
import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbHistorico;
import com.example.financepal.entidades.EntidadHistorico;
import com.example.financepal.entidades.UsuarioGastos;

import java.util.List;

public class Historico extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonInicio, botonBalance, botonMisDatos;
    private DbHistorico db;
    List<EntidadHistorico> lista;

    ListView ListViewHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        db = new DbHistorico(this);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonInicio = findViewById(R.id.botonInicioHISTORICO);
        botonInicio.setOnClickListener(view -> cambiarAInicio(view));

        botonBalance = findViewById(R.id.botonBalanceHISTORICO);
        botonBalance.setOnClickListener(view -> cambiarABalance(view));

        botonMisDatos = findViewById(R.id.botonMisDatosHISTORICO);
        botonMisDatos.setOnClickListener(view -> cambiarAMisDatos(view));

        ListViewHistorico = findViewById(R.id.listViewHistorico);
        try {
            lista=db.mostrarHistorico(correoElectronicoS);
            AdaptadorHistorico adapter = new AdaptadorHistorico(this,lista);
            ListViewHistorico.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }


    }

    public void cambiarAInicio(View view){

        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    public void cambiarABalance(View view){

        Intent miIntent = new Intent(this, Balance.class);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de Finance Pal?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which){

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {

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