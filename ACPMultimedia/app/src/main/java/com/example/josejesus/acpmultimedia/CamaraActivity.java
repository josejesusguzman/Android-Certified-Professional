package com.example.josejesus.acpmultimedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CamaraActivity extends AppCompatActivity {
    private Button btnFoto;
    private ImageView imgFoto;
    private final int IDRESULT = 100;//Valor para saber si este es el resultado esperado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        btnFoto = (Button) findViewById(R.id.camara_btn_foto);
        imgFoto = (ImageView) findViewById(R.id.camara_img_foto);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, IDRESULT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IDRESULT) {
            if (requestCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgFoto.setImageBitmap(bitmap);
            }
        }

    }
}
