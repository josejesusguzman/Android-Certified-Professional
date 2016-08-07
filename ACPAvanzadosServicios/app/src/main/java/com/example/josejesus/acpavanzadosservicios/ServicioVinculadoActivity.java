package com.example.josejesus.acpavanzadosservicios;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josejesus.acpavanzadosservicios.servicios.ServicioVinculado;

public class ServicioVinculadoActivity extends AppCompatActivity {

    private ServicioVinculado servicioVinculado;
    private boolean serviceBound = false;
    private TextView txtTiempo;
    private Button btnTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_vinculado);

        txtTiempo = (TextView) findViewById(R.id.vinculado_txt_tiempo);
        btnTiempo = (Button) findViewById(R.id.vinculado_btn_obtenerTiempo);
        btnTiempo.setOnClickListener(clickListenerTiempo);
    }

    private View.OnClickListener clickListenerTiempo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (serviceBound) { //Preguntar si estamos vinculados al servicio
                txtTiempo.append(servicioVinculado.getTimeStamp() + "\n");
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ServicioVinculado.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE); //No nos preocupamos de como se vincula el servicio
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceBound){
            unbindService(serviceConnection);
            serviceBound = false;
        } else {
            Toast.makeText(this, "Servicio no vinculado", Toast.LENGTH_LONG).show();
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServicioVinculado.MiBinder miBinder = (ServicioVinculado.MiBinder) service;
            servicioVinculado = miBinder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };
}
