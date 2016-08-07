package com.example.josejesus.acppersistenciaalmacenamiento;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SharedPreferencesActivity extends AppCompatActivity {
    private Button btnGuardar;
    private EditText etCorreo, etTelefono;
    private CheckBox chNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        btnGuardar = (Button) findViewById(R.id.shared_btn_guardar);
        chNoticias = (CheckBox) findViewById(R.id.shared_ch_noticias);
        etCorreo = (EditText) findViewById(R.id.shared_et_correo);
        etTelefono = (EditText) findViewById(R.id.shared_et_telefono);

        SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String correo = preferences.getString("correo", "");//LLave, valor por default si no se encuentra lo solicitado
        int telefono = preferences.getInt("telefono", 0);
        boolean noticias = preferences.getBoolean("noticias", false);

        etCorreo.setText(correo);
        etTelefono.setText(String.valueOf(telefono));
        chNoticias.setChecked(noticias);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("correo", etCorreo.getText().toString());
                editor.putInt("telefono", Integer.valueOf(etTelefono.getText().toString()));
                editor.putBoolean("noticias", chNoticias.isChecked());
                editor.commit();//Guardar los cambios
                Toast.makeText(SharedPreferencesActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
