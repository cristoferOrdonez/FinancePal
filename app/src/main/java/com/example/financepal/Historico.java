package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.financepal.adaptadores.AdaptadorHistorico;
import com.example.financepal.db.DbHistorico;
import com.example.financepal.entidades.EntidadHistorico;

import java.util.List;

public class Historico extends AppCompatActivity {

    String correoElectronicoS;
    ImageView botonInicio, botonBalance, botonMisDatos;
    List<EntidadHistorico> lista;

    ListView ListViewHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        DbHistorico db = new DbHistorico(this);
        correoElectronicoS = getIntent().getStringExtra("correoElectronico");

        botonInicio = findViewById(R.id.botonInicioHISTORICO);
        botonInicio.setOnClickListener(view -> cambiarAInicio());

        botonBalance = findViewById(R.id.botonBalanceHISTORICO);
        botonBalance.setOnClickListener(view -> cambiarABalance());

        botonMisDatos = findViewById(R.id.botonMisDatosHISTORICO);
        botonMisDatos.setOnClickListener(view -> cambiarAMisDatos());

        ListViewHistorico = findViewById(R.id.listViewHistorico);
        try {
            lista=db.mostrarHistorico(correoElectronicoS);
            AdaptadorHistorico adapter = new AdaptadorHistorico(this,lista);
            ListViewHistorico.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }


    }

    public void cambiarAInicio(){

        Intent miIntent = new Intent(this, PantallaPrincipal.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    public void cambiarABalance(){

        Intent miIntent = new Intent(this, Balance.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    public void cambiarAMisDatos(){

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

        if(keyCode == KeyEvent.KEYCODE_BACK){

            SpannableString message = new SpannableString("¿Desea salir de Finance Pal?");
            message.setSpan(new ForegroundColorSpan(Color.WHITE), 0, message.length(), 0);

            SpannableString afirmacion = new SpannableString("Si");
            afirmacion.setSpan(new ForegroundColorSpan(Color.WHITE), 0, afirmacion.length(), 0);

            SpannableString negacion = new SpannableString("No");
            negacion.setSpan(new ForegroundColorSpan(Color.WHITE), 0, negacion.length(), 0);

            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
            builder.setMessage(message)
                    .setPositiveButton(afirmacion, (dialog, which)-> {

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finishAndRemoveTask();
                        } else {
                            finishAffinity();
                            System.exit(0);
                        }

                    })
                    .setNegativeButton(negacion, (dialog, which) -> dialog.dismiss());
            builder.show();
        }

        return super.onKeyDown(keyCode, event);

    }

}