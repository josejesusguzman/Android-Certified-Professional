package com.example.josejesus.acpmultimedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.main_list1);
        String[] opciones = {
                "Audio",
                "Video Media Player",
                "Video VideoView",
                "Camara"
        };

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opciones));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, AudioActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, VideoMediaPlayerActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, VideoViewActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, CamaraActivity.class));
                        break;
                }
            }
        });

    }
}
