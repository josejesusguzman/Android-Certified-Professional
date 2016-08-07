package com.example.josejesus.acpprofundizarinterfaz;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    MiVista miVista;
    private Button btnPresionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPresionar = (Button) findViewById(R.id.main_btn_presionar);
        miVista = (MiVista) findViewById(R.id.main_miVista);
        miVista.setColorCirculo(Color.parseColor("#FF1A9FD8"));
        miVista.setColorEtiqueta(Color.parseColor("#000000"));
        miVista.setTextoCirculo("Hola mari mi amor ");

        btnPresionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomColor();
                randomLabel();
            }
        });

    }

    protected void randomColor() {
        Random randomColor = new Random();
        int color = Color.argb(255, randomColor.nextInt(256), randomColor.nextInt(256), randomColor.nextInt(256));//Color de manera aleatoria
        miVista.setColorCirculo(color);
    }

    protected void randomLabel() {
        Random randomColor = new Random();
        int color = Color.argb(255, randomColor.nextInt(256), randomColor.nextInt(256), randomColor.nextInt(256));//Color de manera aleatoria
        miVista.setColorEtiqueta(color);
    }

}
