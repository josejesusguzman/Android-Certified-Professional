package com.example.josejesus.acpavanzadosservicios.servicios;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by josejesus on 6/30/2016.
 */
public class ServicioIniciado extends Service {

    AsyncTaskHora asyncTaskHora; //Utilizada por el servicio

    @Override
    public IBinder onBind(Intent intent) { //como es un iniciado, no va a tener que regresar un elemento CON SERVICIOS VINCULADOS
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Servicio iniciado", Toast.LENGTH_LONG).show();
        asyncTaskHora = new AsyncTaskHora();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_LONG).show();
        asyncTaskHora.cancel(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        asyncTaskHora.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    private class AsyncTaskHora extends AsyncTask<String, String, String> {

        private DateFormat dateFormat;
        private String hora;
        private boolean mostrando;

        @Override
        protected String doInBackground(String... params) {
            while (mostrando) {
                hora = dateFormat.format(new Date());
                try{
                    publishProgress(hora);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dateFormat = new SimpleDateFormat("HH:mm:ss"); //Darle formato
            mostrando = true;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(), "Hora actual: " + values[0], Toast.LENGTH_SHORT).show();
        }

        //Permite identificar cuando una clase de la cual hereda de Async se destruye y se impíde que sigua trabajando en segundo plano
        @Override
        protected void onCancelled() {
            super.onCancelled();
            mostrando = false;
        }
    }
}
