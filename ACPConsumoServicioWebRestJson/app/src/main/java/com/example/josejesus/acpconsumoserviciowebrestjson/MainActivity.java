package com.example.josejesus.acpconsumoserviciowebrestjson;

import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        listView = (ListView) findViewById(R.id.main_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        webServiceREST();

    }
    private void webServiceREST() {
        try{
            URL url = new URL("http://omnius.com.mx:8080/sevenwin/webresources/service/getAllEntidadFederativa");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Leer lo que entre de informacion
            String line = "";
            String resultadoWebService = "";
            while ((line = bufferedReader.readLine()) != null) {
                resultadoWebService += line;
            }
            bufferedReader.close();
            parseInformation(resultadoWebService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseInformation(String resultadoJson) {
        JSONArray jsonArray = null;
        String nombre, clave;

        try {
            jsonArray = new JSONArray(resultadoJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Dependen de como este configurado el servicio web
                clave = jsonObject.getString("clave");
                nombre = jsonObject.getString("nombre");
                adapter.add("Clave: " + clave + "\tNombre: " + nombre);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
