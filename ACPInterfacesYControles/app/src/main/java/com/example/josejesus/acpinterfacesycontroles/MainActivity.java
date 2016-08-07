package com.example.josejesus.acpinterfacesycontroles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button botonPresionar;
    Spinner spinnerColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerColor = (Spinner) findViewById(R.id.main_spn1);
        botonPresionar = (Button) findViewById(R.id.main_btn_presioname);
        //implementacion deel evento por definicion de interfaz
        botonPresionar.setOnClickListener(this);
        //Implementación de un veento por definición de una clase anonima
        botonPresionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Implementación del evento por definición de una clase interna
        botonPresionar.setOnClickListener(eventoBoton);

    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private View.OnClickListener eventoBoton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
