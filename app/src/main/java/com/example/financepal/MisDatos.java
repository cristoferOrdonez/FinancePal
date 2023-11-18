package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.financepal.db.DbHelperFP;
import com.example.financepal.db.DbIngresos;
import com.example.financepal.db.DbUsuarios;
import com.example.financepal.db.DbNombreMetas;

import com.example.financepal.entidades.Usuario;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MisDatos extends AppCompatActivity {

    //
    String correoElectronicoS;
    EditText editTextNombres, editTextApellidos, editTextEdad, editTextCorreoElectronico, editTextContrasena;
    ImageView botonInicio, botonBalance, botonHistorico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);

        botonInicio = findViewById(R.id.botonInicioMISDATOS);
        botonInicio.setOnClickListener(view -> cambiarAInicio(view));

        botonBalance = findViewById(R.id.botonBalanceMISDATOS);
        botonBalance.setOnClickListener(view -> cambiarABalance(view));

        botonHistorico = findViewById(R.id.botonHistoricoMISDATOS);
        botonHistorico.setOnClickListener(view -> cambiarAHistorico(view));

        editTextNombres = findViewById(R.id.editTextNombresMISDATOS);
        editTextApellidos = findViewById(R.id.editTextApellidosMISDATOS);
        editTextEdad = findViewById(R.id.editTextEdadMISDATOS);
        editTextCorreoElectronico = findViewById(R.id.editTextCorreoElectronicoRMISDATOS);
        editTextContrasena = findViewById(R.id.editTextContrasenaRMISDATOS);

        correoElectronicoS = getIntent().getStringExtra("correoElectronico");
        establecerEditText();

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

    public void cambiarAHistorico(View view){

        Intent miIntent = new Intent(this, Historico.class);
        miIntent.putExtra("correoElectronico", correoElectronicoS);
        startActivity(miIntent);
        finishAffinity();

    }

    public void verificarNuevosDatos(View view) {

        boolean flag = true;
        String mensajeError = "";

        if(editTextNombres.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombres validos\n";
            flag = false;
        }
        if(editTextApellidos.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado apellidos validos\n";
            flag = false;
        }
        if(editTextEdad.getText().toString().trim().equals("") || Integer.parseInt(editTextEdad.getText().toString().trim()) > 150) {
            mensajeError += "No ha ingresado una edad valida\n";
            flag = false;
        }
        if(!editTextCorreoElectronico.getText().toString().contains("@") || editTextCorreoElectronico.getText().toString().replaceAll("@","").trim().equals("") || editTextCorreoElectronico.getText().toString().contains(" ")){
            mensajeError += "No ha ingresado un correo electronico valido\n";
            flag = false;
        }
        if(editTextContrasena.getText().toString().length() < 8){
            mensajeError += "Debe ingresar una contraseña de por lo menos 8 caracteres\n";
            flag = false;
        }
        if(editTextContrasena.getText().toString().contains(" ")){
            mensajeError += "La contraseña no puede contener espacios en blanco\n";
            flag = false;
        }

        if(flag)
            actualizarInformacion(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();

    }

    public void actualizarInformacion(View view) {

        DbUsuarios dbUsuarios = new DbUsuarios(this);

        Usuario usuario = dbUsuarios.verUsuario(correoElectronicoS);

        String nombres = editTextNombres.getText().toString().trim();
        String apellidos = editTextApellidos.getText().toString().trim();
        int edad = Integer.parseInt(editTextEdad.getText().toString());
        String correoElectronico = editTextCorreoElectronico.getText().toString();
        String contrasena = editTextContrasena.getText().toString();

        if(nombres.equals(usuario.getNombres()) &&
        apellidos.equals(usuario.getApellidos()) &&
        edad==usuario.getEdad() &&
        correoElectronico.equalsIgnoreCase(usuario.getCorreoElectronico()) &&
        contrasena.equals(usuario.getContrasena())){

            Toast.makeText(this, "No ha cambiado ningún dato.", Toast.LENGTH_SHORT).show();

        } else {

            if(verificarRepeticion(dbUsuarios.obtenerCorreosElectronicos()) && !correoElectronico.trim().equalsIgnoreCase(correoElectronicoS)) {

                Toast.makeText(this, "El correo electronico ingresado ya se encuentra ingresado en otra cuenta.", Toast.LENGTH_SHORT).show();

            } else {
                DbNombreMetas dbNombreMetas = new DbNombreMetas(this);

                boolean correosGastosIngresosActu = new DbHelperFP(this).actualizarCorreos(correoElectronicoS, correoElectronico);


                boolean usuarioActualizado = dbUsuarios.actualizarUsuario(correoElectronicoS, nombres, apellidos, edad, correoElectronico, contrasena);

                boolean metasActualizadas = dbNombreMetas.actualizarCorreosMetas(correoElectronicoS, correoElectronico);

                if (usuarioActualizado && correosGastosIngresosActu && metasActualizadas) {
                    Toast.makeText(this, "Su información ha sido actualizada correctamente.", Toast.LENGTH_SHORT).show();
                    correoElectronicoS = correoElectronico.toLowerCase();
                    cambiarAInicio(view);
                } else {
                    Toast.makeText(this, "Error al actualizar la información. Por favor, inténtelo de nuevo.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
////
    public void establecerEditText() {

        Usuario usuario = new DbUsuarios(this).verUsuario(correoElectronicoS);

        editTextNombres.setText(usuario.getNombres());
        editTextApellidos.setText(usuario.getApellidos());
        editTextEdad.setText("" + usuario.getEdad());
        editTextCorreoElectronico.setText(usuario.getCorreoElectronico());
        editTextContrasena.setText(usuario.getContrasena());

    }

    public boolean verificarRepeticion (List<String> correos) {

        boolean repeticion = false;

        for(String correo : correos){

            repeticion = correo.equalsIgnoreCase(editTextCorreoElectronico.getText().toString().trim());

            if(repeticion)
                break;

        }

        return repeticion;

    }

}