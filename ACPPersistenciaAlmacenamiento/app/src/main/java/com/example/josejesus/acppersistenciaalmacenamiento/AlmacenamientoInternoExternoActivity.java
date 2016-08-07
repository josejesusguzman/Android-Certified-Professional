package com.example.josejesus.acppersistenciaalmacenamiento;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AlmacenamientoInternoExternoActivity extends AppCompatActivity {
    private EditText etInterna, etExterna;
    private Button btnGuardarInterna, btnGuardarExterna, btnLeerInterna, btnLeerExterna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenamiento_interno_externo);

        etInterna = (EditText) findViewById(R.id.alm_et_archivoUno);
        etExterna = (EditText) findViewById(R.id.alm_et_archivoDos);
        btnGuardarExterna = (Button) findViewById(R.id.alm_btn_guardarExterno);
        btnGuardarInterna = (Button) findViewById(R.id.alm_btn_guardarInterno);
        btnLeerExterna = (Button) findViewById(R.id.alm_btn_recuperarExterno);
        btnLeerInterna = (Button) findViewById(R.id.alm_btn_recuperarInterno);

        btnGuardarInterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarInterna(etInterna.getText().toString());
            }
        });
        btnLeerInterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), leerInterna(), Toast.LENGTH_LONG).show();
            }
        });
        btnGuardarExterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarExterna(etExterna.getText().toString());
            }
        });
        btnLeerExterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), leerExterna(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void guardarInterna(String textoArchivo) {
        String nombreArchivo ="archivoAndroid.txt";

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(nombreArchivo, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fileOutputStream != null) {
                fileOutputStream.write(textoArchivo.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Guardado en memoria interna", Toast.LENGTH_SHORT).show();

    }

    private String leerInterna() {
        String nombreArchivo = "archivoAndroid.txt";
        String contenidoArchivo = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(nombreArchivo)));
            contenidoArchivo = bufferedReader.readLine();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contenidoArchivo;
    }

    private void guardarExterna(String textoArchivo) {
        String nombreArchivo = "archivoAndroid.txt";

        String rutaSD = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rutaSD = getExternalFilesDir(null).getAbsolutePath();
        }
        File file = new File(rutaSD + "/" + nombreArchivo);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            if (fileOutputStream != null) {
                fileOutputStream.write(textoArchivo.getBytes());
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Guardado en memoria externa", Toast.LENGTH_SHORT).show();
    }

    private String leerExterna() {
        String nobreArchivo = "archivoAndroid.txt";
        String rutaSD = "";
        String contenidoArchivo = "";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rutaSD = getExternalFilesDir(null).getAbsolutePath();
        }
        try {
            File file = new File(rutaSD + "/" + nobreArchivo);
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            contenidoArchivo = bufferedReader.readLine();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenidoArchivo;
    }

}
