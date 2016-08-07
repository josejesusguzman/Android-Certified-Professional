package com.example.josejesus.acpconectividadbluetoothservidor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private Button btnVisible;
    private TextView txtMensaje;

    private static final int PETICION_BLUETOOTH = 0; //Para StartActivityForResult
    private static final UUID MI_UUID = UUID.fromString("6049a354-3df0-11e3-8e7a-ce3f5508acd9"); //Identificador unico con el cual conectarse a otros dispositivos
    private BluetoothServerSocket bluetoothServerSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVisible = (Button) findViewById(R.id.main_btn_visible);
        txtMensaje = (TextView) findViewById(R.id.main_txt_mensaje);

        btnVisible.setOnClickListener(onClickListener);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(MainActivity.this, "No se encontro un dispositivo bluetooth en el equipo", Toast.LENGTH_LONG).show();
            finish();
        } else if (bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, PETICION_BLUETOOTH);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PETICION_BLUETOOTH:
                if (resultCode == RESULT_OK) {
                    txtMensaje.append("\n" + "Estado: Bluetooth habilitado.");
                } else if (resultCode == RESULT_CANCELED) {
                    txtMensaje.append("\n" + "Estado: Bluetooth deshabilitado");
                    finish();
                }
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!iniciarBluetooth()) {
                txtMensaje.append("\n" + "Estado: Error al pasar a modo visible");
            }
        }
    };

    class AceptarSolicitudesAsync extends AsyncTask<Integer, String, Integer> {

        private BluetoothServerSocket bluetoothServerSocketTemporal = null;

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                bluetoothServerSocketTemporal = bluetoothAdapter.listenUsingRfcommWithServiceRecord("com.example.josejesus.acpconectividadbluetoothservidor", MI_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }

            bluetoothServerSocket = bluetoothServerSocketTemporal;

            BluetoothSocket bluetoothSocket = null;
            try{
                bluetoothSocket = bluetoothServerSocket.accept(); //Que acepte cualquier solicitud que cumpla con los requisitos
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (bluetoothSocket != null) {
                CrearConexionesAsync crearConexionesAsync = new CrearConexionesAsync();
                crearConexionesAsync.execute(bluetoothSocket);
                publishProgress("Estado: Bñuetooth cliente conectado");
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txtMensaje.append("\n" + values[0]);
        }
    }

    class CrearConexionesAsync extends AsyncTask<BluetoothSocket, String, Integer> {

        private BluetoothSocket bluetoothSocket; //La instancia que se enviara a la clase AceptarSolicitudes
        private BufferedReader bufferedReader; //Almacenar todos los mensajes que se reciuben del dliente

        @Override
        protected Integer doInBackground(BluetoothSocket... params) {
            bluetoothSocket = params[0];
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(bluetoothSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    String mensaje = bufferedReader.readLine();
                    publishProgress(mensaje);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txtMensaje.append("\n" + values[0]);
        }
    }

    //Iniciar el dispositivo bluetooth y se haga visible
    private boolean iniciarBluetooth() {
        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 50); //La cantidad de minutos que el dispoitivo puede estar encontrable
            startActivity(intent);
            txtMensaje.append("\n" + "Estado: mmodo encontrable");
            AceptarSolicitudesAsync aceptarSolicitudesAsync = new AceptarSolicitudesAsync();
            aceptarSolicitudesAsync.execute(0);
            txtMensaje.append("\n" + "Estado: esperando conexiones");
            return true;
        }
        return false;
    }

}
