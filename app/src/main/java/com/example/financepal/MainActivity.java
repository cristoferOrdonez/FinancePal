package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financepal.PantallaPrincipal;
import com.example.financepal.db.DbUsuarios;
import com.example.financepal.entidades.Usuario;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Declaracion de los editText
    private EditText editTextCorreoElectronico, editTextContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de editTexts
        editTextCorreoElectronico = findViewById((R.id.editTextCorreoElectronico));
        editTextContrasena = findViewById(R.id.editTextTextContrasena);

    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void registro(View view) {
        Intent miIntent = new Intent(this, Registro.class);
        startActivity(miIntent);
    }

    public void acceder(View view) {

        DbUsuarios dbUsuarios = new DbUsuarios(this);

        if(verificarExistencia(dbUsuarios.obtenerCorreosElectronicos())){

            Usuario usuario = dbUsuarios.verUsuario(editTextCorreoElectronico.getText().toString().toLowerCase());

            if(editTextContrasena.getText().toString().equals(usuario.getContrasena())){

                Intent intent = new Intent(this, PantallaPrincipal.class);
                intent.putExtra("correoElectronico", usuario.getCorreoElectronico());
                startActivity(intent);
                finishAffinity();

            } else {

                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

            }


        } else {

            Toast.makeText(this, "El correo electronico ingresado no se encuentra registrado.", Toast.LENGTH_SHORT).show();

        }

    }

    public boolean verificarExistencia(List<String> correos){

        boolean existencia = false;

        for(String correo : correos){

            existencia = correo.equalsIgnoreCase(editTextCorreoElectronico.getText().toString());

            if(existencia)
                break;

        }

        return existencia;

    }

}