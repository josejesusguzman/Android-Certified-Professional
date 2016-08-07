package com.example.josejesus.acpavanzadoscontentprovider;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnNuevo;
    private EditText etNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNuevo = (Button) findViewById(R.id.main_btn_nuevo);
        etNombre = (EditText) findViewById(R.id.main_et_nombre);

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("name", etNombre.getText().toString());
                Uri uri = getContentResolver().insert(MiProvider.CONTENT_URI, values);
                Toast.makeText(getBaseContext(), "Nuevo elemento agregado", Toast.LENGTH_LONG).show();
                etNombre.setText("");
            }
        });

    }
}
