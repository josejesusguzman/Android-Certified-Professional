package com.example.josejesus.acpavanzadosservicios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.josejesus.acpavanzadosservicios.servicios.ServicioIniciado;

public class ServicioIniciadoActivity extends AppCompatActivity {

    private Button btnIniciarServicio;
    private Button btnDetenerServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_iniciado);

        btnIniciarServicio = (Button) findViewById(R.id.iniciado_btn_iniciarServicio);
        btnDetenerServicio = (Button) findViewById(R.id.iniciado_btn_detenerServicio);

        btnIniciarServicio.setOnClickListener(clickListenerIniciar);
        btnDetenerServicio.setOnClickListener(clickListenerDetener);
    }

    private View.OnClickListener clickListenerIniciar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ServicioIniciadoActivity.this, ServicioIniciado.class);
            startService(intent); //Se inicia el servicio
        }
    };

    private View.OnClickListener clickListenerDetener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ServicioIniciadoActivity.this, ServicioIniciado.class);
            stopService(intent);
        }
    };

}
