package com.example.josejesus.acpconsumoserviciosweb;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    Button btnSOAP;
    TextView txtRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build()); //Definir un modo estricto por la cual la app podra trabajar

        btnSOAP = (Button) findViewById(R.id.main_btn_soap);
        txtRespuesta = (TextView) findViewById(R.id.main_txt_respuesta);

        btnSOAP.setOnClickListener(clickListenerSOAP);
    }

    private View.OnClickListener clickListenerSOAP = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                LlamadaSoap llamadaSoap = new LlamadaSoap();
                String respuesta = llamadaSoap.getResultado("mexico"); //SE pone el pais
                txtRespuesta.setText(respuesta);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    class LlamadaSoap {
        private static final String metodo = "GetCitiesByCountry"; //Metodo para poder consumir el servicio web
        private static final String namespace = "http://www.webserviceX.NET";
        private static final String accionSoap = "http://www.webserviceX.NET/GetCitiesByCountry";
        private static final String url = "http://www.webservicex.net/globalweather.asmx";

        public String getResultado(String parametro) throws Exception { //Que el metodo capture cualquier excepcion
            try {
                SoapObject peticion = new SoapObject(namespace, metodo); //Peticion que se enviara a internet //REcibe namespace, metodo
                peticion.addProperty("CountryName", parametro); //Lo que se pedira
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);  //Sobre por le cual sera envuelta la petición
                sobre.dotNet = true; //Si es compatible con tecnologia .NET
                sobre.setOutputSoapObject(peticion);  //Incorporar la petición
                HttpTransportSE transporte = new HttpTransportSE(url); //Recibe el canal por el que viajara
                transporte.call(accionSoap, sobre);  //Solicitar la llamada
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse(); //Guardar el resultado obtenido
                return resultado.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

    }

}
