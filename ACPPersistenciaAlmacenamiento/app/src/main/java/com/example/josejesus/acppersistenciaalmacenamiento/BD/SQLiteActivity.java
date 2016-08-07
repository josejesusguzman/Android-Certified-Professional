package com.example.josejesus.acppersistenciaalmacenamiento.BD;

import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.josejesus.acppersistenciaalmacenamiento.R;

import java.util.List;

public class SQLiteActivity extends AppCompatActivity {
    private Button btnGuardar;
    private EditText etNombre, etApellido, etEdad;
    private ListView listView;
    private PersonaDataSource personaDataSource;
    private ArrayAdapter<Persona> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        btnGuardar = (Button) findViewById(R.id.sql_btn_guardar);
        etNombre = (EditText) findViewById(R.id.sql_et_nomnbre);
        etApellido = (EditText) findViewById(R.id.sql_et_apellido);
        etEdad = (EditText) findViewById(R.id.sql_et_edad);
        listView = (ListView) findViewById(R.id.sql_list);

        crearBaseDatos();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarRegistro();
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new ArrayAdapter<Persona>(this,
                android.R.layout.simple_list_item_1,
                obtenerRegistros());

        listView.setAdapter(adapter);

    }

    protected void crearBaseDatos() {
        personaDataSource = new PersonaDataSource(this);
    }

    protected void insertarRegistro() {
        personaDataSource.open();
        personaDataSource.insertarRegistro(etNombre.getText().toString(),
                etApellido.getText().toString(),
                Integer.valueOf(etEdad.getText().toString()));
        personaDataSource.close();
        etEdad.setText("");
        etApellido.setText("");
        etNombre.setText("");
    }

    protected List<Persona> obtenerRegistros() {
        personaDataSource.open();
        List<Persona> personas = personaDataSource.obtenerReguistros();
        personaDataSource.close();
        return personas;
    }

}
