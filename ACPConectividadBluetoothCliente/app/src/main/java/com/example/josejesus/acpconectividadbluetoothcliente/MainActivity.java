package com.example.josejesus.acpconectividadbluetoothcliente;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter; //Agregar dispositivois y depsues conectarse
    private ArrayAdapter<String> dispositivosAdapter;
    public BluetoothSocket bluetoothSocket; //dar entrada del flujo de datos
    private PrintWriter printWriter;

    private Button btnEnviar;
    private EditText etMEnsaje;
    private ListView listView;

    private static final int PETICION_BLUETOOTH = 0; //Para StartActivityForResult
    private static final UUID MI_UUID = UUID.fromString("6049a354-3df0-11e3-8e7a-ce3f5508acd9"); //Identificador unico con el cual conectarse a otros dispositivos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnviar = (Button) findViewById(R.id.main_btn_enviar);
        etMEnsaje = (EditText) findViewById(R.id.main_et_mensaje);
        listView  = (ListView) findViewById(R.id.main_list);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) { //Preguntar si tiene bluetooth
            Toast.makeText(MainActivity.this, "No se ha podido encontrar un dispositivo Bluetooth", Toast.LENGTH_LONG).show();
            finish();
        } else {
            if (bluetoothAdapter.isEnabled()) { //Preguntar si se encuentra prendido
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, PETICION_BLUETOOTH);
            }
        }

        dispositivosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(dispositivosAdapter);

        listView.setOnItemClickListener(itemClickListener);
        btnEnviar.setOnClickListener(onClickListener);

        btnEnviar.setEnabled(false);

    }

    //Cada vez que el usuario de clic a un elemento de la lista se inicie una conexion y se pueda enviar el mensaje
    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        private BluetoothDevice bluetoothDevice;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            bluetoothAdapter.cancelDiscovery(); //detener la busqueda de más dispositivos
            String nombreBluetooth = ((TextView)view).getText().toString();
            String direccionBluetooth = nombreBluetooth.substring(nombreBluetooth.length() - 17); //sustraer los ultimos 17 caracteres

            bluetoothDevice = bluetoothAdapter.getRemoteDevice(direccionBluetooth);
            Toast.makeText(MainActivity.this, "Realizando conexión", Toast.LENGTH_LONG).show();

            ConexionClienteAsync conexionClienteAsync = new ConexionClienteAsync(); //Por que es activitvidad bloqueanre e impide que sea bloqueda por el SO
            conexionClienteAsync.execute(bluetoothDevice);
        }
    };

    //Enviar un mensaje
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            enviarMnensaje();
        }
    };

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //Para cuando se encuentre un dispositivo REcibir toda la info cuando se encuentre un dispositivo
        @Override
        public void onReceive(Context context, Intent intent) {
            String accion = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(accion)) { //Si es accion de descubrimiento
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE); //Obtener la info del elemento a agregar a la lista
                if (bluetoothDevice.getBondState() != BluetoothDevice.BOND_BONDED) { //SI el disp ya esta en la lista
                    dispositivosAdapter.add(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(accion)) { //si se acabo la accion de descubrimiento
                Toast.makeText(MainActivity.this, "Busqueda de dispositivos nuevos terminada", Toast.LENGTH_LONG).show();
                if (dispositivosAdapter.getCount() == 0) { //si no se obtuvieron dispositivos
                    dispositivosAdapter.add("No se encontraron dispotsitivos");
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actualizar) {
            iniciarDescrubrimiento();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //Para determinar si se activo el bluetooth
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PETICION_BLUETOOTH:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(MainActivity.this, "BLuetooh habilitado", Toast.LENGTH_LONG).show();
                    iniciarDescrubrimiento();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "BLuetooh deshabilitado", Toast.LENGTH_LONG).show();
                    finish();
                }
        }
    }

    @Override
    protected void onPause() { //Cuando la activity ha pasado a segundo plano
        super.onPause();
        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
            this.unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onResume() { //Se ha entrado de nuevo a la app
        super.onResume();
        registrarBroadcast();
    }

    private void registrarBroadcast() { //Obtener la información a partir de un Broadcast
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(broadcastReceiver, intentFilter);

        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void iniciarDescrubrimiento() { //Preguntar si el bluetooth adapter se encuentra descubriendo
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        Toast.makeText(MainActivity.this, "Iniciando descrubrimiento de dispositivos", Toast.LENGTH_LONG).show();
        bluetoothAdapter.startDiscovery();
    }

    private void enviarMnensaje() {
        if (printWriter != null) {
            try {
                String mensaje = etMEnsaje.getText().toString();
                printWriter.print(mensaje);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "Conexión no establecida con dispositivo", Toast.LENGTH_LONG).show();
        }

    }

    class ConexionClienteAsync extends AsyncTask<BluetoothDevice, String, Boolean> {

        BluetoothDevice bluetoothDevice;
        BluetoothSocket bluetoothSocketTemporal;

        @Override
        protected Boolean doInBackground(BluetoothDevice... params) {
            this.bluetoothDevice = params[0];
            try {
                bluetoothSocketTemporal = bluetoothDevice.createRfcommSocketToServiceRecord(MI_UUID);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            bluetoothSocket = bluetoothSocketTemporal;
            bluetoothAdapter.cancelDiscovery();
            try {
                bluetoothSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Toast.makeText(MainActivity.this, "Conexión establecida", Toast.LENGTH_LONG).show();
                btnEnviar.setEnabled(true);
            } else {
                Toast.makeText(MainActivity.this, "Error al intentar conectar", Toast.LENGTH_LONG).show();
            }
        }
    }

}
