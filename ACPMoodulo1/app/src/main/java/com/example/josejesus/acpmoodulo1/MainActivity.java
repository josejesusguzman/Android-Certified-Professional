package com.example.josejesus.acpmoodulo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnElemento;
    EditText etMain1;
    TextView txtHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout miLinear = new LinearLayout(this);
        miLinear.setOrientation(LinearLayout.VERTICAL);
        btnElemento = new Button(this);
        btnElemento.setText("Boton creado desde JAVA");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        btnElemento.setLayoutParams(layoutParams);
        miLinear.addView(btnElemento);

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        miLinear.setLayoutParams(layoutParams1);
        setContentView(miLinear);

        //Diseño de interfaz desde XML
        //btnElemento = (Button) findViewById(R.id.main_btn_elemento);

        btnElemento.setOnClickListener(eventoClick);

    }

    private View.OnClickListener eventoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnElemento){
                Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

}
