package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financepal.db.DbGastos;
import com.example.financepal.db.DbUsuarios;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    //Declaracion variables de informacion
    EditText nombres, apellidos, edad, correoElectronicoR, contrasenaR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombres = findViewById(R.id.editTextNombresMISDATOS);
        apellidos = findViewById(R.id.editTextApellidosMISDATOS);
        edad = findViewById(R.id.editTextEdadMISDATOS);
        correoElectronicoR = findViewById(R.id.editTextCorreoElectronicoRMISDATOS);
        contrasenaR = findViewById(R.id.editTextContrasenaRMISDATOS);

        //filtros
        edad.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        contrasenaR.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        nombres.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        apellidos.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
        correoElectronicoR.setFilters(new InputFilter[]{new InputFilter.LengthFilter(320)});

    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void cambiarAAcceso(View view) {
        Intent miIntent = new Intent(this, MainActivity.class);
        startActivity(miIntent);
        finishAffinity();
    }

    public void VerificarInformacionRegistro(View view) {

        boolean flag = true;
        String mensajeError = "";

        if(nombres.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado nombres validos\n";
            flag = false;
        }
        if(apellidos.getText().toString().trim().equals("")) {
            mensajeError += "No ha ingresado apellidos validos\n";
            flag = false;
        }
        if(edad.getText().toString().trim().equals("") || Integer.parseInt(edad.getText().toString().trim()) > 150) {
            mensajeError += "No ha ingresado una edad valida\n";
            flag = false;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correoElectronicoR.getText().toString());

        if(!mather.find()){
            mensajeError += "No ha ingresado un correo electronico valido\n";
            flag = false;
        }
        if(contrasenaR.getText().toString().length() < 8){
            mensajeError += "Debe ingresar una contraseña de por lo menos 8 caracteres\n";
            flag = false;
        }
        if(contrasenaR.getText().toString().contains(" ")){
            mensajeError += "La contraseña no puede contener espacios en blanco\n";
            flag = false;
        }

        if(flag)
            Registrar(view);
        else
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();

    }

    public void Registrar(View view) {
        String nombres = this.nombres.getText().toString().trim();
        String apellidos = this.apellidos.getText().toString().trim();
        int edad = Integer.parseInt(this.edad.getText().toString());
        String correoElectronicoR = this.correoElectronicoR.getText().toString();
        String contrasenaR = this.contrasenaR.getText().toString();

        DbUsuarios dbUsuarios = new DbUsuarios(this);

        if(!verificarRepeticion(dbUsuarios.obtenerCorreosElectronicos())){

            long i = dbUsuarios.agregarUsuario(nombres, apellidos, edad, correoElectronicoR.toLowerCase(), contrasenaR);
            DbGastos db = new DbGastos(this);
            db.insertarprimeraCategoria(correoElectronicoR.toLowerCase());
            db.close();
            Toast.makeText(this, "Se ha registrado con exitosamente.", Toast.LENGTH_SHORT).show();

            cambiarAAcceso(view);

        } else {

            Toast.makeText(this, "El correo electronico ingresado ya se encuentra registrado", Toast.LENGTH_SHORT).show();

        }

    }

    public boolean verificarRepeticion(List<String> correos) {

        boolean repeticion = false;

        for(String correo : correos){

            repeticion = correo.equalsIgnoreCase(correoElectronicoR.getText().toString().trim());

            if(repeticion)
                break;

        }

        return repeticion;

    }

    public void onBackPressed(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode == KeyEvent.KEYCODE_BACK){

            cambiarAAcceso(new View(this));

        }

        return super.onKeyDown(keyCode, event);

    }

}