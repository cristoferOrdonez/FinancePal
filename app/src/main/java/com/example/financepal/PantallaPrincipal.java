package com.example.financepal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Toast.makeText(this, "Bienvenido.", Toast.LENGTH_LONG).show();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}