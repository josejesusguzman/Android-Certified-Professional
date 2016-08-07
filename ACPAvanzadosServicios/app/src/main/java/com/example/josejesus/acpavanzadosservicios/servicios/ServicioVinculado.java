package com.example.josejesus.acpavanzadosservicios.servicios;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServicioVinculado extends Service {

    private IBinder iBinder = new MiBinder(); //Establece el puente entre el componente y el servicio

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio Destruido", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    public String getTimeStamp() {
        String hora;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SS");
        hora = dateFormat.format(new Date());
        return hora;
    }

    public class MiBinder extends Binder {
        public ServicioVinculado getService() {
            return ServicioVinculado.this;
        }
    }
}
