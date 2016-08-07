package com.example.josejesus.acppersistenciaalmacenamiento;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.josejesus.acppersistenciaalmacenamiento.BD.SQLiteActivity;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.main_list);
        String[] opciones = {
                "Shared preferences",
                "Shared preferences activity",
                "Almacenamiento de archivos",
                "Base de datos"
        };

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opciones));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, SharedPreferencesActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, SharedPreferencesScreenActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, AlmacenamientoInternoExternoActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, SQLiteActivity.class));
                        break;
                }
            }
        });

    }
}
