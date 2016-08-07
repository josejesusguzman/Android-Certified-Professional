package com.example.josejesus.multitaskingasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private Button btnIniciar;
    private TextView txtLineasLeidas;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLineasLeidas = (TextView) findViewById(R.id.main_txt_numeroLineas);
        webView = (WebView) findViewById(R.id.main_webView);
        btnIniciar = (Button) findViewById(R.id.main_btn_iniciar);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionAsincrona conexionAsincrona = new ConexionAsincrona();
                conexionAsincrona.execute("http://omnius.com.mx/edu/course/view.php?id=3");
            }
        });
    }

    class ConexionAsincrona extends AsyncTask<String, Integer, String> {
    //Se deben decfinir tres valores y estos definen el tipo de parametro
        //El tipo de parametros que recibira el doInBackground
        //El tipo de valor que tomara la comunicacion entre en Background y el onProgress
        //Tipo de respuesta o de retorno que enviara el DoinBackground

        @Override
        protected String doInBackground(String... params) {
            Integer contadorLineas = 0;
            String resultado = "";
            try {
                URL url = null;
                url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    resultado += line;
                    contadorLineas++;
                    publishProgress(contadorLineas); //Permite enviar infor al metodo onProgressUpdate
                    Thread.sleep(100); //Dormir el hilo
                }
                bufferedReader.close();
                return resultado;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPreExecute() { //Se llama antes de iniciar la tarea asincrona. Aun tiene acceso a la interfaz de usuario EN HILO PRINCIPAL
            super.onPreExecute();

            Toast.makeText(getApplicationContext(),
                    "Iniciando descarga de datos",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String s) { //Se ejecuta despues de que doInBackground termina eN HILO PRINCIPAL ACCESO A INTERFAZ
            super.onPostExecute(s);

            webView.loadData(s, "text/html", "UTF-8");
            Toast.makeText(getApplicationContext(),
                    "Descarga Finalizada",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) { //Permite comunicarse desde doInBackground con la interfaz EN HILO PRINCIPAL ACCESO A INTERFAZ
            super.onProgressUpdate(values);

            txtLineasLeidas.setText("Lineas leidas: " + values[0]);
        }
    }


}
