package com.example.josejesus.acpconectividadwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    WifiManager wifiManager;
    private BroadcastReceiverWifi broadcastReceiverWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.main_list);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE); //Permitir el escaneo, dteección y conexión a redes WIFI
        broadcastReceiverWifi = new BroadcastReceiverWifi();

        estadoWifi();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiverWifi); //Quitar el registro del Broadcast
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void estadoWifi() { //Conocer si tenemos habilitado el WIFI
        if (wifiManager.isWifiEnabled()) {
            wifiManager.startScan();
        } else {
            Toast.makeText(MainActivity.this, "No esta encendido el WIFI", Toast.LENGTH_SHORT).show();
        }
    }

    class BroadcastReceiverWifi extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) { //Conocer la información que se este divulgando en el Broadcast
            //El Broadcast dura lo que dure este metodo
            List<ScanResult> listaEscaneo = wifiManager.getScanResults();
            String[] listaWifi = new String[listaEscaneo.size()];

            for (int i = 0; i < listaEscaneo.size(); i++) {
                listaWifi[i] = (listaEscaneo.get(i).SSID + "\n"
                        + listaEscaneo.get(i).frequency + "\n"
                        + listaEscaneo.get(i).capabilities + "\n"
                        + String.valueOf(listaEscaneo.get(i).timestamp) + "\n");
            }

            listView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, listaWifi));

        }
    }

}
