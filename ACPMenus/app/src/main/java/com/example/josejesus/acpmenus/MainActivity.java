package com.example.josejesus.acpmenus;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnContextual;
    private Button btnPopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnContextual = (Button) findViewById(R.id.main_btn_contextual);
        btnPopUp = (Button) findViewById(R.id.main_btn_popup);

        registerForContextMenu(btnContextual);

        btnPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.setOnMenuItemClickListener(eventoPopUp);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.show();
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(menu.NONE, 100, menu.NONE, "Cerrar App");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contactos) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
            startActivity(intent);
            return true;
        } else if (id == R.id.llamar) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:5521527721"));
            startActivity(intent);
            return true;
        } else if (id == R.id.ver_mapa) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:19.50, -96.90"));
            startActivity(intent);
            return true;
        } else if (id == R.id.navegar) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com.mx"));
            startActivity(intent);
            return true;
        } else if (id == 100) {//Cerrar app
            finish();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(menu.NONE, 100, menu.NONE, "Cerrar App");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contactos) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
            startActivity(intent);
            return true;
        } else if (id == R.id.llamar) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:5521527721"));
            startActivity(intent);
            return true;
        } else if (id == R.id.ver_mapa) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:19.50, -96.90"));
            startActivity(intent);
            return true;
        } else if (id == R.id.navegar) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com.mx"));
            startActivity(intent);
            return true;
        } else if (id == 100) {//Cerrar app
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private PopupMenu.OnMenuItemClickListener eventoPopUp = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.contactos) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                startActivity(intent);
                return true;
            } else if (id == R.id.llamar) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:5521527721"));
                startActivity(intent);
                return true;
            } else if (id == R.id.ver_mapa) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:19.50, -96.90"));
                startActivity(intent);
                return true;
            } else if (id == R.id.navegar) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com.mx"));
                startActivity(intent);
                return true;
            } else if (id == 100) {//Cerrar app
                finish();
            }
            return false;
        }
    };
}
