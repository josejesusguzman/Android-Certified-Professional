package com.example.josejesus.acpmoodulo1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SegundaActivity extends AppCompatActivity {
    Button btnActividad;
    Button btnActividadE;
    Button btnWeb;
    Button btnLlamada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        btnActividad = (Button) findViewById(R.id.segunda_btn_activitdad);
        btnActividadE = (Button) findViewById(R.id.segunda_btn_activitdadE);
        btnWeb = (Button) findViewById(R.id.segunda_btn_web);
        btnLlamada = (Button) findViewById(R.id.segunda_btn_llamada);

        btnActividad.setOnClickListener(eventoOnClick);
        btnActividadE.setOnClickListener(eventoOnClick);
        btnWeb.setOnClickListener(eventoOnClick);
        btnLlamada.setOnClickListener(eventoOnClick);

    }

    private View.OnClickListener eventoOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnActividad) {
                Intent intent = new Intent(SegundaActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (v == btnActividadE) {
                Intent intent = new Intent(SegundaActivity.this, MainActivity.class);
                intent.putExtra("valor1","Dato 1");
                intent.putExtra("valor2", 7);
                startActivity(intent);
            } else if (v == btnWeb) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com.mx"));
                startActivity(intent);
            } else if (v == btnLlamada) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:5521527721"));
                startActivity(intent);
            }
        }
    };

}
